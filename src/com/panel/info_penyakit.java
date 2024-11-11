/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package com.panel;


import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import koneksi.konek;

public class info_penyakit extends javax.swing.JPanel {

      private Connection connection;
    private JPanel cardPanel;
    private ArrayList<PenyakitData> penyakitList;


    private class PenyakitData {
        String id, nama, deskripsi;
        
        public PenyakitData(String id, String nama, String deskripsi) {
            this.id = id;
            this.nama = nama;
            this.deskripsi = deskripsi;
        }
    }

    public info_penyakit() {
        initComponents();
        setupUI();
        loadPenyakitData();
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 255, 255));

        // Panel untuk judul
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(255, 255, 255));
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel titleLabel = new JLabel("Informasi Penyakit Mental");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(51, 51, 51));
        titlePanel.add(titleLabel);

        // Wrapper panel untuk mengatur lebar maksimum
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.setBackground(new Color(255, 255, 255));

        // Panel untuk cards dengan GridLayout
        cardPanel = new JPanel() {
            @Override
            public Dimension getPreferredSize() {
                Dimension size = super.getPreferredSize();
                return new Dimension(getParent().getWidth(), size.height);
            }
        };
        
        cardPanel.setLayout(new GridLayout(0, 2, 20, 20));
        cardPanel.setBackground(new Color(255, 255, 255));
        cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ScrollPane dengan policy yang tepat
        JScrollPane scrollPane = new JScrollPane(cardPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        // Tambahkan scrollPane ke wrapper
        wrapperPanel.add(scrollPane, BorderLayout.CENTER);

        // Tambahkan panels ke frame
        add(titlePanel, BorderLayout.NORTH);
        add(wrapperPanel, BorderLayout.CENTER);

        // Initialize connection
        try {
            connection = konek.GetConnection();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error koneksi database: " + e.getMessage());
        }
    }

    private void loadPenyakitData() {
        penyakitList = new ArrayList<>();
        try {
            String query = "SELECT id_penyakit, nama_penyakit, deskripsi FROM penyakit";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                PenyakitData penyakit = new PenyakitData(
                    rs.getString("id_penyakit"),
                    rs.getString("nama_penyakit"),
                    rs.getString("deskripsi")
                );
                penyakitList.add(penyakit);
                addPenyakitCard(penyakit);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage());
        }
    }

    private void addPenyakitCard(PenyakitData penyakit) {
        // Buat panel untuk setiap penyakit
        JPanel card = new JPanel();
        card.setLayout(new BorderLayout(10, 10));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        // Panel untuk icon dan nama
        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));
        headerPanel.setOpaque(false);
        
        // Icon penyakit
        JLabel iconLabel = new JLabel("🧠"); // Brain emoji
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32));
        headerPanel.add(iconLabel, BorderLayout.WEST);

        // Nama penyakit
        JLabel nameLabel = new JLabel(penyakit.nama);
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        headerPanel.add(nameLabel, BorderLayout.CENTER);

        // ID penyakit (kecil dan abu-abu)
        JLabel idLabel = new JLabel("ID: " + penyakit.id);
        idLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        idLabel.setForeground(new Color(128, 128, 128));
        headerPanel.add(idLabel, BorderLayout.EAST);

        // Panel untuk deskripsi dengan scroll
        JTextArea descArea = new JTextArea(penyakit.deskripsi);
        descArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descArea.setLineWrap(true);
        descArea.setWrapStyleWord(true);
        descArea.setEditable(false);
        descArea.setBackground(new Color(250, 250, 250));
        descArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane descScroll = new JScrollPane(descArea);
        descScroll.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 240)));
        descScroll.setPreferredSize(new Dimension(0, 120));

        // Panel untuk selengkapnya button
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);



        // Tambahkan semua komponen ke card
        card.add(headerPanel, BorderLayout.NORTH);
        card.add(descScroll, BorderLayout.CENTER);
        card.add(buttonPanel, BorderLayout.SOUTH);

        // Tambahkan card ke panel utama
        cardPanel.add(card);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1104, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 536, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
