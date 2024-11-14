/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.panel;


import java.awt.GridLayout;
import koneksi.konek;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class adm_data_rs extends javax.swing.JPanel {

     private Connection connection;

    public adm_data_rs() {
        initComponents();
        try {
            connection = konek.GetConnection();
            loadDataToTable();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal: " + e.getMessage());
        }
    }

    private void loadDataToTable() {
        try {
     
            String query = "SELECT id_rs, nama_rs, alamat, latitude, longitude FROM rumah_sakit";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(new String[] {
                "ID RS", "Nama RS", "Alamat", "Latitude", "Longitude" }, 0);

            while (rs.next()) {
                int idRs = rs.getInt("id_rs");
                String namaRs = rs.getString("nama_rs");
                String alamat = rs.getString("alamat");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

                model.addRow(new Object[] { idRs, namaRs, alamat, latitude, longitude });
            }
            
            tabel_rs.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage());
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_rs = new javax.swing.JTable();
        delete_btn = new javax.swing.JButton();
        update_btn = new javax.swing.JButton();
        tambah_rs_btn = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

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

        delete_btn.setText("delete");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        update_btn.setText("update");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        tambah_rs_btn.setText("tambahrs");
        tambah_rs_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambah_rs_btnActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel2.setText("Data Rumah Sakit");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(223, 223, 223)
                .addComponent(jLabel2)
                .addGap(0, 267, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(delete_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(update_btn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tambah_rs_btn)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jLabel2)
                .addGap(51, 51, 51)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete_btn)
                    .addComponent(update_btn)
                    .addComponent(tambah_rs_btn))
                .addContainerGap(65, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
    int row = tabel_rs.getSelectedRow();
        if (row != -1) {
            int idRs = (int) tabel_rs.getValueAt(row, 0);
            deleteRS(idRs);
        } else {
            JOptionPane.showMessageDialog(this, "Silakan pilih rumah sakit untuk dihapus");
        }
    }
         private void deleteRS(int idRs) {
        try {
            String query = "DELETE FROM rumah_sakit WHERE id_rs = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, idRs);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data rumah sakit berhasil dihapus");
                loadDataToTable();  // Reload data setelah penghapusan
            } else {
                JOptionPane.showMessageDialog(this, "Data rumah sakit tidak ditemukan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat menghapus data: " + e.getMessage());
        }
    }//GEN-LAST:event_delete_btnActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
  int selectedRow = tabel_rs.getSelectedRow();
    if (selectedRow != -1) {

        int idRs = (int) tabel_rs.getValueAt(selectedRow, 0);
        String namaRs = (String) tabel_rs.getValueAt(selectedRow, 1);
        String alamat = (String) tabel_rs.getValueAt(selectedRow, 2);
        double latitude = (double) tabel_rs.getValueAt(selectedRow, 3);
        double longitude = (double) tabel_rs.getValueAt(selectedRow, 4);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField namaField = new JTextField(namaRs);
        JTextField alamatField = new JTextField(alamat);
        JTextField latField = new JTextField(String.valueOf(latitude));
        JTextField longField = new JTextField(String.valueOf(longitude));

        panel.add(new JLabel("Nama RS:"));
        panel.add(namaField);
        panel.add(new JLabel("Alamat:"));
        panel.add(alamatField);
        panel.add(new JLabel("Latitude:"));
        panel.add(latField);
        panel.add(new JLabel("Longitude:"));
        panel.add(longField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Update Data Rumah Sakit",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            try {

                String newNama = namaField.getText().trim();
                String newAlamat = alamatField.getText().trim();
                double newLat = Double.parseDouble(latField.getText().trim());
                double newLong = Double.parseDouble(longField.getText().trim());

                if (newNama.isEmpty() || newAlamat.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                    return;
                }


                String query = "UPDATE rumah_sakit SET nama_rs = ?, alamat = ?, latitude = ?, longitude = ? WHERE id_rs = ?";
                PreparedStatement pst = connection.prepareStatement(query);
                pst.setString(1, newNama);
                pst.setString(2, newAlamat);
                pst.setDouble(3, newLat);
                pst.setDouble(4, newLong);
                pst.setInt(5, idRs);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                    loadDataToTable(); // Refresh table
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Latitude dan Longitude harus berupa angka!");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage());
            }
        }
    } else {
        JOptionPane.showMessageDialog(this, "Pilih rumah sakit yang akan diupdate!");
    }

    }//GEN-LAST:event_update_btnActionPerformed

    private void tambah_rs_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambah_rs_btnActionPerformed

    JPanel panel = new JPanel(new GridLayout(4, 2));
    JTextField namaField = new JTextField();
    JTextField alamatField = new JTextField();
    JTextField latField = new JTextField();
    JTextField longField = new JTextField();

    panel.add(new JLabel("Nama RS:"));
    panel.add(namaField);
    panel.add(new JLabel("Alamat:"));
    panel.add(alamatField);
    panel.add(new JLabel("Latitude:"));
    panel.add(latField);
    panel.add(new JLabel("Longitude:"));
    panel.add(longField);

    int result = JOptionPane.showConfirmDialog(null, panel, "Tambah Data Rumah Sakit",
            JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        try {

            String nama = namaField.getText().trim();
            String alamat = alamatField.getText().trim();
            String latText = latField.getText().trim();
            String longText = longField.getText().trim();

            if (nama.isEmpty() || alamat.isEmpty() || latText.isEmpty() || longText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            double latitude = Double.parseDouble(latText);
            double longitude = Double.parseDouble(longText);

            String query = "INSERT INTO rumah_sakit (nama_rs, alamat, latitude, longitude) VALUES (?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, nama);
            pst.setString(2, alamat);
            pst.setDouble(3, latitude);
            pst.setDouble(4, longitude);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
                loadDataToTable(); 
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Latitude dan Longitude harus berupa angka!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error menambahkan data: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_tambah_rs_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delete_btn;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_rs;
    private javax.swing.JButton tambah_rs_btn;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables
}
