
package com.panel;

import koneksi.konek;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class rs_terdekat extends javax.swing.JPanel {
    
    private Connection conn;
    private double[][] kordinatKecamatan;
    
    public rs_terdekat() {
        initComponents();
        try {
            conn = konek.GetConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Koneksi database gagal: " + e.getMessage());
        }
        loadKecamatanData();
        loadData();
    }

    private void loadKecamatanData() {

        String query = "SELECT id_kecamatan, nama_kecamatan, latitude, longitude FROM kecamatan";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            ArrayList<double[]> coordinatesList = new ArrayList<>();
            while (rs.next()) {
                double lat = rs.getDouble("latitude");
                double lon = rs.getDouble("longitude");
                coordinatesList.add(new double[]{lat, lon});
            }
            kordinatKecamatan = coordinatesList.toArray(new double[coordinatesList.size()][]);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data kecamatan: " + e.getMessage());
        }
    }

    private void loadData() {
       
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Nama Rumah Sakit");
        model.addColumn("Alamat");
        model.addColumn("Jarak");

        // get selected index kecamatan
        int kecamatanIndex = jComboBoxKecamatan.getSelectedIndex();
        if (kecamatanIndex == -1) {
            JOptionPane.showMessageDialog(this, "Silakan pilih kecamatan");
            return;
        }
        double latKecamatan = kordinatKecamatan[kecamatanIndex][0];
        double lonKecamatan = kordinatKecamatan[kecamatanIndex][1];

     
        ArrayList<RumahSakit> hospitals = new ArrayList<>();
        String query = "SELECT nama_rs, alamat, latitude, longitude FROM rumah_sakit";
        try (Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String name = rs.getString("nama_rs");
                String address = rs.getString("alamat");
                double latRS = rs.getDouble("latitude");
                double lonRS = rs.getDouble("longitude");

                double distance = haversine(latKecamatan, lonKecamatan, latRS, lonRS);
                hospitals.add(new RumahSakit(name, address, distance));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal mengambil data rumah sakit: " + e.getMessage());
        }

        Collections.sort(hospitals, new Comparator<RumahSakit>() {
            @Override
            public int compare(RumahSakit h1, RumahSakit h2) {
                return Double.compare(h1.getJarak(), h2.getJarak());
            }
        });

 
        for (RumahSakit hospital : hospitals) {
            model.addRow(new Object[]{hospital.getNama(), hospital.getAlamatRS(), String.format("%.2f km", hospital.getJarak())});
        }

        tabel_rs.setModel(model);
    }

    public static double haversine(double lat1, double lon1, double lat2, double lon2) {
        final double R = 6371; // Radius of Earth in kilometers
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

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
        tabel_rs.setRowHeight(40);
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
