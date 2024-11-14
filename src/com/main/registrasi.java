
package com.main;

import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.raven.datechooser.SelectedDate;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JOptionPane;
import koneksi.konek;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;

public class registrasi extends javax.swing.JFrame {
    
    private com.raven.datechooser.DateChooser dateChooser;

    
    public registrasi() {
        initComponents();
        setLocationRelativeTo(null);
        initDateChooser();
    }
    
    private void initDateChooser() {
        dateChooser = new com.raven.datechooser.DateChooser();
        dateChooser.setTextRefernce(tanggal_lahir_txt);
        tanggal_lahir_txt.setEditable(false);
    }

   private void choose_date_btnActionPerformed(java.awt.event.ActionEvent evt) {
    if (dateChooser != null && isShowing()) {
        dateChooser.showPopup(this, 10, 10);

        SelectedDate selectedDate = dateChooser.getSelectedDate();
        if (selectedDate != null) {
            try {

                String inputTanggal = selectedDate.getDay() + "-" + 
                                    selectedDate.getMonth() + "-" + 
                                    selectedDate.getYear();
                
                SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
                
                Date date = inputFormat.parse(inputTanggal);
                String formattedDate = outputFormat.format(date);

                tanggal_lahir_txt.setText(formattedDate);

            } catch (ParseException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, 
                    "Error saat memformat tanggal: " + e.getMessage(),
                    "Error Format Tanggal",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.swing.background();
        login_container = new javax.swing.JPanel();
        password_txt = new javax.swing.JPasswordField();
        email_txt = new javax.swing.JTextField();
        cancel_btn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        fullname_txt = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        username_txt = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        repassword_txt = new javax.swing.JPasswordField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        tanggal_lahir_txt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jComboBoxrole = new javax.swing.JComboBox<>();
        regis_btn = new javax.swing.JButton();
        fullname_txt2 = new javax.swing.JTextField();
        shape31 = new com.swing.Shape3();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        login_container.setBackground(new java.awt.Color(255, 255, 255));
        login_container.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        login_container.add(password_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 260, 250, 30));
        login_container.add(email_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 210, 250, 30));

        cancel_btn.setText("cancel");
        cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancel_btnActionPerformed(evt);
            }
        });
        login_container.add(cancel_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 370, -1, -1));

        jLabel6.setText("role");
        login_container.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 190, 110, -1));
        login_container.add(fullname_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 160, 250, 30));

        jLabel3.setText("re-password");
        login_container.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 290, 160, -1));
        login_container.add(username_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 160, 220, 30));

        jLabel7.setText("username");
        login_container.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 140, 120, -1));
        login_container.add(repassword_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 310, 250, 30));

        jLabel4.setText("password");
        login_container.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 240, 150, -1));

        jLabel5.setText("email");
        login_container.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 190, 130, -1));

        jLabel8.setText("fullname");
        login_container.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 140, 140, -1));
        login_container.add(tanggal_lahir_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 310, 220, 30));

        jLabel9.setText("no telpon");
        login_container.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(590, 240, 110, -1));

        jLabel10.setText("tanggal_lahir");
        login_container.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 290, 110, -1));

        jComboBoxrole.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "user", "admin", " " }));
        login_container.add(jComboBoxrole, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 210, 220, 30));

        regis_btn.setText("registrasi");
        regis_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                regis_btnActionPerformed(evt);
            }
        });
        login_container.add(regis_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 370, -1, -1));
        login_container.add(fullname_txt2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 260, 220, 30));

        shape31.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/picture/logo.png"))); // NOI18N
        shape31.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, 190, 140));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 3, 24)); // NOI18N
        jLabel13.setText("Selamat datang");
        shape31.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 190, 60));

        login_container.add(shape31, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 260, 430));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel11.setText("Registrasi");
        login_container.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 190, 60));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 16)); // NOI18N
        jLabel2.setText("Silakan isi lengkap data diri anda untuk membuat akun");
        login_container.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 60, 410, 40));

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(login_container, javax.swing.GroupLayout.DEFAULT_SIZE, 838, Short.MAX_VALUE)
                .addContainerGap())
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(login_container, javax.swing.GroupLayout.PREFERRED_SIZE, 440, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(background1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 850, 450));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancel_btnActionPerformed
    Login login = new Login();
    this.dispose();
    login.setVisible(true);     
    }//GEN-LAST:event_cancel_btnActionPerformed

    private void regis_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_regis_btnActionPerformed
      String fullname = fullname_txt.getText();
    String username = username_txt.getText();
    String email = email_txt.getText();
    String password = new String(password_txt.getPassword());
    String repassword = new String(repassword_txt.getPassword());
    String role = (String) jComboBoxrole.getSelectedItem();
    String noTelpon = fullname_txt2.getText();
    String inputTanggal = tanggal_lahir_txt.getText();
    String formattedDate = "";

    // Validate password match
    if (!password.equals(repassword)) {
        JOptionPane.showMessageDialog(this, "Password dan Re-password tidak cocok!");
        return;
    }

    // Format the date
    try {
        SimpleDateFormat inputFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = inputFormat.parse(inputTanggal);
        formattedDate = outputFormat.format(date);
    } catch (ParseException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, 
            "Format tanggal tidak valid. Gunakan format dd-MM-yyyy",
            "Error Format Tanggal",
            JOptionPane.ERROR_MESSAGE);
        return;
    }

    String query = "INSERT INTO akun (username, email, password, role, no_telepon, fullname, tanggal_lahir, created_at) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, NOW())";

    try (Connection conn = konek.GetConnection(); 
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setString(1, username);
        stmt.setString(2, email);
        stmt.setString(3, password);
        stmt.setString(4, role);
        stmt.setString(5, noTelpon);
        stmt.setString(6, fullname);
        stmt.setString(7, formattedDate); 
        
        int rowsInserted = stmt.executeUpdate();

        if (rowsInserted > 0) {
            JOptionPane.showMessageDialog(this, "Registrasi berhasil!");
            this.dispose(); 
            new Login().setVisible(true); 
        }
    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Terjadi kesalahan saat registrasi. Coba lagi.");
    }

    }//GEN-LAST:event_regis_btnActionPerformed

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
            java.util.logging.Logger.getLogger(registrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(registrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(registrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(registrasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new registrasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.swing.background background1;
    private javax.swing.JButton cancel_btn;
    private javax.swing.JTextField email_txt;
    private javax.swing.JTextField fullname_txt;
    private javax.swing.JTextField fullname_txt2;
    private javax.swing.JComboBox<String> jComboBoxrole;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel login_container;
    private javax.swing.JPasswordField password_txt;
    private javax.swing.JButton regis_btn;
    private javax.swing.JPasswordField repassword_txt;
    private com.swing.Shape3 shape31;
    private javax.swing.JTextField tanggal_lahir_txt;
    private javax.swing.JTextField username_txt;
    // End of variables declaration//GEN-END:variables
}
