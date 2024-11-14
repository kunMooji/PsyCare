package com.panel;

import koneksi.konek;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;
import java.util.ArrayList;


public class konsultasi extends javax.swing.JPanel {
    private Connection connection;
    private JPanel cardPanel;
    private ArrayList<DokterData> dokterList;

    private class DokterData {
        String nama, jamPraktek, noTelp;
        
        public DokterData(String nama, String jamPraktek, String noTelp) {
            this.nama = nama;
            this.jamPraktek = jamPraktek;
            this.noTelp = noTelp;
        }
    }

    public konsultasi() {
        init();
        setupUI();
        loadDokterData();
    }

    private void openWhatsApp(String notelp) {
        try {
            String cleanNumber = notelp.replaceAll("[^0-9]", "");
            
            if (!cleanNumber.startsWith("62") && cleanNumber.startsWith("0")) {
                cleanNumber = "62" + cleanNumber.substring(1);
            }
            
            String url = "https://wa.me/" + cleanNumber;
            
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "error membuka whatsapp: " + e.getMessage(),
                "error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 255, 255));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Daftar Dokter Tersedia");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));
        titlePanel.add(titleLabel);

        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(245, 245, 245));

        cardPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                return new Dimension(getParent().getWidth(), size.height);
            }
        };
        cardPanel.setLayout(new GridLayout(0, 2, 20, 20));
        cardPanel.setBackground(new Color(245, 245, 245));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        add(titlePanel, BorderLayout.NORTH);
        add(wrapperPanel, BorderLayout.CENTER);

        try {
            connection = konek.GetConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error koneksi database: " + e.getMessage());
        }
    }

    private void loadDokterData() {
        dokterList = new ArrayList<>();
        try {
            String query = "SELECT nama_dokter, jam_praktek, no_telp FROM dokter";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                DokterData dokter = new DokterData(
                    rs.getString("nama_dokter"),
                    rs.getString("jam_praktek"),
                    rs.getString("no_telp")
                );
                dokterList.add(dokter);
                addDokterCard(dokter);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage());
        }
    }

    private void addDokterCard(DokterData dokter) {
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setOpaque(false);
        
        JLabel iconLabel = new JLabel("\uD83D\uDC68\u200D⚕️");
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        headerPanel.add(iconLabel, BorderLayout.WEST);

        JLabel nameLabel = new JLabel(dokter.nama);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerPanel.add(nameLabel, BorderLayout.CENTER);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel jamPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        jamPanel.setOpaque(false);
        JLabel jamLabel = new JLabel("⏰ Jam Praktek: " + dokter.jamPraktek);
        jamLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        jamPanel.add(jamLabel);
        
        JPanel telpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        telpPanel.setOpaque(false);
        JLabel telpLabel = new JLabel("📞 " + dokter.noTelp);
        telpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        telpPanel.add(telpLabel);

        infoPanel.add(jamPanel);
        infoPanel.add(Box.createVerticalStrut(5));
        infoPanel.add(telpPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setOpaque(false);

        JButton contactBtn = new JButton("Contact via WhatsApp");
        contactBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        contactBtn.setBackground(new Color(37, 211, 102));
        contactBtn.setForeground(Color.WHITE);
        contactBtn.setFocusPainted(false);
        contactBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        contactBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        contactBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openWhatsApp(dokter.noTelp);
            }
        });

        //efek
        contactBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                contactBtn.setBackground(new Color(32, 176, 86));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                contactBtn.setBackground(new Color(37, 211, 102));
            }
        });

        buttonPanel.add(contactBtn);

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(infoPanel, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        cardPanel.add(card);
    }

    @SuppressWarnings("unchecked")
    private void init() {
        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1116, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1116, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
