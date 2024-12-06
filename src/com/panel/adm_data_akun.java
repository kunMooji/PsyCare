
package com.panel;

import com.main.registrasi;
import com.main.registrasiAdmin;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.*;
import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import koneksi.konek;

public class adm_data_akun extends javax.swing.JPanel {

    private Connection connection;

    public adm_data_akun() {
        initComponents();
        
        try {
            connection = konek.GetConnection(); // koneksi ke database
            loadDataToTable(""); //load data awal 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal: " + e.getMessage()); //misalnya koneksi ke db nya gagal
        }

        setLayout(new BorderLayout(10, 10)); // spasi antar elemen
        
        
        //panel header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); 
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); //padding

        JLabel titleLabel = new JLabel("Manajemen Data Akun");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24)); 
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);

        //panel main
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 
        containerPanel.setBackground(Color.WHITE);

        //buat panel atas nya tabel ( buat search)
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE); 
       
        
          JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(tambahAdmin); 
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);

        JTextField searchField = new JTextField(20); //searchbar
        searchField.setToolTipText("Cari berdasarkan username, email, atau nama lengkap");
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent evt) {
                String searchText = searchField.getText().trim();
                loadDataToTable(searchText); // filter data sesuai input
            }
        });

        topPanel.add(new JLabel("Search:")); // label untuk search bar
        topPanel.add(searchField);

        containerPanel.add(topPanel, BorderLayout.NORTH); 

        //tabel akun biar bisa di scroll2
        JScrollPane tableScrollPane = new JScrollPane(tabel_akun);
        containerPanel.add(tableScrollPane, BorderLayout.CENTER); 

        //add header sm panel utama(container) ke layout utama
        add(headerPanel, BorderLayout.NORTH); 
        add(containerPanel, BorderLayout.CENTER); 
    }


    private void loadDataToTable(String filter) {
        try {
            // query buat ambil data akun
            String query = "SELECT id, username, email, role, no_telepon, fullname, tanggal_lahir, created_at FROM akun";
            if (!filter.isEmpty()) { 
                query += " WHERE username LIKE ? OR email LIKE ? OR fullname LIKE ?";
            }

            PreparedStatement pst = connection.prepareStatement(query); // prepare statement
            if (!filter.isEmpty()) { 
                String wildcardFilter = "%" + filter + "%"; // tambahin wildcard buat query LIKE = ..
                pst.setString(1, wildcardFilter);
                pst.setString(2, wildcardFilter);
                pst.setString(3, wildcardFilter);
            }

            ResultSet rs = pst.executeQuery(); // eksekusi query

            DefaultTableModel model = new DefaultTableModel(new String[]{
                "Id Akun", "Username", "Email", "Role", "No Telp", "Nama Lengkap", "Tanggal Lahir", "Akun Dibuat"
            }, 0); // set model tabel

            while (rs.next()) { // looping buat masukin data ke tabel
                int idAkun = rs.getInt("id"); // ambil id akun
                String usernameAkun = rs.getString("username"); // ambil username
                String emailAkun = rs.getString("email"); // ambil email, dst.. ini no telp sm ttl pake setString juga soale di db nya pake varchar
                String roleAkun = rs.getString("role"); 
                String noTelpAkun = rs.getString("no_telepon"); 
                String namaLengkap = rs.getString("fullname"); 
                String ttl = rs.getString("tanggal_lahir"); 
                String createdAt = rs.getString("created_at"); 

                // masukin data ke model
                model.addRow(new Object[]{
                    idAkun, usernameAkun, emailAkun, roleAkun, noTelpAkun, namaLengkap, ttl, createdAt
                });
            }

            tabel_akun.setModel(model); // update model tabel pake data yang terbaru

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage()); // ini klo error ambil data
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_akun = new javax.swing.JTable();
        tambahAdmin = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();

        tabel_akun.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabel_akun.setMaximumSize(new java.awt.Dimension(2147483647, 440));
        tabel_akun.setMinimumSize(new java.awt.Dimension(45, 440));
        tabel_akun.setRowHeight(40);
        tabel_akun.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(tabel_akun);

        tambahAdmin.setText("tambah");
        tambahAdmin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambahAdminActionPerformed(evt);
            }
        });

        delete_btn.setText("delete");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(delete_btn)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tambahAdmin)
                .addContainerGap(721, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(23, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(490, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(delete_btn)
                    .addComponent(tambahAdmin))
                .addGap(57, 57, 57))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(106, 106, 106)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(106, Short.MAX_VALUE)))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tambahAdminActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambahAdminActionPerformed
          // Tampilkan frame registrasi penyakit
        registrasiAdmin registrasiFrame = new registrasiAdmin();
    registrasiFrame.setVisible(true);

    // Tutup frame saat ini
    SwingUtilities.getWindowAncestor(this).dispose();
    }//GEN-LAST:event_tambahAdminActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
  

    }//GEN-LAST:event_delete_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delete_btn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_akun;
    private javax.swing.JButton tambahAdmin;
    // End of variables declaration//GEN-END:variables
}
