package com.panel;

import com.main.Login;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.border.TitledBorder;
import koneksi.konek;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class dashboard_user extends javax.swing.JPanel {

       private int userId; //nyimpen id pengguna
 public dashboard_user(int userId) {
        this.userId = userId; // simpen ID pengguna
        initComponents();
        init();
    }

    //ini biar pas di maximize ngikutin
   private void init() {
    setLayout(new BorderLayout(10, 10));  
    setBackground(new Color(245, 245, 245));  

    // panel judul
    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(new Color(70, 130, 180));  
    titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  

    JLabel titleLabel = new JLabel("Status Mood Harian");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));  
    titleLabel.setForeground(Color.WHITE);  
    titlePanel.add(titleLabel);  

    // panel untuk grafik pie
    JPanel grafikPanelContainer = new JPanel(new BorderLayout());
    grafikPanelContainer.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
        BorderFactory.createEmptyBorder(20, 20, 20, 20)
    ));
    grafikPanelContainer.setBackground(Color.WHITE);  
    grafikPanelContainer.add(panel_chart, BorderLayout.CENTER); // ini biar grafik di tengah

    // panel utk side info
    JPanel sidePanel = new JPanel();
    sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS)); // layputnya di set vertikal
    sidePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding
    sidePanel.setBackground(Color.WHITE); 

    // panel Deteksi Mood
    JPanel moodPanel = new JPanel(new BorderLayout());
    moodPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
        "Deteksi Mood",
        TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION,
        new Font("Segoe UI", Font.BOLD, 14),
        new Color(50, 50, 50)
    ));
    moodPanel.setBackground(Color.WHITE);
    moodPanel.add(mood_detect, BorderLayout.CENTER);  

    // panel Latest Score
    JPanel panelSkor = new JPanel(new BorderLayout());
    panelSkor.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
        "Latest Score",
        TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION,
        new Font("Segoe UI", Font.BOLD, 14)
    ));
    panelSkor.setBackground(Color.WHITE);
    panelSkor.add(skor_terakhir, BorderLayout.CENTER); // Menampilkan skor terakhir

    // panel Status
    panel_status.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(180, 180, 180), 1),
        "Status",
        TitledBorder.DEFAULT_JUSTIFICATION,
        TitledBorder.DEFAULT_POSITION,
        new Font("Segoe UI", Font.BOLD, 14)
    ));
    panel_status.setBackground(Color.WHITE);

    // button Logout
    JButton logout = new JButton("Logout");
    logout.setAlignmentX(Component.CENTER_ALIGNMENT);
    logout.setFont(new Font("Segoe UI", Font.BOLD, 14));
    logout.setBackground(new Color(255, 102, 102));  
    logout.setForeground(Color.WHITE);  
    logout.setFocusPainted(false);  
    logout.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));  
    logout.addActionListener(e -> LogoutAction()); //action event buat logout buttonnyaa

    // add komponen ke side panel
    sidePanel.add(moodPanel);
    sidePanel.add(Box.createVerticalStrut(20));
    sidePanel.add(panelSkor);
    sidePanel.add(Box.createVerticalStrut(20));
    sidePanel.add(panel_status);
    sidePanel.add(Box.createVerticalStrut(20));
    sidePanel.add(logout);

    // split panel buat grafik dan side panel, ngebagi ukuran panel 7;3 7 nya di chart pie nya
    //3 nya di side panelnya
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, grafikPanelContainer, sidePanel);
    splitPane.setDividerLocation(0.7);  
    splitPane.setResizeWeight(0.7); 
    splitPane.setContinuousLayout(true);
    splitPane.setOneTouchExpandable(true);  //ini biar splitpanenya bisa digeser kesana kesini

    // nambahin komponen utama ke layout
    add(titlePanel, BorderLayout.NORTH);  
    add(splitPane, BorderLayout.CENTER);  

    loadDataSurvey();
    grafikPieAdd();
}

    private void LogoutAction() {
        int confirm = JOptionPane.showConfirmDialog(this, "apakah yakin ingin logout ?", "", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            ((javax.swing.JFrame) this.getTopLevelAncestor()).dispose();  // nutup frame
        }
    }

    private void grafikPieAdd() {
        try {
            DefaultPieDataset dataset = createDataset();  // buat dataset
            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Distribusi Emosi",
                    dataset,        
                    true,              
                    true,             
                    false               
            );
            ChartPanel chartPanel = new ChartPanel(pieChart);
            chartPanel.setVisible(true);// set chart jadi visible

            panel_chart.setLayout(new BorderLayout());  // set layout panel chart
            panel_chart.removeAll();
            panel_chart.add(chartPanel, BorderLayout.CENTER);
            panel_chart.validate();
            panel_chart.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "error saat memuat grafik : " + e.getMessage());  // error handling
            e.printStackTrace();
        }
    }

