package com.panel;

import com.button.buttonGroupJawaban;
import koneksi.konek;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class survey_mood extends javax.swing.JPanel {
    private int userId; 
    private List<String> questions = new ArrayList<>();
    private Map<Integer, Integer> answers = new HashMap<>();
    private int currentPage = 0;
    private static final int QUESTIONS_PER_PAGE = 5;
    private static final int TOTAL_QUESTIONS = 10;
    
    private JPanel questionsPanel;
    private List<buttonGroupJawaban> answerPanels;
    private List<JLabel> questionLabels;
    private JButton nextButton;
    private JButton submitButton;
    private JPanel cardPanel;
    private JPanel buttonPanel;
    
    private int totalScore = 0;

   public survey_mood(int userId) {
        this.userId = userId;
        initComponents();
        init();
        loadQuestionsFromDatabase();
        displayQuestions();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));
        questionsPanel.setBackground(Color.WHITE);

        answerPanels = new ArrayList<>();
        questionLabels = new ArrayList<>();

        // Create button panel
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        nextButton = new JButton("Pertanyaan Selanjutnya");
        submitButton = new JButton("Kirim");
        submitButton.setVisible(false);

        nextButton.addActionListener(e -> showNextPage());
        submitButton.addActionListener(e -> calculateAndShowScore());

        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);

        JScrollPane scrollPane = new JScrollPane(questionsPanel);
        cardPanel.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(cardPanel, BorderLayout.CENTER);
    }

    private void loadQuestionsFromDatabase() {
        try (Connection conn = konek.GetConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT question_text FROM pertanyaan_survey LIMIT ?")) {
            stmt.setInt(1, TOTAL_QUESTIONS);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                questions.add(rs.getString("question_text"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat pertanyaan: " + e.getMessage());
        }
    }

    private void displayQuestions() {
        questionsPanel.removeAll();
        int startIndex = currentPage * QUESTIONS_PER_PAGE;
        int endIndex = Math.min(startIndex + QUESTIONS_PER_PAGE, questions.size());

        for (int i = startIndex; i < endIndex; i++) {
            JPanel questionContainer = new JPanel(new BorderLayout());
            questionContainer.setBackground(Color.WHITE);
            
            JLabel questionLabel = new JLabel((i + 1) + ". " + questions.get(i));
            questionLabel.setFont(new Font("Arial", Font.BOLD, 14));
            questionLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
            
            buttonGroupJawaban answerPanel = new buttonGroupJawaban();
            
            questionContainer.add(questionLabel, BorderLayout.NORTH);
            questionContainer.add(answerPanel, BorderLayout.CENTER);
            
            questionLabels.add(questionLabel);
            answerPanels.add(answerPanel);
            
            questionsPanel.add(questionContainer);
            questionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        updateButtonVisibility();
        revalidate();
        repaint();
        
    }

    private void showNextPage() {
        int startIndex = currentPage * QUESTIONS_PER_PAGE;
        int endIndex = Math.min(startIndex + QUESTIONS_PER_PAGE, questions.size());
        
        boolean allAnswered = true;
        for (int i = 0; i < (endIndex - startIndex); i++) {
            buttonGroupJawaban panel = answerPanels.get(i);
            if (panel.getSelectedValue() == -1) {
                allAnswered = false;
                break;
            }
            answers.put(startIndex + i, panel.getSelectedValue());
        }

        if (!allAnswered) {
            JOptionPane.showMessageDialog(this, "Silakan jawab semua pertanyaan terlebih dahulu.");
            return;
        }

        answerPanels.clear();
        questionLabels.clear();
        currentPage++;
        displayQuestions();
        
    }

    private void updateButtonVisibility() {
        int totalPages = (int) Math.ceil(questions.size() / (double) QUESTIONS_PER_PAGE);
        if (currentPage == totalPages - 1) {
            nextButton.setVisible(false);
            submitButton.setVisible(true);
        } else {
            nextButton.setVisible(true);
            submitButton.setVisible(false);
        }
        
    }

    private void calculateAndShowScore() {

        int startIndex = currentPage * QUESTIONS_PER_PAGE;
        int endIndex = Math.min(startIndex + QUESTIONS_PER_PAGE, questions.size());
        
        for (int i = 0; i < (endIndex - startIndex); i++) {
            buttonGroupJawaban panel = answerPanels.get(i);
            if (panel.getSelectedValue() != -1) {
                answers.put(startIndex + i, panel.getSelectedValue());
            }
        }

        for (Integer answer : answers.values()) {
            switch (answer) {
                case 2: 
                    this.totalScore += 2;
                    break;
                case 4: 
                    this.totalScore += 4;
                    break;
                case 6: 
                    this.totalScore += 6;
                    break;
                case 8: // Baik
                    this.totalScore += 8;
                    break;
                case 10: // Sangat Baik
                    this.totalScore += 10;
                    break;
            }
        }

        double averageScore = (double) totalScore / answers.size();
        String result = String.format("Total Nilai: %.2f", averageScore);
        JOptionPane.showMessageDialog(this, result);
        
        saveResults(averageScore);
    }
private void saveResults(double averageScore) {
    try (Connection conn = konek.GetConnection();
         PreparedStatement stmt = conn.prepareStatement(
         "INSERT INTO hasil_survey (id, average_score, survey_date) VALUES (?, ?, NOW())")) {
        
        stmt.setInt(1, userId); // Menggunakan userId sebagai ID pengguna
        stmt.setDouble(2, averageScore); // Menyimpan skor rata-rata
        
        stmt.executeUpdate(); // Menjalankan query
        JOptionPane.showMessageDialog(this, "Hasil survei berhasil disimpan!");
        
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Gagal menyimpan hasil: " + e.getMessage());
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
