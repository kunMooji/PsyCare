package com.panel;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import koneksi.konek;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class dashboard_user extends javax.swing.JPanel {

      public dashboard_user() {
        initComponents();
        init();
    }

    private void init() {
        setLayout(new BorderLayout());

        // Chart panel setup
        JPanel chartPanelContainer = new JPanel(new BorderLayout());
        chartPanelContainer.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 20, 10)); // Margin untuk chart
        chartPanelContainer.add(panel_chart, BorderLayout.CENTER);

        // Side panel for mood and score
        JPanel sidePanel = new JPanel();
        sidePanel.setLayout(new BoxLayout(sidePanel, BoxLayout.Y_AXIS));
        sidePanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 10, 20, 20)); // Margin untuk side panel

        // Mood detect panel
        JPanel moodPanel = new JPanel();
        moodPanel.setLayout(new BorderLayout());
        moodPanel.add(new JLabel("Mood: "), BorderLayout.WEST); // Label mood
        moodPanel.add(mood_detect, BorderLayout.CENTER);
        sidePanel.add(moodPanel);

        // Spacer for separation
        sidePanel.add(Box.createVerticalStrut(20)); // Jarak antar panel

        // Score panel
        JPanel scorePanel = new JPanel();
        scorePanel.setLayout(new BorderLayout());
        scorePanel.add(new JLabel("Last Score: "), BorderLayout.WEST); // Label skor
        scorePanel.add(skor_terakhir, BorderLayout.CENTER);
        sidePanel.add(scorePanel);

        // JSplitPane to divide chart and side panel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chartPanelContainer, sidePanel);
        splitPane.setDividerLocation(0.5); // Membagi setengah layar
        splitPane.setResizeWeight(0.5); // Proporsi awal pembagian 50:50
        splitPane.setContinuousLayout(true); // Smooth resizing
        splitPane.setOneTouchExpandable(true); // Tombol untuk expand/collapse

        add(splitPane, BorderLayout.CENTER);

        // Load data and chart
        loadDataSurvey();
        addPieChart();
    }


    
    
  private void addPieChart() {
        try {
            // Create dataset from database
            DefaultPieDataset dataset = createDataset();

            // Create pie chart
            JFreeChart pieChart = ChartFactory.createPieChart(
                    "Distribusi Emosi", // Chart title
                    dataset,            // Dataset
                    true,               // Show legend
                    true,               // Show tooltips
                    false               // URLs not needed
            );
            ChartPanel chartPanel = new ChartPanel(pieChart);
            chartPanel.setVisible(true);

            panel_chart.setLayout(new BorderLayout());
            panel_chart.removeAll();
            panel_chart.add(chartPanel, BorderLayout.CENTER);
            panel_chart.validate();
            panel_chart.repaint();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error creating pie chart: " + e.getMessage());
            e.printStackTrace();
        }
    }


    private DefaultPieDataset createDataset() throws SQLException {
        DefaultPieDataset dataset = new DefaultPieDataset();
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = konek.GetConnection();
            String query = "SELECT average_score FROM hasil_survey Where id = '1'";
            stmt = conn.prepareStatement(query);
            rs = stmt.executeQuery();
            
            int sadCount = 0;
            int angryCount = 0;
            int neutralCount = 0;
            int happyCount = 0;

            // Count mood categories
            while (rs.next()) {
                double averageScore = rs.getDouble("average_score");

                if (averageScore >= 0 && averageScore <= 2.5) {
                    sadCount++;
                } else if (averageScore > 2.5 && averageScore <= 5.0) {
                    angryCount++;
                } else if (averageScore > 5.0 && averageScore <= 7.5) {
                    neutralCount++;
                } else if (averageScore > 7.5 && averageScore <= 10.0) {
                    happyCount++;
                }
            }

            // Add data to the dataset
            dataset.setValue("Sedih", sadCount);
            dataset.setValue("Marah", angryCount);
            dataset.setValue("Netral", neutralCount);
            dataset.setValue("Bahagia", happyCount);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error retrieving mood data: " + e.getMessage());
            throw e; // Re-throw to be handled by caller
        } finally {
            // Close resources in reverse order
            if (rs != null) try { rs.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (stmt != null) try { stmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (conn != null) try { conn.close(); } catch (SQLException e) { e.printStackTrace(); }
        }

        return dataset;
    }
    
    private void loadDataSurvey() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = konek.GetConnection();

            // ngambil skor rata-rata terakhir dari tabel hasil_survey
            String query = "SELECT average_score FROM hasil_survey ORDER BY survey_date DESC LIMIT 1";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            if (rs.next()) {
                
                float skor_rata2 = rs.getFloat("average_score");
                skor_terakhir.setText("Skor terakhir: " + skor_rata2);
                
                    String mood;
              if (skor_rata2 >= 9) {
                  mood = "Mood: Sangat Baik";
              } else if (skor_rata2 >= 7) {
                  mood = "Mood: Baik";
              } else if (skor_rata2 >= 5) {
                  mood = "Mood: Cukup Baik";
              } else if (skor_rata2 >= 3) {
                  mood = "Mood: Kurang Baik";
              } else if (skor_rata2 >= 1) {
                  mood = "Mood: Buruk";
              } else {
                  mood = "Mood: Sangat Buruk";
              }

                mood_detect.setText(mood);
            } else {
                skor_terakhir.setText("Skor terakhir: tidak ada data");
                mood_detect.setText("Mood: tidak ada data");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (ps != null) ps.close(); } catch (Exception e) { e.printStackTrace(); }
            try { if (conn != null) conn.close(); } catch (Exception e) { e.printStackTrace(); }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        mood_detect = new javax.swing.JLabel();
        skor_terakhir = new javax.swing.JLabel();
        panel_chart = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        mood_detect.setText("mood : ?");
        jPanel1.add(mood_detect, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 16, 140, 20));

        skor_terakhir.setText("skor terakhir :  ?");
        jPanel1.add(skor_terakhir, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        panel_chart.setLayout(new java.awt.BorderLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(panel_chart, javax.swing.GroupLayout.PREFERRED_SIZE, 505, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(330, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panel_chart, javax.swing.GroupLayout.PREFERRED_SIZE, 408, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(56, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel mood_detect;
    private javax.swing.JPanel panel_chart;
    private javax.swing.JLabel skor_terakhir;
    // End of variables declaration//GEN-END:variables
}