private DefaultPieDataset createDataset() throws SQLException {
    DefaultPieDataset dataset = new DefaultPieDataset();
    try (Connection conn = konek.GetConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT average_score FROM hasil_survey WHERE id = ?")) {
        stmt.setInt(1, userId); // Gunakan id dari login
        try (ResultSet rs = stmt.executeQuery()) {
            int burukHitungan = 0, cukupHitungan = 0, baikHitungan = 0, sangatBaikHitungan = 0;

            while (rs.next()) {
                double averageScore = rs.getDouble("average_score");

                if (averageScore <= 2.5) burukHitungan++;
                else if (averageScore <= 5.0) cukupHitungan++;
                else if (averageScore <= 7.5) baikHitungan++;
                else sangatBaikHitungan++;
            }

            dataset.setValue("buruk", burukHitungan);
            dataset.setValue("cukup", cukupHitungan);
            dataset.setValue("baik", baikHitungan);
            dataset.setValue("sangat baik", sangatBaikHitungan);
        }
    }
    return dataset;
}


private void loadDataSurvey() {
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = konek.GetConnection();

        // query buat dapetin skor terakhir berdasarkan ID pengguna
        String query = "SELECT average_score FROM hasil_survey WHERE id = ? ORDER BY survey_date DESC LIMIT 1";
        ps = conn.prepareStatement(query);
        ps.setInt(1, userId); // gunakan userId dari konstruktor

        System.out.println("User ID: " + userId);
        System.out.println("Query executed: " + query);

        rs = ps.executeQuery();

        if (rs.next()) {
            // ngambil skor rata-rata
            float skor_rata2 = rs.getFloat("average_score");
            skor_terakhir.setText("Skor terakhir: " + skor_rata2);

            // nentuin mood berdasarkan skor
            String mood = (skor_rata2 >= 9) ? "Mood: Sangat Baik" :
                          (skor_rata2 >= 7) ? "Mood: Baik" :
                          (skor_rata2 >= 5) ? "Mood: Cukup Baik" :
                          (skor_rata2 >= 3) ? "Mood: Kurang Baik" :
                          (skor_rata2 >= 1) ? "Mood: Buruk" : "Mood: Sangat Buruk";
            mood_detect.setText(mood);

            //  status (Aman/Tidak Aman)
            String status = (skor_rata2 >= 3) ? "Aman" : "Tidak Aman";
            status_yorno.setText("Status: " + status);

            // update border status panel
            panel_status.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180), 1),
                "Status: " + status,
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)
            ));

            // refresh tampilan panel
            panel_status.revalidate();
            panel_status.repaint();

        } else {
            // if tidak ada data ditemukan
            skor_terakhir.setText("Skor terakhir: Tidak Ada Data");
            mood_detect.setText("Mood: Tidak Ada Data");
            status_yorno.setText("Status: Tidak Ada Data");
            panel_status.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(new java.awt.Color(180, 180, 180), 1),
                "Status: Tidak Ada Data",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new java.awt.Font("Segoe UI", java.awt.Font.BOLD, 14)
            ));
        }
    } catch (SQLException e) {
        e.printStackTrace(); // log error ke console
    } finally {
        // close semua resource untuk mencegah memory leaks
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mood_detect = new javax.swing.JLabel();
        skor_terakhir = new javax.swing.JLabel();
        panel_chart = new javax.swing.JPanel();
        panel_status = new javax.swing.JPanel();
        status_yorno = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mood_detect.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mood_detect.setText("mood : ?");
        jPanel1.add(mood_detect, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 140, 20));

        skor_terakhir.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        skor_terakhir.setText("skor terakhir :  ?");
        jPanel1.add(skor_terakhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, -1, -1));

        panel_chart.setBackground(new java.awt.Color(255, 255, 255));
        panel_chart.setLayout(new java.awt.BorderLayout());

        status_yorno.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        status_yorno.setText("status : ");

        javax.swing.GroupLayout panel_statusLayout = new javax.swing.GroupLayout(panel_status);
        panel_status.setLayout(panel_statusLayout);
        panel_statusLayout.setHorizontalGroup(
            panel_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_statusLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(status_yorno, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_statusLayout.setVerticalGroup(
            panel_statusLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel_statusLayout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(status_yorno)
                .addContainerGap(34, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(panel_chart, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                    .addComponent(panel_status, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(297, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(108, 108, 108)
                        .addComponent(panel_status, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(panel_chart, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel mood_detect;
    private javax.swing.JPanel panel_chart;
    private javax.swing.JPanel panel_status;
    private javax.swing.JLabel skor_terakhir;
    private javax.swing.JLabel status_yorno;
    // End of variables declaration//GEN-END:variables
}
