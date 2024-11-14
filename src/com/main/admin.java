
package com.main;

import static com.main.user.mainPanel;
import com.panel.adm_data_rs;
import com.panel.dashboard_user;
import com.panel.info_penyakit;
import com.panel.rs_terdekat;
import com.panel.adm_data_dokter;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.Timer;

public class admin extends javax.swing.JFrame {

    private dashboard_user main;
    private info_penyakit infoPenyakit;
    private adm_data_rs DataRs;
    private adm_data_dokter DataDokter;
 
    public admin() {
    initComponents();
    init(); 
    setLocationRelativeTo(null);
    setBackground(new Color(0, 0, 0, 0));
    tampilan1.event(this, background1);
    }

     public void init(){
         //ini buat inisialisasi / deklarasi panel2 yg akan di pake
     main = new dashboard_user();
     mainPanel.setLayout(new BorderLayout());
     mainPanel.add(main);
     DataRs = new adm_data_rs();
     DataDokter = new adm_data_dokter();
     
     addHoverAnimation(dashboard_btn);
     addHoverAnimation(dataDokter);
     addHoverAnimation(data_rs);

    }
    public void showForm(Component com){
        //ini method buat nampilin panel dengan cara ngeremove dulu
        mainPanel.removeAll();
        mainPanel.add(com);
        mainPanel.repaint();
        mainPanel.revalidate();
    }
    
    //ini biar ad animasinya
    private void addHoverAnimation(JLabel label) {
        final int DURASI_ANIMASI = 150; 
        final int PERUBAHAN_ANIMASI = 15;
        final int GROW_SIZE = 10; 

        final int lebar_awal = label.getWidth();
        final int tinggi_awal = label.getHeight();
        final Color warna_ori = label.getForeground();
        final Color hoverColor = new Color(65, 105, 225);

        Timer growTimer = new Timer(DURASI_ANIMASI / PERUBAHAN_ANIMASI, null);
        Timer shrinkTimer = new Timer(DURASI_ANIMASI / PERUBAHAN_ANIMASI, null);

        growTimer.addActionListener(new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                float progress = (float) step / PERUBAHAN_ANIMASI;

                int newWidth = lebar_awal + (int)(GROW_SIZE * progress);
                int newHeight = tinggi_awal + (int)(GROW_SIZE * progress);

                label.setSize(newWidth, newHeight);

                if (step >= PERUBAHAN_ANIMASI) {
                    step = 0;
                    growTimer.stop();
                }
            }
        });

        shrinkTimer.addActionListener(new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                float progress = (float) step / PERUBAHAN_ANIMASI;

                int newWidth = lebar_awal + GROW_SIZE - (int)(GROW_SIZE * progress);
                int newHeight = tinggi_awal + GROW_SIZE - (int)(GROW_SIZE * progress);


                label.setSize(newWidth, newHeight);


                if (step >= PERUBAHAN_ANIMASI) {
                    step = 0;
                    shrinkTimer.stop();
                }
            }
        });

        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                shrinkTimer.stop();
                growTimer.restart();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                growTimer.stop();
                shrinkTimer.restart();
            }
        });
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        background1 = new com.swing.background();
        Dasboard = new javax.swing.JPanel();
        shape41 = new com.swing.Shape4();
        dashboard_btn = new javax.swing.JLabel();
        data_rs = new javax.swing.JLabel();
        dataDokter = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        Header = new javax.swing.JPanel();
        tampilan1 = new com.button.Tampilan();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        javax.swing.GroupLayout mainPanelLayout = new javax.swing.GroupLayout(mainPanel);
        mainPanel.setLayout(mainPanelLayout);
        mainPanelLayout.setHorizontalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 843, Short.MAX_VALUE)
        );
        mainPanelLayout.setVerticalGroup(
            mainPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 528, Short.MAX_VALUE)
        );

        Dasboard.setOpaque(false);

        dashboard_btn.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        dashboard_btn.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/picture/dashboard_btn.png"))); // NOI18N
        dashboard_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dashboard_btnMouseClicked(evt);
            }
        });

        data_rs.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        data_rs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/picture/data_rumah_sakit.png"))); // NOI18N
        data_rs.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                data_rsMouseClicked(evt);
            }
        });

        dataDokter.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        dataDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/picture/data_dokter.png"))); // NOI18N
        dataDokter.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dataDokterMouseClicked(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Verdana", 1, 14)); // NOI18N
        jLabel1.setText("Admin");

        jLabel2.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel2.setText("PsyCare App");

        javax.swing.GroupLayout shape41Layout = new javax.swing.GroupLayout(shape41);
        shape41.setLayout(shape41Layout);
        shape41Layout.setHorizontalGroup(
            shape41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shape41Layout.createSequentialGroup()
                .addGroup(shape41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(shape41Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(shape41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dashboard_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dataDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(data_rs, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(shape41Layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        shape41Layout.setVerticalGroup(
            shape41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shape41Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(78, 78, 78)
                .addComponent(dashboard_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(data_rs, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(dataDokter)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout DasboardLayout = new javax.swing.GroupLayout(Dasboard);
        Dasboard.setLayout(DasboardLayout);
        DasboardLayout.setHorizontalGroup(
            DasboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DasboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(shape41, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        DasboardLayout.setVerticalGroup(
            DasboardLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DasboardLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(shape41, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Header.setOpaque(false);

        javax.swing.GroupLayout HeaderLayout = new javax.swing.GroupLayout(Header);
        Header.setLayout(HeaderLayout);
        HeaderLayout.setHorizontalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, HeaderLayout.createSequentialGroup()
                .addContainerGap(557, Short.MAX_VALUE)
                .addComponent(tampilan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        HeaderLayout.setVerticalGroup(
            HeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(tampilan1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout background1Layout = new javax.swing.GroupLayout(background1);
        background1.setLayout(background1Layout);
        background1Layout.setHorizontalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(Dasboard, javax.swing.GroupLayout.PREFERRED_SIZE, 231, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(323, 323, 323)
                .addComponent(Header, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addGap(238, 238, 238)
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        background1Layout.setVerticalGroup(
            background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Dasboard, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(background1Layout.createSequentialGroup()
                .addComponent(Header, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(564, Short.MAX_VALUE))
            .addGroup(background1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(background1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(mainPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(background1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void dashboard_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dashboard_btnMouseClicked
        showForm(main);
    }//GEN-LAST:event_dashboard_btnMouseClicked

    private void data_rsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_data_rsMouseClicked
        showForm(DataRs);
    }//GEN-LAST:event_data_rsMouseClicked

    private void dataDokterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dataDokterMouseClicked
        showForm(DataDokter);
    }//GEN-LAST:event_dataDokterMouseClicked

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
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(admin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new admin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Dasboard;
    private javax.swing.JPanel Header;
    private com.swing.background background1;
    private javax.swing.JLabel dashboard_btn;
    private javax.swing.JLabel dataDokter;
    private javax.swing.JLabel data_rs;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    public static final com.swing.MainPanel mainPanel = new com.swing.MainPanel();
    private com.swing.Shape4 shape41;
    private com.button.Tampilan tampilan1;
    // End of variables declaration//GEN-END:variables
}
