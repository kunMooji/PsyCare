package com.panel;

import com.main.Login;
import static com.main.admin.mainPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import koneksi.konek;

public class dashboard_admin extends javax.swing.JPanel {
    private adm_data_rs DataRs;
    private adm_data_dokter DataDokter;
    private adm_data_penyakit DataPenyakit;
    private adm_data_akun DataAkun;
    
    private Connection koneksi;
    
    public dashboard_admin() {
        try {
            koneksi = konek.GetConnection();
            
            if (koneksi == null) {
                throw new SQLException("koneksi ke database gagal");
            }
        } catch (SQLException e) {
            // misal eror
            JOptionPane.showMessageDialog(null, "koneksi ke database gagal: " + e.getMessage());
            koneksi = null;
        }
        
        initComponents();
        
        // setting layout panel utama
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // bikin panel isi
        JPanel panelIsi = new JPanel(new BorderLayout());
        panelIsi.setBackground(Color.WHITE);

        // bikin panel statistik dan masukin ke panel isi
        JPanel panelStatistik = PanelStatistik();
        panelIsi.add(panelStatistik, BorderLayout.CENTER);

        // tambahin panel isi ke layout utama
        add(panelIsi, BorderLayout.CENTER);

        // bikin panel bawah buat tombol logout
        JPanel panelBawah = new JPanel(new BorderLayout());
        panelBawah.setBorder(new EmptyBorder(10, 10, 10, 10));
        panelBawah.setBackground(Color.WHITE);

        // tombol logout
        JButton tombolLogout = new JButton("Logout");
        tombolLogout.setFont(new Font("Segoe UI", Font.BOLD, 14));
        tombolLogout.setBackground(new Color(255, 102, 102));
        tombolLogout.setForeground(Color.WHITE);
        tombolLogout.addActionListener(e -> aksiLogout());
        panelBawah.add(tombolLogout, BorderLayout.EAST);

        add(panelBawah, BorderLayout.SOUTH);

        try {
            // inisialisasi data untuk komponen lain lainnya
            DataRs = new adm_data_rs();
            DataDokter = new adm_data_dokter();
            DataPenyakit = new adm_data_penyakit();
            DataAkun = new adm_data_akun();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "gagal inisialisasi komponen: " + e.getMessage());
        }
    }

  private JPanel PanelStatistik() {
    JPanel panelStatistik = new JPanel(new BorderLayout());
    panelStatistik.setBorder(new EmptyBorder(20, 20, 20, 20));
    panelStatistik.setBackground(Color.WHITE);

    
    // judul di atas
    JLabel labelJudul = new JLabel("Selamat Datang, Admin!");
    labelJudul.setFont(new Font("Roboto", Font.BOLD, 24));
    labelJudul.setHorizontalAlignment(SwingConstants.CENTER);
    labelJudul.setForeground(new Color(60, 63, 65));
    panelStatistik.add(labelJudul, BorderLayout.NORTH);

    // panel grid buat statistik akun, dokter, penyakit, dan rumah sakitnya
    JPanel panelGrid = new JPanel(new java.awt.GridLayout(2, 2, 20, 20));
    panelGrid.setBackground(Color.WHITE);
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(10, 10, 10, 10); //pake insets buat margin
    gbc.fill = GridBagConstraints.BOTH;

    String[] labelStatistik = {
        "User Aktif", 
        "Jumlah Dokter Terdaftar", 
        "Jumlah Rumah Sakit Terdaftar", 
        "Jumlah Penyakit"
    };
    JLabel[] labelNilai = {
        label_user_aktif, 
        label_dokter_terdaftar, 
        label_rs_terdaftar, 
        label_penyakit_terdaftar
    };
    JButton[] tombolDetail = {
        jButton3, 
        btn_detail_dokter, 
        btn_detail_rs, 
        btn_detail_penyakit
    };

    for (int i = 0; i < labelStatistik.length; i++) {
        JPanel panelItem = panelItemStatistik(
            labelStatistik[i], 
            labelNilai[i], 
            tombolDetail[i], 
            koneksi != null ? ambilJumlahKolom(getNamaTabel(i)) : 0
        );
        gbc.gridx = i % 2; // 2 kolom
        gbc.gridy = i / 2; // baris baru setiap 2 item
        panelGrid.add(panelItem, gbc);
    }

    panelStatistik.add(panelGrid, BorderLayout.CENTER);
    return panelStatistik;
}

    private JPanel panelItemStatistik(String teksLabel, JLabel labelNilai, JButton tombolDetail, int nilai) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        //label judul
        JLabel labelJudul = new JLabel(teksLabel);
        labelJudul.setFont(new Font("Roboto", Font.BOLD, 20)); 
        labelJudul.setForeground(new Color(60, 63, 65));
        labelJudul.setAlignmentX(Component.CENTER_ALIGNMENT);

        //label jumlah
        labelNilai.setFont(new Font("Roboto", Font.PLAIN, 28));
        labelNilai.setForeground(new Color(33, 150, 243));
        labelNilai.setText(String.valueOf(nilai));
        labelNilai.setAlignmentX(Component.CENTER_ALIGNMENT);

        // tombol detail
        tombolDetail.setFont(new Font("Roboto", Font.BOLD, 16)); 
        tombolDetail.setPreferredSize(new Dimension(120, 40)); 
        tombolDetail.setBackground(new Color(33, 150, 243));
        tombolDetail.setForeground(Color.WHITE);
        tombolDetail.setFocusPainted(false);
        tombolDetail.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        tombolDetail.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        tombolDetail.setAlignmentX(Component.CENTER_ALIGNMENT);

        tombolDetail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tombolDetail.setBackground(new Color(30, 136, 229));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                tombolDetail.setBackground(new Color(33, 150, 243));
            }
        });

        // nambahin komponen ke panel
        panel.add(labelJudul);
        panel.add(Box.createVerticalStrut(15)); // jarak antar elemen
        panel.add(labelNilai);
        panel.add(Box.createVerticalStrut(20)); // jarak antara nilai dan tombol
        panel.add(tombolDetail);

        return panel;
    }

    private void aksiLogout() {
            int confirm = JOptionPane.showConfirmDialog(this, "apakah yakin ingin logout ?", "", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
            ((javax.swing.JFrame) this.getTopLevelAncestor()).dispose();  // nutup frame
        }
    }

    private int ambilJumlahKolom(String namaTabel) {
        // ambil jumlah data di tabel
        if (koneksi == null || namaTabel.isEmpty()) {
            return 0;
        }

        int count = 0;
        try {
            String query = "SELECT COUNT(*) AS total FROM " + namaTabel;
            PreparedStatement pst = koneksi.prepareStatement(query);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                count = rs.getInt("total");
            }

            rs.close();
            pst.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "error saat ambil data: " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }
       private String getNamaTabel(int index) {
        // nyesuaiin nama tabel sesuai index
        switch(index) {
            case 0: return "akun";
            case 1: return "dokter";
            case 2: return "rumah_sakit";
            case 3: return "penyakit";
            default: return "";
        }
    }

    public void showForm(Component com) {
        // buat ganti panel utama
        mainPanel.removeAll();
        mainPanel.add(com, BorderLayout.CENTER);
        mainPanel.repaint();
        mainPanel.revalidate();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        label_user_aktif = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        label_dokter_terdaftar = new javax.swing.JLabel();
        btn_detail_dokter = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        label_rs_terdaftar = new javax.swing.JLabel();
        btn_detail_rs = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        label_penyakit_terdaftar = new javax.swing.JLabel();
        btn_detail_penyakit = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        jLabel1.setText("selamat datang, Admin !");
        add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 10, 420, 102));

        jButton1.setText("logout");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 450, 170, 60));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("user aktif");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 10, 80, -1));

        label_user_aktif.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_user_aktif.setText("...");
        jPanel1.add(label_user_aktif, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 30, -1));

        jButton3.setText("detail");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));

        add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 270, 130));

        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("jumlah dokter terdaftar");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 10, -1, -1));
        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 290, 60));

        label_dokter_terdaftar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_dokter_terdaftar.setText("12");
        jPanel2.add(label_dokter_terdaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 30, -1));

        btn_detail_dokter.setText("detail");
        btn_detail_dokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detail_dokterActionPerformed(evt);
            }
        });
        jPanel2.add(btn_detail_dokter, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 90, -1, -1));

        add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 120, 290, 130));

        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel4.setText("jumlah rumah sakit terdaftar");
        jPanel5.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 10, -1, -1));

        label_rs_terdaftar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_rs_terdaftar.setText("43");
        jPanel5.add(label_rs_terdaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 30, -1));

        btn_detail_rs.setText("detail");
        btn_detail_rs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detail_rsActionPerformed(evt);
            }
        });
        jPanel5.add(btn_detail_rs, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 90, -1, -1));

        add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 270, 270, 130));

        jPanel8.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setText("jumlah penyakit");
        jPanel8.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 10, -1, -1));

        label_penyakit_terdaftar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        label_penyakit_terdaftar.setText("10");
        jPanel8.add(label_penyakit_terdaftar, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 50, 30, -1));

        btn_detail_penyakit.setText("detail");
        btn_detail_penyakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_detail_penyakitActionPerformed(evt);
            }
        });
        jPanel8.add(btn_detail_penyakit, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 90, -1, -1));

        add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(390, 270, 290, 130));
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
       int confirm = JOptionPane.showConfirmDialog(this, "apakah yakin ingin logout ?", "", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            Login loginFrame = new Login();
            loginFrame.setVisible(true);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void btn_detail_dokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detail_dokterActionPerformed
     showForm(DataDokter);
    }//GEN-LAST:event_btn_detail_dokterActionPerformed

    private void btn_detail_rsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detail_rsActionPerformed
      showForm(DataRs);
    }//GEN-LAST:event_btn_detail_rsActionPerformed

    private void btn_detail_penyakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_detail_penyakitActionPerformed
     showForm(DataPenyakit);
    }//GEN-LAST:event_btn_detail_penyakitActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
   showForm(DataAkun);
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_detail_dokter;
    private javax.swing.JButton btn_detail_penyakit;
    private javax.swing.JButton btn_detail_rs;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel label_dokter_terdaftar;
    private javax.swing.JLabel label_penyakit_terdaftar;
    private javax.swing.JLabel label_rs_terdaftar;
    private javax.swing.JLabel label_user_aktif;
    // End of variables declaration//GEN-END:variables
}