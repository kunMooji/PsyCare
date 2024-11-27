package com.panel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import koneksi.konek;

public class adm_data_dokter extends javax.swing.JPanel {

    private Connection connection;
    private JTextField searchField;

    public adm_data_dokter() {
        initComponents();

        try {
            connection = konek.GetConnection(); 
            loadDataToTable(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal: " + e.getMessage());
        }

        // set layout utama
        setLayout(new BorderLayout(10, 10)); //spasi antar elemen

        // panel header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(70, 130, 180)); 
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        JLabel titleLabel = new JLabel("Manajemen Data Dokter");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE); 
        headerPanel.add(titleLabel);

        // panel utama
        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        containerPanel.setBackground(Color.WHITE);

        // searchpanel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(Color.WHITE);
        searchField = new JTextField(20);
        searchField.setToolTipText("Cari berdasarkan nama dokter");
        searchField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchFieldKeyReleased(evt); // filter saat admin nge inputin 
            }
        });
        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);

        containerPanel.add(searchPanel, BorderLayout.NORTH);

        containerPanel.add(jScrollPane1, BorderLayout.CENTER);
        
        // panel buat button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(tambah_dokter); 
        buttonPanel.add(update_btn);
        buttonPanel.add(delete_btn); 

        //ini naruh button biar posisinya ada di bawah
        containerPanel.add(buttonPanel, BorderLayout.SOUTH);

        // tambhin panel header sama container ke layout utama
        add(headerPanel, BorderLayout.NORTH);
        add(containerPanel, BorderLayout.CENTER);
    }

    private void searchFieldKeyReleased(java.awt.event.KeyEvent evt) {
        String searchText = searchField.getText().trim(); // ambil teks dari search field
        loadDataToTable(searchText); // filter data dokter berdasarkan inputan yg dimasukin
    }

    private void loadDataToTable() {
        loadDataToTable(""); // load data dokter tanpa filter, kosong
    }

    private void loadDataToTable(String searchText) {
        try {
            String query = "SELECT id_dokter, nama_dokter, jam_praktek, no_telp FROM dokter";
            if (searchText != null && !searchText.isEmpty()) {
                query += " WHERE nama_dokter LIKE ?";
            }

            PreparedStatement pst = connection.prepareStatement(query);
            if (searchText != null && !searchText.isEmpty()) {
                pst.setString(1, "%" + searchText + "%"); //pencarian berdasarkan nama dokter
            }

            ResultSet rs = pst.executeQuery();

            DefaultTableModel model = new DefaultTableModel(new String[] {
                "ID Dokter", "Nama Dokter", "Jam Praktek", "No Telp" }, 0);

            while (rs.next()) {
                int idDok = rs.getInt("id_dokter");
                String namaDok = rs.getString("nama_dokter");
                String jamDok = rs.getString("jam_praktek");
                String noTelp = rs.getString("no_telp");

                model.addRow(new Object[] { idDok, namaDok, jamDok, noTelp }); // tambahin data dokter ke tabel
            }

            tabel_dokter.setModel(model); // update model tabel pake data yg paling baru 

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage()); // error saat ngambil data
        }
    }

    private void deleteDokter(int idDokter) {
        try {
            String query = "DELETE FROM dokter WHERE id_dokter = ?";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, idDokter); // set id dokter yang mau dihapus
            int rowsAffected = pst.executeUpdate(); // eksekusi query delete

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data dokter berhasil dihapus");
                loadDataToTable(); // reload data pas uda dihapus
            } else {
                JOptionPane.showMessageDialog(this, "Data dokter tidak ditemukan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saat menghapus data: " + e.getMessage()); // error saat hapus
        }
    }


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tabel_dokter = new javax.swing.JTable();
        tambah_dokter = new javax.swing.JButton();
        update_btn = new javax.swing.JButton();
        delete_btn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setBackground(new java.awt.Color(255, 255, 255));

        tabel_dokter.setModel(new javax.swing.table.DefaultTableModel(
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
        tabel_dokter.setMaximumSize(new java.awt.Dimension(2147483647, 440));
        tabel_dokter.setMinimumSize(new java.awt.Dimension(45, 440));
        tabel_dokter.setRowHeight(40);
        tabel_dokter.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(tabel_dokter);

        tambah_dokter.setText("tambah");
        tambah_dokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tambah_dokterActionPerformed(evt);
            }
        });

        update_btn.setText("update");
        update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_btnActionPerformed(evt);
            }
        });

        delete_btn.setText("delete");
        delete_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                delete_btnActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 48)); // NOI18N
        jLabel1.setText("Data Dokter");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(300, 300, 300)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 858, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(delete_btn)
                                .addGap(6, 6, 6)
                                .addComponent(update_btn)
                                .addGap(6, 6, 6)
                                .addComponent(tambah_dokter)))))
                .addGap(26, 26, 26))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 358, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(delete_btn)
                    .addComponent(update_btn)
                    .addComponent(tambah_dokter))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tambah_dokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tambah_dokterActionPerformed
     JPanel panel = new JPanel(new GridLayout(3, 2));
    JTextField namaField = new JTextField();
    JTextField jamField = new JTextField();
    JTextField notelpField = new JTextField();

    panel.add(new JLabel("Nama Dokter:"));
    panel.add(namaField);
    panel.add(new JLabel("Jam Praktek:"));
    panel.add(jamField);
    panel.add(new JLabel("No Telp Dokter:"));
    panel.add(notelpField);

    int result = JOptionPane.showConfirmDialog(null, panel, "Tambah Data Dokter",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {
        try {
            String namaDokter = namaField.getText().trim();
            String jamPraktek = jamField.getText().trim();
            String noTelpDokter = notelpField.getText().trim();

            if (namaDokter.isEmpty() || jamPraktek.isEmpty() || noTelpDokter.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Semua field harus diisi!");
                return;
            }

            String query = "INSERT INTO dokter (nama_dokter, jam_praktek, no_telp) VALUES (?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, namaDokter);
            pst.setString(2, jamPraktek);
            pst.setString(3, noTelpDokter);

            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "Data dokter berhasil ditambahkan!");
                loadDataToTable(); 
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error menambahkan data: " + e.getMessage());
        }
    }
    }//GEN-LAST:event_tambah_dokterActionPerformed

    private void update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_btnActionPerformed
    int selectedRow = tabel_dokter.getSelectedRow();
