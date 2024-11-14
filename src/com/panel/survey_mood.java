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
    private List<String> pertanyaan = new ArrayList<>();
    private Map<Integer, Integer> answers = new HashMap<>();
    private int pageSekarang = 0;
    private static final int PERTANYAAN_PERPAGE = 5;
    private static final int TOTAL_PERTANYAAN = 10;
    
    private JPanel panelPertanyaan;
    private List<buttonGroupJawaban> panelJawaban;
    private List<JLabel> labelPertanyaan;
    private JButton nextButton;
    private JButton submitButton;
    private JPanel cardPanel;
    private JPanel buttonPanel;

   public survey_mood(int userId) {
        initComponents();
        init();
        loadPertanyaan();
        tampilkanPertanyaan();
    }

    private void init() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        cardPanel = new JPanel(new BorderLayout());
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        panelPertanyaan = new JPanel();
        panelPertanyaan.setLayout(new BoxLayout(panelPertanyaan, BoxLayout.Y_AXIS));
        panelPertanyaan.setBackground(Color.WHITE);

        panelJawaban = new ArrayList<>();
        labelPertanyaan = new ArrayList<>();

        // panel buat naruh button next
        buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        nextButton = new JButton("Pertanyaan Selanjutnya");
        submitButton = new JButton("Kirim");
        submitButton.setVisible(false);

        nextButton.addActionListener(e -> showPageSelanjutnya());
        submitButton.addActionListener(e -> hitungSkor());

        buttonPanel.add(nextButton);
        buttonPanel.add(submitButton);

        JScrollPane scrollPane = new JScrollPane(panelPertanyaan);
        cardPanel.add(scrollPane, BorderLayout.CENTER);
        cardPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(cardPanel, BorderLayout.CENTER);
    }

    private void loadPertanyaan() {
        try (Connection conn = konek.GetConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT question_text FROM pertanyaan_survey LIMIT ?")) {
            stmt.setInt(1, TOTAL_PERTANYAAN);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pertanyaan.add(rs.getString("question_text"));
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "gagal menampilkan pertanyaan: " + e.getMessage());
        }
    }

    private void tampilkanPertanyaan() {
        panelPertanyaan.removeAll();
        int startIndex = pageSekarang * PERTANYAAN_PERPAGE;
        int endIndex = Math.min(startIndex + PERTANYAAN_PERPAGE, pertanyaan.size());

        for (int i = startIndex; i < endIndex; i++) {
            JPanel containerPertanyaan = new JPanel(new BorderLayout());
            containerPertanyaan.setBackground(Color.WHITE);
            
            JLabel pertanyaanlabel = new JLabel((i + 1) + ". " + pertanyaan.get(i));
            pertanyaanlabel.setFont(new Font("Arial", Font.BOLD, 14));
            pertanyaanlabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));
            
            buttonGroupJawaban jawabanPanel = new buttonGroupJawaban();
            
            containerPertanyaan.add(pertanyaanlabel, BorderLayout.NORTH);
            containerPertanyaan.add(jawabanPanel, BorderLayout.CENTER);
            
            labelPertanyaan.add(pertanyaanlabel);
            panelJawaban.add(jawabanPanel);
            
            panelPertanyaan.add(containerPertanyaan);
            panelPertanyaan.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        updateButtonVisibility();
        revalidate();
        repaint();
    }

    private void showPageSelanjutnya() {
        int startIndex = pageSekarang * PERTANYAAN_PERPAGE;
        int endIndex = Math.min(startIndex + PERTANYAAN_PERPAGE, pertanyaan.size());
        
        boolean diisi_semua = true;
        for (int i = 0; i < (endIndex - startIndex); i++) {
            buttonGroupJawaban panel = panelJawaban.get(i);
            if (panel.getSelectedValue() == -1) {
                diisi_semua = false;
                break;
            }
            answers.put(startIndex + i, panel.getSelectedValue());
        }

        if (!diisi_semua) {
            JOptionPane.showMessageDialog(this, "silakan jawab semua pertanyaan terlebih dahulu.");
            return;
        }

        panelJawaban.clear();
        labelPertanyaan.clear();
        pageSekarang++;
        tampilkanPertanyaan();
    }

    private void updateButtonVisibility() {
        int totalPages = (int) Math.ceil(pertanyaan.size() / (double) PERTANYAAN_PERPAGE);
        if (pageSekarang == totalPages - 1) {
            nextButton.setVisible(false);
            submitButton.setVisible(true);
        } else {
            nextButton.setVisible(true);
            submitButton.setVisible(false);
        }
    }

  private void hitungSkor() {
        int startIndex = pageSekarang * PERTANYAAN_PERPAGE;
        int endIndex = Math.min(startIndex + PERTANYAAN_PERPAGE, pertanyaan.size());
        
        boolean allAnswered = true;
        for (int i = 0; i < (endIndex - startIndex); i++) {
            buttonGroupJawaban panel = panelJawaban.get(i);
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

        int totalScore = 0;
        for (Integer answer : answers.values()) {
            totalScore += answer;
        }

        double averageScore = (double) totalScore / answers.size();
        
        String category;
        if (averageScore >= 9) {
            category = "Sangat Baik";
        } else if (averageScore >= 7) {
            category = "Baik";
        } else if (averageScore >= 5) {
            category = "Cukup Baik";
        } else if (averageScore >= 3) {
            category = "Kurang Baik";
        } else {
            category = "Buruk";
        }

        String result = String.format("Hasil Survey:\nNilai Rata-rata: %.2f\nKategori: %s", 
                                    averageScore, category);
        JOptionPane.showMessageDialog(this, result);
        
        saveJawaban(totalScore, averageScore);
    }
 private void saveJawaban(int totalScore, double averageScore) {
        try (Connection conn = konek.GetConnection();
             PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO hasil_survey (result_id, average_score, survey_date) VALUES (?, ?, NOW())")) {
           
            stmt.setDouble(2, averageScore);
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Hasil survey berhasil disimpan!");
            
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
