package com.panel;

import java.awt.Color;
import java.awt.Font;
import koneksi.konek;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class rs_terdekat extends javax.swing.JPanel {
    private Connection conn; 
    private double[][] kordinatKecamatan; // buat nyimpen koordinat kecamatan
    
   public rs_terdekat() {
    initComponents(); 
    try {
        conn = konek.GetConnection();  
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Koneksi database gagal: " + e.getMessage());  
    }
    loadKecamatanData(); // ngambil data kecamatan
    loadData(); // ngambil data rumah sakit

    // set layout utama, biar rapi
    setLayout(new java.awt.BorderLayout(10, 10));
    setBackground(new java.awt.Color(250, 250, 250));  

    // panel atas buat header 
    JPanel topPanel = new JPanel();
    topPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 15, 10)); // layout flow ke kiri
    topPanel.setBackground(new java.awt.Color(70, 130, 180));  
    topPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10)); 

    // label judul "Rumah Sakit Terdekat"
    jLabel2.setText("Rumah Sakit Terdekat");
    jLabel2.setFont(new Font("Segoe UI", Font.BOLD, 24)); // font 
    jLabel2.setForeground(new java.awt.Color(255, 255, 255));  
    topPanel.add(jLabel2); // tambahin label ke panel atas

    // label "Pilih Kecamatan"
    jLabel1.setText("Pilih Kecamatan:");
    jLabel1.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14)); 
    jLabel1.setForeground(new java.awt.Color(255, 255, 255));  
    topPanel.add(jLabel1); // tambahin label kecamatan ke panel

    // combo box untuk pilih kecamatan
    jComboBoxKecamatan.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 14));  
    topPanel.add(jComboBoxKecamatan); // tambahin combo box ke panel

    // scroll pane buat daftar rumah sakit
    JScrollPane scrollPane = new JScrollPane(jScrollPane1);
    scrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 12, 12, 12));  
    scrollPane.getViewport().setBackground(new java.awt.Color(250, 250, 250));  

    // panel kontainer utama untuk semua elemen
    JPanel containerPanel = new JPanel();
    containerPanel.setLayout(new java.awt.BorderLayout(10, 10)); // layout border tp ad padding
    containerPanel.setBackground(new java.awt.Color(250, 250, 250));  
    containerPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));  
    containerPanel.add(scrollPane, java.awt.BorderLayout.CENTER);  
    containerPanel.add(topPanel, java.awt.BorderLayout.NORTH);  

    // border scrollpane biar keliatan jelas
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));

    add(containerPanel, java.awt.BorderLayout.CENTER);
}

    // function buat ngemuat data kecamatan dari database
    private void loadKecamatanData() {
        String query = "SELECT id_kecamatan, nama_kecamatan, latitude, longitude FROM kecamatan";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            ArrayList<double[]> coordinatesList = new ArrayList<>();
            while (rs.next()) {
                double lat = rs.getDouble("latitude"); // ngambil latitude
                double lon = rs.getDouble("longitude"); // ngambil longitude
                coordinatesList.add(new double[]{lat, lon}); // nambahin koordinat ke list
            }
            kordinatKecamatan = coordinatesList.toArray(new double[coordinatesList.size()][]); // nyimpen koordinat kecamatan
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data kecamatan: " + e.getMessage()); 
        }
    }

    // function buat ngambil data rumah sakit dari database sama sekalian ngitung jaraknya
    private void loadData() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Rumah Sakit");
        model.addColumn("Alamat");
        model.addColumn("Jarak");

        // get kecamatan yang dipilih
        int kecamatanIndex = jComboBoxKecamatan.getSelectedIndex();
        if (kecamatanIndex == -1) {
            JOptionPane.showMessageDialog(this, "Silakan pilih kecamatan"); // misal kecamatan belum dipilih
            return;
        }
        double latKecamatan = kordinatKecamatan[kecamatanIndex][0]; // ngambil latitude kecamatan
        double lonKecamatan = kordinatKecamatan[kecamatanIndex][1]; // ngambil longitude kecamatan

        ArrayList<RumahSakit> hospitals = new ArrayList<>();
        String query = "SELECT nama_rs, alamat, latitude, longitude FROM rumah_sakit";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("nama_rs"); // ngambil nama rumah sakit
                String address = rs.getString("alamat"); // ngambil alamat rumah sakit
                double latRS = rs.getDouble("latitude"); // ngambil latitude rumah sakit
                double lonRS = rs.getDouble("longitude"); // ngambil longitude rumah sakit

                double distance = haversine(latKecamatan, lonKecamatan, latRS, lonRS); // ngitung jarak menggunakan fungsi haversine
                hospitals.add(new RumahSakit(name, address, distance)); // nambahin rumah sakit ke dalam list
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data rumah sakit: " + e.getMessage()); 
        }

        // Mengurutkan rumah sakit berdasarkan jarak
        Collections.sort(hospitals, new Comparator<RumahSakit>() {
            @Override
            public int compare(RumahSakit h1, RumahSakit h2) {
                return Double.compare(h1.getJarak(), h2.getJarak()); // ngebandingin jarak rs
            }
        });

        // nambahin rumah sakit ke dalam model tabel
        for (RumahSakit hospital : hospitals) {
            model.addRow(new Object[]{hospital.getNama(), hospital.getAlamatRS(), String.format("%.2f km", hospital.getJarak())});
        }

        tabel_rs.setModel(model); // nampilin data ke tabel
    }

    // function buat nghitung jarak antara dua titik (lat1, lon1) dan (lat2, lon2) pake rumus Haversine
    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // rad Bumi dalam kilometer
        double latDistance = Math.toRadians(lat2 - lat1); // ngitung selisih latitude dalam radian
        double lonDistance = Math.toRadians(lon2 - lon1); // ngitung selisih longitude dalam radian
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2); // ini rumus Haversine
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a)); // ngitung jarak
        return R * c; // return jarak dalam kilometer
    }

    //class rs buat nyimpen data rs
    class RumahSakit {
        private String nama; 
        private String alamatRS; 
        private double jarak; 

        public RumahSakit(String name, String address, double distance) {
            this.nama = name;
            this.alamatRS = address;
            this.jarak = distance;
        }

        public String getNama() {
            return nama; 
        }

        public String getAlamatRS() {
            return alamatRS; 
        }

        public double getJarak() {
            return jarak; 
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_rs = new javax.swing.JTable();
        jComboBoxKecamatan = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel1.setText("Dari Kecamatan Anda");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(301, 93, -1, -1));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tabel_rs.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tabel_rs.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3"
            }
        ));
        tabel_rs.setFocusTraversalPolicyProvider(true);
        tabel_rs.setRowHeight(50);
        tabel_rs.setShowGrid(false);
        tabel_rs.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(tabel_rs);

        add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(26, 158, 860, 410));

        jComboBoxKecamatan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Ajung", "Ambulu", "Arjasa", "Balung", "Bangsalsari", "Gumukmas", "Jelbuk", "Jenggawah", "Jombang", "Kaliwates", "Kalisat", "Kencong", "Ledokombo", "Mumbulsari", "Pakusari", "Panti", "Patrang", "Puger", "Rambipuji", "Semboro", "Silo", "Sukorambi", "Sukowono", "Sumberbaru", "Sumberjambe", "Sumbersari", "Tanggul", "Tempurejo", "Umbulsari", "Wuluhan", "Mayang" }));
        jComboBoxKecamatan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxKecamatanActionPerformed(evt);
            }
        });
        add(jComboBoxKecamatan, new org.netbeans.lib.awtextra.AbsoluteConstraints(721, 93, -1, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        jLabel2.setText("Berikut List Rumah Sakit Terdekat ");
        add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(233, 61, -1, -1));
    }// </editor-fold>//GEN-END:initComponents

    private void jComboBoxKecamatanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxKecamatanActionPerformed
        loadData();
    }//GEN-LAST:event_jComboBoxKecamatanActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> jComboBoxKecamatan;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_rs;
    // End of variables declaration//GEN-END:variables
}
