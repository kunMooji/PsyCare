package com.panel;
import com.main.Login;
import static com.main.admin.mainPanel;
import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import koneksi.konek;

public class dashboard_admin extends javax.swing.JPanel {
    private adm_data_rs DataRs;
    private adm_data_dokter DataDokter;
    private adm_data_penyakit DataPenyakit;
    private Connection connection;
    
    public dashboard_admin() {
        initComponents();
        
        try {
            connection = konek.GetConnection();
            
            DataRs = new adm_data_rs();
            DataDokter = new adm_data_dokter();
            DataPenyakit = new adm_data_penyakit();
            
            if (connection != null) {
                loadDashboardData(); // Memuat data dashboard saat inisialisasi
            } else {
                JOptionPane.showMessageDialog(this, "Koneksi ke database gagal");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Koneksi ke database gagal: " + e.getMessage());
        }
    }
    
    public void showForm(Component com) {
        // Menampilkan panel tertentu dengan cara mengganti isi mainPanel
        mainPanel.removeAll();
        mainPanel.add(com);
        mainPanel.repaint();
        mainPanel.revalidate();
    }
    
    // Mengambil jumlah data dari tabel tertentu
    private int getCountFromTable(String tableName) {
        int count = 0;
        try {
            String query = "SELECT COUNT(*) AS total FROM " + tableName;
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet rs = pst.executeQuery();
            
            if (rs.next()) {
                count = rs.getInt("total");
            }
            
            rs.close();
            pst.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage());
            e.printStackTrace();
        }
        return count;
    }
    
    private void loadDashboardData() {
        label_dokter_terdaftar.setText(String.valueOf(getCountFromTable("dokter")));
        label_rs_terdaftar.setText(String.valueOf(getCountFromTable("rumah_sakit")));
        label_penyakit_terdaftar.setText(String.valueOf(getCountFromTable("penyakit")));
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
    Login loginFrame = new Login();
    loginFrame.setVisible(true);

    ((javax.swing.JFrame) this.getTopLevelAncestor()).dispose();
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