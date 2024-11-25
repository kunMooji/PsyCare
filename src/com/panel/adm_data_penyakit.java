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



public class adm_data_penyakit extends javax.swing.JPanel {

    private Connection connection;

    public adm_data_penyakit() {
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
     
            String query = "SELECT nama_penyakit, deskripsi FROM penyakit";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            DefaultTableModel model = new DefaultTableModel(new String[] {
                "Nama Penyakit ", "Deskirpsi"}, 0);

            while (rs.next()) {
                String namaPenyakit = rs.getString("nama_penyakit");
                String Deskripsi = rs.getString("deskripsi");

                model.addRow(new Object[] { namaPenyakit, Deskripsi});
            }
            
            tabel_data_penyakit.setModel(model);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_data_penyakit = new javax.swing.JTable();
        delete_btn = new javax.swing.JButton();
        update_btn = new javax.swing.JButton();
        tambah_rs_btn = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        tabel_data_penyakit.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_data_penyakit.setFocusTraversalPolicyProvider(true);
        tabel_data_penyakit.setRowHeight(40);
        tabel_data_penyakit.setShowGrid(false);
        tabel_data_penyakit.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(tabel_data_penyakit);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(delete_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(update_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tambah_rs_btn)
                .addContainerGap(645, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(19, 19, 19)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(19, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(566, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete_btn)
                    .addComponent(update_btn)
                    .addComponent(tambah_rs_btn))
                .addGap(41, 41, 41))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(138, 138, 138)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(139, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
         int row = tabel_data_penyakit.getSelectedRow();
        if (row != -1) {
            String namaPenyakit = (String) tabel_data_penyakit.getValueAt(row, 0);
            deletePenyakit(namaPenyakit);
        } else {
            JOptionPane.showMessageDialog(this, "Silakan pilih penyakit untuk dihapus");
        }
    }

    private void deletePenyakit(String namaPenyakit) {
        try {
            String query = "DELETE FROM penyakit WHERE nama_penyakit = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, namaPenyakit);
            
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data penyakit berhasil dihapus");
                loadDataToTable();  // Reload data setelah penghapusan
            } else {
                JOptionPane.showMessageDialog(this, "Data penyakit tidak ditemukan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat menghapus data: " + e.getMessage());
        }
    }//GEN-LAST:event_delete_btnActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
     int selectedRow = tabel_data_penyakit.getSelectedRow();
        if (selectedRow != -1) {
            String namaPenyakit = (String) tabel_data_penyakit.getValueAt(selectedRow, 0);
            String deskripsi = (String) tabel_data_penyakit.getValueAt(selectedRow, 1);

            JPanel panel = new JPanel(new GridLayout(2, 2));
            JTextField namaField = new JTextField(namaPenyakit);
            JTextArea deskripsiArea = new JTextArea(deskripsi, 5, 20);
            deskripsiArea.setLineWrap(true);
            deskripsiArea.setWrapStyleWord(true);
            JScrollPane scrollPane = new JScrollPane(deskripsiArea);

            panel.add(new JLabel("Nama Penyakit:"));
            panel.add(namaField);
            panel.add(new JLabel("Deskripsi:"));
            panel.add(scrollPane);

            int result = JOptionPane.showConfirmDialog(null, panel, "Update Data Penyakit",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String newNama = namaField.getText().trim();
                    String newDeskripsi = deskripsiArea.getText().trim();

                    if (newNama.isEmpty() || newDeskripsi.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                        return;
                    }

                    String query = "UPDATE penyakit SET nama_penyakit = ?, deskripsi = ? WHERE nama_penyakit = ?";
                    PreparedStatement pst = connection.prepareStatement(query);
                    pst.setString(1, newNama);
                    pst.setString(2, newDeskripsi);
                    pst.setString(3, namaPenyakit);

                    int rowsAffected = pst.executeUpdate();
                    if (rowsAffected > 0) {
                        JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
                        loadDataToTable();
                    }
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih penyakit yang akan diupdate!");
        }
    }//GEN-LAST:event_update_btnActionPerformed

    private void tambah_rs_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambah_rs_btnActionPerformed
  JPanel panel = new JPanel(new GridLayout(2, 2));
JTextField namaField = new JTextField();
JTextArea deskripsiArea = new JTextArea(5, 20);
deskripsiArea.setLineWrap(true);
deskripsiArea.setWrapStyleWord(true);
JScrollPane scrollPane = new JScrollPane(deskripsiArea);

panel.add(new JLabel("Nama Penyakit:"));
panel.add(namaField);
panel.add(new JLabel("Deskripsi:"));
panel.add(scrollPane);

int result = JOptionPane.showConfirmDialog(null, panel, "Tambah Data Penyakit",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

if (result == JOptionPane.OK_OPTION) {
    try {
        String nama = namaField.getText().trim();
        String deskripsi = deskripsiArea.getText().trim();

        // Validasi input tidak boleh kosong
        if (nama.isEmpty() || deskripsi.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Semua field harus diisi!");
            return;
        }

        // Query SQL tanpa kolom id_penyakit karena AUTO_INCREMENT
        String query = "INSERT INTO penyakit (nama_penyakit, deskripsi) VALUES (?, ?)";
        PreparedStatement pst = connection.prepareStatement(query);

        // Set parameter
        pst.setString(1, nama);
        pst.setString(2, deskripsi);

        // Eksekusi query
        int rowsAffected = pst.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(null, "Data penyakit berhasil ditambahkan!");
            loadDataToTable(); // Method untuk refresh data di tabel
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Error menambahkan data: " + e.getMessage());
    }
}

    }//GEN-LAST:event_tambah_rs_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delete_btn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_data_penyakit;
    private javax.swing.JButton tambah_rs_btn;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables
}
