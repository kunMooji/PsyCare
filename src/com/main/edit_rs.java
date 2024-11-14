
package com.main;
import koneksi.konek;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

public class edit_rs extends javax.swing.JFrame {

    public edit_rs() {
        initComponents();
        setLocationRelativeTo(null);
    }

    // Method to update hospital data
    public void LoadRSData(String rumahsakitNama) {
        Connection conn = null;
        try {
            conn = konek.GetConnection();
            //select * buat nampilin semua data yang ada di tabel rumah_sakit
            //where rs adalah sbg acuan buat nampilinnya
            //misal SELECT * FROM rumah_sakit WHERE nama_rs = bina sehat
            //maka yang muncul adalah semua data dari bina sehat
            
            String query = "SELECT * FROM rumah_sakit WHERE nama_rs = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, rumahsakitNama);

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
         
                nama_new.setText(rs.getString("nama_rs"));
                alamat_new.setText(rs.getString("alamat"));
                latitute.setText(rs.getString("latitude"));
                longitude.setText(rs.getString("longitude"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error: " + e.getMessage());
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error closing connection: " + e.getMessage());
            }
        }
    }
 private void updateDataRs(String nama, String alamat, String latitude, String longitude) {
    Connection conn = null;
    try {
        conn = konek.GetConnection();

        // query update nya
        String query = "UPDATE rumah_sakit SET nama_rs = ?, alamat = ?, latitude = ?, longitude = ? WHERE nama_rs = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, nama);
        pst.setString(2, alamat); 
        pst.setString(3, latitude);
        pst.setString(4, longitude);

        int rowsUpdated = pst.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Data rumah sakit berhasil diperbarui!");
            this.dispose();//nutup frame update kalo udh kelar
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data rumah sakit!");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "error update data: " + e.getMessage());
    } finally {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "error saat menutup koneksi " + e.getMessage());
        }
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        nama_new = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        alamat_new = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        latitute = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        longitude = new javax.swing.JTextField();
        cancel_btn = new javax.swing.JButton();
        simpan_upd_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("nama rumah sakit");

        nama_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nama_newActionPerformed(evt);
            }
        });

        jLabel3.setText("alamat rumah sakit");

        alamat_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamat_newActionPerformed(evt);
            }
        });

        jLabel4.setText("latitute");

        latitute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                latituteActionPerformed(evt);
            }
        });

        jLabel2.setText("longitude");

        cancel_btn.setText("cancel");
        cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_btnActionPerformed(evt);
            }
        });

        simpan_upd_btn.setText("simpan");
        simpan_upd_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan_upd_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(nama_new, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(alamat_new, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(latitute, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(longitude, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(cancel_btn)
                        .addGap(18, 18, 18)
                        .addComponent(simpan_upd_btn)))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(68, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(6, 6, 6)
                .addComponent(nama_new, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addGap(6, 6, 6)
                .addComponent(alamat_new, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jLabel4)
                .addGap(6, 6, 6)
                .addComponent(latitute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGap(6, 6, 6)
                .addComponent(longitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cancel_btn)
                    .addComponent(simpan_upd_btn))
                .addGap(46, 46, 46))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 290, 380));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void nama_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nama_newActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_nama_newActionPerformed

    private void alamat_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_alamat_newActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_alamat_newActionPerformed

    private void latituteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_latituteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_latituteActionPerformed

    private void simpan_upd_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simpan_upd_btnActionPerformed

    }//GEN-LAST:event_simpan_upd_btnActionPerformed

    private void cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_btnActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancel_btnActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(edit_rs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(edit_rs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(edit_rs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(edit_rs.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new edit_rs().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alamat_new;
    private javax.swing.JButton cancel_btn;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField latitute;
    private javax.swing.JTextField longitude;
    private javax.swing.JTextField nama_new;
    private javax.swing.JButton simpan_upd_btn;
    // End of variables declaration//GEN-END:variables
}
