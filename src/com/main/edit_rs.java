/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
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
    public void showHospitalDetails(String hospitalName) {
        Connection conn = null;
        try {
            conn = konek.GetConnection();
            String query = "SELECT * FROM rumah_sakit WHERE nama_rs = ?";
            PreparedStatement pst = conn.prepareStatement(query);
            pst.setString(1, hospitalName);

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
 private void updateHospitalData(String nama, String alamat, String latitude, String longitude) {
    Connection conn = null;
    try {
        conn = konek.GetConnection();

        // SQL Query Update
        String query = "UPDATE rumah_sakit SET nama_rs = ?, alamat = ?, latitude = ?, longitude = ? WHERE nama_rs = ?";
        PreparedStatement pst = conn.prepareStatement(query);
        pst.setString(1, nama); // New name
        pst.setString(2, alamat); // New address
        pst.setString(3, latitude); // New latitude
        pst.setString(4, longitude); // New longitude

        int rowsUpdated = pst.executeUpdate();

        if (rowsUpdated > 0) {
            JOptionPane.showMessageDialog(this, "Data rumah sakit berhasil diperbarui!");
            this.dispose();  // Menutup form setelah update
        } else {
            JOptionPane.showMessageDialog(this, "Gagal memperbarui data rumah sakit!");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error updating data: " + e.getMessage());
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        nama_new = new javax.swing.JTextField();
        alamat_new = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        longitude = new javax.swing.JTextField();
        latitute = new javax.swing.JTextField();
        simpan_upd_btn = new javax.swing.JButton();
        cancel_btn = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        nama_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nama_newActionPerformed(evt);
            }
        });

        alamat_new.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                alamat_newActionPerformed(evt);
            }
        });

        jLabel1.setText("nama rumah sakit");

        jLabel2.setText("longitude");

        jLabel3.setText("alamat rumah sakit");

        jLabel4.setText("latitute");

        latitute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                latituteActionPerformed(evt);
            }
        });

        simpan_upd_btn.setText("simpan");
        simpan_upd_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simpan_upd_btnActionPerformed(evt);
            }
        });

        cancel_btn.setText("cancel");
        cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2)
                        .addComponent(nama_new)
                        .addComponent(alamat_new)
                        .addComponent(jLabel4)
                        .addComponent(longitude)
                        .addComponent(latitute, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(69, Short.MAX_VALUE)
                .addComponent(cancel_btn)
                .addGap(18, 18, 18)
                .addComponent(simpan_upd_btn)
                .addGap(59, 59, 59))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nama_new, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(alamat_new, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(latitute, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(longitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(simpan_upd_btn)
                    .addComponent(cancel_btn))
                .addContainerGap(29, Short.MAX_VALUE))
        );

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
    private javax.swing.JTextField latitute;
    private javax.swing.JTextField longitude;
    private javax.swing.JTextField nama_new;
    private javax.swing.JButton simpan_upd_btn;
    // End of variables declaration//GEN-END:variables
}
