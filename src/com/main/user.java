
package com.main;

import com.panel.dashboard_user;
import com.panel.info_penyakit;
import com.panel.konsultasi;
import com.panel.rs_terdekat;
import com.panel.survey_mood;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.Timer;

public class user extends javax.swing.JFrame {

 private dashboard_user main;
 private info_penyakit infoPenyakit;
 private rs_terdekat rsTerdekat;
 private konsultasi kawanKonsul;
 private info_penyakit penyakitInfo;
 private survey_mood survey;
   private int userId;
   
    public user() {
        initComponents();
        setLocationRelativeTo(null);
        tampilan1.event(this, background1);
        init();
    }
    
    public void setUserId(int userId) {
        this.userId = userId;
    }
    
        private void initializeSurveyPanel() {
        survey_mood surveyPanel = new survey_mood(userId);
        background1.add(surveyPanel); 
    }

    
    public void init(){
   main = new dashboard_user();
   mainPanel.setLayout(new BorderLayout());
   mainPanel.add(main);
   
   rsTerdekat = new rs_terdekat();
   kawanKonsul = new konsultasi();
   penyakitInfo = new info_penyakit();
   survey   = new survey_mood(userId);
  
   addHoverAnimation(dashboard_btn);
   addHoverAnimation(rs_terdekat_btn1);
   addHoverAnimation(kawan_konsul);
   addHoverAnimation(info_penyakit);
   
}

    public void showForm(Component com){
        mainPanel.removeAll();
        mainPanel.add(com);
        mainPanel.repaint();
        mainPanel.revalidate();
    }
  
    
    //ini sm kya di admin
    private void addHoverAnimation(JLabel label) {
     
        final int ANIMATION_DURATION = 150;
        final int ANIMATION_STEPS = 15;
        final int GROW_SIZE = 10;

        final int originalWidth = label.getWidth();
        final int originalHeight = label.getHeight();
        final Color originalColor = label.getForeground();
        final Color hoverColor = new Color(65, 105, 225);

        Timer growTimer = new Timer(ANIMATION_DURATION / ANIMATION_STEPS, null);
        Timer shrinkTimer = new Timer(ANIMATION_DURATION / ANIMATION_STEPS, null);

        growTimer.addActionListener(new ActionListener() {
            int step = 0;

            @Override
            public void actionPerformed(ActionEvent e) {
                step++;
                float progress = (float) step / ANIMATION_STEPS;

                int newWidth = originalWidth + (int)(GROW_SIZE * progress);
                int newHeight = originalHeight + (int)(GROW_SIZE * progress);

                label.setSize(newWidth, newHeight);

                if (step >= ANIMATION_STEPS) {
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
                float progress = (float) step / ANIMATION_STEPS;

                int newWidth = originalWidth + GROW_SIZE - (int)(GROW_SIZE * progress);
                int newHeight = originalHeight + GROW_SIZE - (int)(GROW_SIZE * progress);


                label.setSize(newWidth, newHeight);


                if (step >= ANIMATION_STEPS) {
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
        rs_terdekat_btn1 = new javax.swing.JLabel();
        kawan_konsul = new javax.swing.JLabel();
        info_penyakit = new javax.swing.JLabel();
        survey_btn = new javax.swing.JLabel();
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

        rs_terdekat_btn1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        rs_terdekat_btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/picture/btn_home.png"))); // NOI18N
        rs_terdekat_btn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rs_terdekat_btn1MouseClicked(evt);
            }
        });

        kawan_konsul.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        kawan_konsul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/picture/kawan_konsul.png"))); // NOI18N
        kawan_konsul.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                kawan_konsulMouseClicked(evt);
            }
        });

        info_penyakit.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        info_penyakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/picture/info_penyakit.png"))); // NOI18N
        info_penyakit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                info_penyakitMouseClicked(evt);
            }
        });

        survey_btn.setText("jLabel1");
        survey_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                survey_btnMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout shape41Layout = new javax.swing.GroupLayout(shape41);
        shape41.setLayout(shape41Layout);
        shape41Layout.setHorizontalGroup(
            shape41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shape41Layout.createSequentialGroup()
                .addGroup(shape41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(shape41Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(shape41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(kawan_konsul, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(info_penyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(rs_terdekat_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dashboard_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(shape41Layout.createSequentialGroup()
                        .addGap(75, 75, 75)
                        .addComponent(survey_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        shape41Layout.setVerticalGroup(
            shape41Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(shape41Layout.createSequentialGroup()
                .addGap(96, 96, 96)
                .addComponent(dashboard_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(rs_terdekat_btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addComponent(kawan_konsul, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(info_penyakit, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addComponent(survey_btn, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
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

    private void rs_terdekat_btn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rs_terdekat_btn1MouseClicked
   showForm(rsTerdekat);
    }//GEN-LAST:event_rs_terdekat_btn1MouseClicked

    private void kawan_konsulMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_kawan_konsulMouseClicked
    showForm(kawanKonsul);
    }//GEN-LAST:event_kawan_konsulMouseClicked

    private void info_penyakitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_info_penyakitMouseClicked
    showForm(penyakitInfo);
    }//GEN-LAST:event_info_penyakitMouseClicked

    private void survey_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_survey_btnMouseClicked
    showForm(survey);
    }//GEN-LAST:event_survey_btnMouseClicked

 
    public static void main(String args[]) {

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new user().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Dasboard;
    private javax.swing.JPanel Header;
    private com.swing.background background1;
    private javax.swing.JLabel dashboard_btn;
    private javax.swing.JLabel info_penyakit;
    private javax.swing.JLabel kawan_konsul;
    public static final com.swing.MainPanel mainPanel = new com.swing.MainPanel();
    private javax.swing.JLabel rs_terdekat_btn1;
    private com.swing.Shape4 shape41;
    private javax.swing.JLabel survey_btn;
    private com.button.Tampilan tampilan1;
    // End of variables declaration//GEN-END:variables
}
