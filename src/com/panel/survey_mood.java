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
    private int userId; // ID user buat nyimpen hasil survei
    private List<String> pertanyaan = new ArrayList<>(); // daftar pertanyaan survei
    private Map<Integer, Integer> jawaban = new HashMap<>(); // nyimpen jawaban user
    private int currentPage = 0; // halaman yang saat ini ditampilkan
    private static final int QUESTIONS_PER_PAGE = 5; 
    private static final int TOTAL_QUESTIONS = 10; 
    
    private JPanel pertanyaanPanel; // panel buat nampung pertanyaan
    private List<buttonGroupJawaban> jawabanPanel; 
    private List<JLabel> labelPertanyaan; 
    private JButton nextButton; 
    private JButton submitButton; 
    private JPanel cardPanel; 
    private JPanel buttonPanel; 
    
    private int totalScore = 0; // skor total dihitung berdasarkan jawaban

   public survey_mood(int userId) {
    this.userId = userId; // nyimpen ID pengguna
    initComponents();
    init();
    loadPertanyaanDariDb();
    displayPertanyaan();
}

   private void init() {
    setLayout(new BorderLayout(10, 10)); // atur layout utama 
    setBackground(new Color(245, 245, 245));  

    // panel buat judul
    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(new Color(70, 130, 180));  
    titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  

    JLabel titleLabel = new JLabel("Survei Mood Harian");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));  
    titleLabel.setForeground(Color.WHITE);  
    titlePanel.add(titleLabel); // masukin label judul ke panel

    // panel utama (cardPanel)
    cardPanel = new JPanel(new BorderLayout());
    cardPanel.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2)); 
    cardPanel.setBackground(Color.WHITE);  
    
    // panel buat pertanyaan
    pertanyaanPanel = new JPanel();
    pertanyaanPanel.setLayout(new BoxLayout(pertanyaanPanel, BoxLayout.Y_AXIS)); // layout vertikal buat pertanyaan
    pertanyaanPanel.setBackground(Color.WHITE);  

    // inisialisasi komponen-komponen
    jawabanPanel = new ArrayList<>();
    labelPertanyaan = new ArrayList<>();

    // panel buat tombol navigasi
    buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); 
    buttonPanel.setBackground(new Color(245, 245, 245)); 

    nextButton = new JButton("Pertanyaan Selanjutnya");
    submitButton = new JButton("Kirim");
    submitButton.setVisible(false); // tombol kirim cuma muncul di akhir

    nextButton.setBackground(new Color(70, 130, 180));  
    nextButton.setForeground(Color.WHITE);  
    nextButton.setFocusPainted(false);  
    nextButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // padding dalam tombol

    submitButton.setBackground(new Color(70, 130, 180));  
    submitButton.setForeground(Color.WHITE);
    submitButton.setFocusPainted(false);
    submitButton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

    // action tombol
    nextButton.addActionListener(e -> showNextPage()); // aksi tombol next
    submitButton.addActionListener(e -> hitungSkor()); // aksi tombol kirim

    // masukin tombol ke panel
    buttonPanel.add(nextButton);
    buttonPanel.add(submitButton);

    // scroll pane buat pertanyaan
    JScrollPane scrollPane = new JScrollPane(pertanyaanPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // matiin scroll horizontal
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED); // nyalain scroll vertikal
    scrollPane.setBorder(BorderFactory.createEmptyBorder());  

    // masukin scrollPane dan buttonPanel ke cardPanel
    cardPanel.add(scrollPane, BorderLayout.CENTER); 
    cardPanel.add(buttonPanel, BorderLayout.SOUTH); // tombol navigasi di bawah

    // tambahin panel judul dan cardPanel ke layout utama
    add(titlePanel, BorderLayout.NORTH); 
    add(cardPanel, BorderLayout.CENTER);
}

    // function/fungsi buat ngambil pertanyaan dari database
    private void loadPertanyaanDariDb() {
        try (Connection conn = konek.GetConnection(); 
             PreparedStatement stmt = conn.prepareStatement("SELECT question_text FROM pertanyaan_survey LIMIT ?")) {
            stmt.setInt(1, TOTAL_QUESTIONS); 
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                pertanyaan.add(rs.getString("question_text")); 
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat pertanyaan: " + e.getMessage()); //handle error
        }
    }

   private void displayPertanyaan() {
    pertanyaanPanel.removeAll(); 

    int indexAwal = currentPage * QUESTIONS_PER_PAGE;
    int indexAkhir = Math.min(indexAwal + QUESTIONS_PER_PAGE, pertanyaan.size());

    for (int i = indexAwal; i < indexAkhir; i++) {
        JPanel containerPertanyaan = new JPanel(new BorderLayout());
        containerPertanyaan.setBackground(Color.WHITE);
        
        JLabel labelPertanyaan = new JLabel((i + 1) + ". " + pertanyaan.get(i)); //label buat pertanyaan
        labelPertanyaan.setFont(new Font("Arial", Font.BOLD, 14)); 
        labelPertanyaan.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));//nambahin margin biar rapi

        //panel buat nampung jawaban
        buttonGroupJawaban answerPanel = new buttonGroupJawaban();
        
        containerPertanyaan.add(labelPertanyaan, BorderLayout.NORTH);
        containerPertanyaan.add(answerPanel, BorderLayout.CENTER);
        
        jawabanPanel.add(answerPanel); 
        
        pertanyaanPanel.add(containerPertanyaan); 
        pertanyaanPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
    }

    updateButtonVisibility(); 
    revalidate(); 
    repaint(); // refresh panel
}

    private void showNextPage() {
        int startIndex = currentPage * QUESTIONS_PER_PAGE;
        int endIndex = Math.min(startIndex + QUESTIONS_PER_PAGE, pertanyaan.size());
        
        boolean allAnswered = true;
        // ngemastiin semua pertanyaan pada halaman ini sudah dijawab
        for (int i = 0; i < (endIndex - startIndex); i++) {
            buttonGroupJawaban panel = jawabanPanel.get(i);
            if (panel.getSelectedValue() == -1) {
                allAnswered = false;
                break;
            }
            jawaban.put(startIndex + i, panel.getSelectedValue()); // nyimpen jawaban
        }

        if (!allAnswered) {
            JOptionPane.showMessageDialog(this, "Silakan jawab semua pertanyaan terlebih dahulu.");
            return;
        }

        // buat memperbarui tampilan ke halaman berikutnya
        jawabanPanel.clear();
        labelPertanyaan.clear();
        currentPage++;
        displayPertanyaan();
    }

    // update visibilitas tombol berdasarkan halaman yang ditampilkan
    private void updateButtonVisibility() {
        int totalPages = (int) Math.ceil(pertanyaan.size() / (double) QUESTIONS_PER_PAGE);
        if (currentPage == totalPages - 1) {
            nextButton.setVisible(false); 
            submitButton.setVisible(true); 
        } else {
            nextButton.setVisible(true); 
            submitButton.setVisible(false);
        }
    }

    // ngitung skor berdasarkan jawaban yang diberikan dan menampilkan hasilnya
    private void hitungSkor() {
        int indexAwal = currentPage * QUESTIONS_PER_PAGE;
        int indexAkhir = Math.min(indexAwal + QUESTIONS_PER_PAGE, pertanyaan.size());
        
 
        for (int i = 0; i < (indexAkhir - indexAwal); i++) {
            buttonGroupJawaban panel = jawabanPanel.get(i);
            if (panel.getSelectedValue() != -1) {
                jawaban.put(indexAwal + i, panel.getSelectedValue());
            }
        }

        for (Integer answer : jawaban.values()) {
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
                case 8:
                    this.totalScore += 8;
                    break;
                case 10: 
                    this.totalScore += 10;
                    break;
            }
        }

        double averageScore = (double) totalScore / jawaban.size(); // rata-rata skor
        String result = String.format("Total Nilai: %.2f", averageScore); //  hasil
        JOptionPane.showMessageDialog(this, result);
        
        simpanHasil(averageScore); // save hasil ke database
    }

    // nyimpen hasil survei ke database
    private void simpanHasil(double rata2_skor) {
        try (Connection conn = konek.GetConnection(); 
             PreparedStatement stmt = conn.prepareStatement(
             "INSERT INTO hasil_survey (id, average_score, survey_date) VALUES (?, ?, NOW())")) {
            
            stmt.setInt(1, userId); // nyimpen ID pengguna
            stmt.setDouble(2, rata2_skor); // nyimpen skor rata-rata
            
            stmt.executeUpdate();
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