if (selectedRow == -1) {  
    JOptionPane.showMessageDialog(this, "Pilih dokter yang akan diupdate!");  // ngecek ap ada dokter yang dipilih
    return;
}
try {
    DefaultTableModel model = (DefaultTableModel) tabel_dokter.getModel();
    int idDokter = Integer.parseInt(model.getValueAt(selectedRow, 0).toString());  // ambil id dokter, dst
    String currentNama = model.getValueAt(selectedRow, 1).toString(); 
    String currentJam = model.getValueAt(selectedRow, 2).toString();  
    String currentNoTelp = model.getValueAt(selectedRow, 3).toString();  

    JPanel panel = new JPanel(new GridLayout(3, 2));
    JTextField namaField = new JTextField(currentNama);
    JTextField jamField = new JTextField(currentJam);
    JTextField notelpField = new JTextField(currentNoTelp);

    panel.add(new JLabel("Nama Dokter:"));
    panel.add(namaField);
    panel.add(new JLabel("Jam Praktek:"));
    panel.add(jamField);
    panel.add(new JLabel("No Telp Dokter:"));
    panel.add(notelpField);

    int result = JOptionPane.showConfirmDialog(null, panel, "Update Data Dokter",
        JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

    if (result == JOptionPane.OK_OPTION) {  
        // ambil input baru
        String newNamaDokter = namaField.getText().trim();
        String newJamPraktek = jamField.getText().trim();
        String newNoTelpDokter = notelpField.getText().trim();

        if (newNamaDokter.isEmpty() || newJamPraktek.isEmpty() || newNoTelpDokter.isEmpty()) {  
            JOptionPane.showMessageDialog(this, "Semua field harus diisi!");  
            return;
        }

        // validasi no telp
        if (!newNoTelpDokter.matches("\\d+")) {  // cek no telp harus angka
            JOptionPane.showMessageDialog(this, "Nomor telepon harus berupa angka!");  
            return;
        }

        // konfirmasi update
        int konfirmasi = JOptionPane.showConfirmDialog(this,
            "Apakah Anda yakin ingin mengupdate data dokter ini?",
            "Konfirmasi Update", JOptionPane.YES_NO_OPTION);

        if (konfirmasi == JOptionPane.YES_OPTION) {  
            // query update
            String query = "UPDATE dokter SET nama_dokter=?, jam_praktek=?, no_telp=? WHERE id_dokter=?";
            try (PreparedStatement pst = connection.prepareStatement(query)) {
                pst.setString(1, newNamaDokter);
                pst.setString(2, newJamPraktek);
                pst.setString(3, newNoTelpDokter);
                pst.setInt(4, idDokter);

                int rowsAffected = pst.executeUpdate();
                if (rowsAffected > 0) {  
                    JOptionPane.showMessageDialog(this, "Data dokter berhasil diupdate!");  
                    loadDataToTable();  
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal mengupdate data dokter!");  
                }
            }
        }
    }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error saat mengupdate data: " + e.getMessage());  
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Format data tidak valid!");  // validasi format
    }

    }//GEN-LAST:event_update_btnActionPerformed

    private void delete_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_delete_btnActionPerformed
        int selectedRow = tabel_dokter.getSelectedRow();
        if (selectedRow != -1) {
            int idDokter = (int) tabel_dokter.getValueAt(selectedRow, 0);
            
            int confirm = JOptionPane.showConfirmDialog(this,
                "apakah anda yakin ingin menghapus data dokter ini?",
                "Konfirmasi Hapus",
                JOptionPane.YES_NO_OPTION);
                
            if (confirm == JOptionPane.YES_OPTION) {
                deleteDokter(idDokter);
            }
        } else {
            JOptionPane.showMessageDialog(this, "silakan pilih dulu dokter yang ingin dihapus!");
        }

    }//GEN-LAST:event_delete_btnActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton delete_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tabel_dokter;
    private javax.swing.JButton tambah_dokter;
    private javax.swing.JButton update_btn;
    // End of variables declaration//GEN-END:variables
}
