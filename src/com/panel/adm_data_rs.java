
package com.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
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
        loadDataToTable(""); //ngambil data ke tabel tanpa filter awal / load
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Koneksi ke database gagal: " + e.getMessage());
    }

    // set layout utama
    setLayout(new BorderLayout(10, 10)); // spasi antar elemen

    // Panel header
    JPanel headerPanel = new JPanel();
    headerPanel.setBackground(new Color(70, 130, 180));  
    headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));  

    JLabel titleLabel = new JLabel("Manajemen Data Rumah Sakit");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
    titleLabel.setForeground(Color.WHITE);  
    headerPanel.add(titleLabel);

    // panel utama
    JPanel containerPanel = new JPanel(new BorderLayout());
    containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    containerPanel.setBackground(Color.WHITE);

    // panel pencarian
    JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    searchPanel.setBackground(Color.WHITE); // Latar belakang putih
    JTextField searchField = new JTextField(20);
    searchField.setToolTipText("Cari berdasarkan nama RS");
    searchField.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            String searchText = searchField.getText().trim();
            loadDataToTable(searchText); // Panggil ulang data dengan filter pencarian
        }
    });
    searchPanel.add(new JLabel("Search:"));
    searchPanel.add(searchField);

    // tambahin panel search ke atas tabel
    containerPanel.add(searchPanel, BorderLayout.NORTH);

    // tambahin tabel ke containerPanel
    containerPanel.add(jScrollPane1, BorderLayout.CENTER);

    // panel buat button
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    buttonPanel.setBackground(Color.WHITE);
    buttonPanel.add(tambah_rs_btn);  
    buttonPanel.add(update_btn);  
    buttonPanel.add(delete_btn);  

    // buttonPanel di bagian bawah
    containerPanel.add(buttonPanel, BorderLayout.SOUTH);

    add(headerPanel, BorderLayout.NORTH);
    add(containerPanel, BorderLayout.CENTER);
}

    // ini fungsi buat nge muat data ke tabel dengan filter
    private void loadDataToTable(String filter) {
        try {
            String query = "SELECT id_rs, nama_rs, alamat, latitude, longitude FROM rumah_sakit";
            if (!filter.isEmpty()) {
                query += " WHERE nama_rs LIKE ?";  //filter pake LIKE = ?
            }

            PreparedStatement pst = connection.prepareStatement(query);
            if (!filter.isEmpty()) {
                pst.setString(1, "%" + filter + "%"); //pake nama rs
            }

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[] {
                "ID RS", "Nama RS", "Alamat", "Latitude", "Longitude" }, 0);

            while (rs.next()) {
                int idRs = rs.getInt("id_rs");
                String namaRs = rs.getString("nama_rs");
                String alamat = rs.getString("alamat");
                double latitude = rs.getDouble("latitude");
                double longitude = rs.getDouble("longitude");

                model.addRow(new Object[] { idRs, namaRs, alamat, latitude, longitude });  // nambahin data ke tabel
            }

            tabel_rs.setModel(model);  // set model tabel

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage());  // handle error 
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
            //query delete pake whre id yg dipiilih
            String query = "DELETE FROM rumah_sakit WHERE id_rs = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, idRs);
            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data rumah sakit berhasil dihapus");
                  loadDataToTable("");  // reload data setelah hapus data
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

       // ambil data rumah sakit dari tabel
       int idRs = (int) tabel_rs.getValueAt(selectedRow, 0);
       String namaRs = (String) tabel_rs.getValueAt(selectedRow, 1);
       String alamat = (String) tabel_rs.getValueAt(selectedRow, 2);
       double latitude = (double) tabel_rs.getValueAt(selectedRow, 3);
       double longitude = (double) tabel_rs.getValueAt(selectedRow, 4);

       // buat panel untuk form update
       JPanel panel = new JPanel(new GridLayout(4, 2));
       JTextField namaField = new JTextField(namaRs);
       JTextField alamatField = new JTextField(alamat);
       JTextField latField = new JTextField(String.valueOf(latitude));
       JTextField longField = new JTextField(String.valueOf(longitude));

       // nambahin komponen ke panel
       panel.add(new JLabel("nama rs:"));
       panel.add(namaField);
       panel.add(new JLabel("alamat:"));
       panel.add(alamatField);
       panel.add(new JLabel("latitude:"));
       panel.add(latField);
       panel.add(new JLabel("longitude:"));
       panel.add(longField);

       // tampilkan dialog buat update
       int result = JOptionPane.showConfirmDialog(null, panel, "update data rumah sakit",
               JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

       if (result == JOptionPane.OK_OPTION) {
           try {
               // ambil data baru dari form
               String newNama = namaField.getText().trim();
               String newAlamat = alamatField.getText().trim();
               double newLat = Double.parseDouble(latField.getText().trim());
               double newLong = Double.parseDouble(longField.getText().trim());

               // cek apakah field kosong apa nggak
               if (newNama.isEmpty() || newAlamat.isEmpty()) {
                   JOptionPane.showMessageDialog(this, "semua field harus diisi!");
                   return;
               }

               // query update rumah sakit where id rs
               String query = "UPDATE rumah_sakit SET nama_rs = ?, alamat = ?, latitude = ?, longitude = ? WHERE id_rs = ?";
               PreparedStatement pst = connection.prepareStatement(query);
               pst.setString(1, newNama);
               pst.setString(2, newAlamat);
               pst.setDouble(3, newLat);
               pst.setDouble(4, newLong);
               pst.setInt(5, idRs);

               // eksekusi update
               int rowsAffected = pst.executeUpdate();
               if (rowsAffected > 0) {
                   JOptionPane.showMessageDialog(this, "data berhasil diupdate!");
                   loadDataToTable(""); // refresh tabel
               }
           } catch (NumberFormatException e) {
               // misal format angkanya salah
               JOptionPane.showMessageDialog(this, "latitude dan longitude harus berupa angka!");
           } catch (SQLException e) {
               JOptionPane.showMessageDialog(this, "error updating data: " + e.getMessage()); // handle error
           }
       }
   } else {
       // jika belum mmilih rumah sakit
       JOptionPane.showMessageDialog(this, "pilih rumah sakit yang akan diupdate!");
   }


    }//GEN-LAST:event_update_btnActionPerformed

    private void tambah_rs_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambah_rs_btnActionPerformed

        // buat panel grid layout buat input data rumah sakit
     JPanel panel = new JPanel(new GridLayout(4, 2));
     JTextField namaField = new JTextField();
     JTextField alamatField = new JTextField();
     JTextField latField = new JTextField();
     JTextField longField = new JTextField();

     // nambahin label sama field input ke panel
     panel.add(new JLabel("nama rs:"));
     panel.add(namaField);
     panel.add(new JLabel("alamat:"));
     panel.add(alamatField);
     panel.add(new JLabel("latitude:"));
     panel.add(latField);
     panel.add(new JLabel("longitude:"));
     panel.add(longField);

     // nampilin dialog buat nambah data rumah sakit
     int result = JOptionPane.showConfirmDialog(null, panel, "tambah data rumah sakit",
             JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

     // jika admin klik ok
     if (result == JOptionPane.OK_OPTION) {
         try {
             // ambil data dari field input
             String nama = namaField.getText().trim();
             String alamat = alamatField.getText().trim();
             String latText = latField.getText().trim();
             String longText = longField.getText().trim();

             // ngecek apakah ada field yang kosong
             if (nama.isEmpty() || alamat.isEmpty() || latText.isEmpty() || longText.isEmpty()) {
                 JOptionPane.showMessageDialog(this, "semua field harus diisi!");
                 return;
             }

             // konversi latitude dan longitude ke tipe double
             double latitude = Double.parseDouble(latText);
             double longitude = Double.parseDouble(longText);

             // query memasukkan data rumah sakit ke database
             String query = "INSERT INTO rumah_sakit (nama_rs, alamat, latitude, longitude) VALUES (?, ?, ?, ?)";
             PreparedStatement pst = connection.prepareStatement(query);
             pst.setString(1, nama);
             pst.setString(2, alamat);
             pst.setDouble(3, latitude);
             pst.setDouble(4, longitude);

             // eksekusi query dan cek apakah data udah bisa ditambahkan
             int rowsAffected = pst.executeUpdate();
             if (rowsAffected > 0) {
                 JOptionPane.showMessageDialog(this, "data berhasil ditambahkan!");
                 loadDataToTable(""); // refresh tabel pas data sudah ditambahkan
             }
         } catch (NumberFormatException e) {
             // error waktu latitude atau longitude bukan angka
             JOptionPane.showMessageDialog(this, "latitude dan longitude harus berupa angka!");
         } catch (SQLException e) {
             // error waktu eksekusi query
             JOptionPane.showMessageDialog(this, "error menambahkan data: " + e.getMessage());
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
