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
        setupUI();  //nyiapin tampilan UI
        loadDokterData(); //ngambil data dokter dari database
    }

    private void openWhatsApp(String notelp) {
        try {
            String cleanNumber = notelp.replaceAll("[^0-9]", ""); //hapus semua karakter selain angka, buat format nomor yang bersih
            
            if (!cleanNumber.startsWith("62") && cleanNumber.startsWith("0")) {
                cleanNumber = "62" + cleanNumber.substring(1); //ubah nomor telp yang diawali 0 jadi format internasional Indonesia
            }
            
            String url = "https://wa.me/" + cleanNumber; //bikin link WhatsApp
            Desktop.getDesktop().browse(new URI(url)); //nyalain browser dan buka link WhatsApp
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "error membuka whatsapp: " + e.getMessage(),
                "error",
                JOptionPane.ERROR_MESSAGE); //kalo error muncul pop-up pesan
        }
    }

private void setupUI() {
    setLayout(new BorderLayout(10, 10)); // bikin layout utama, biar rapi ada jarak antar elemen
    setBackground(new Color(245, 245, 245));  

    // panel buat judul di atas
    JPanel titlePanel = new JPanel();
    titlePanel.setBackground(new Color(70, 130, 180));  
    titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // kasih jarak biar gak mepet

    // label buat tulisan judul
    JLabel titleLabel = new JLabel("Daftar Dokter Tersedia");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));  
    titleLabel.setForeground(Color.WHITE);  
    titlePanel.add(titleLabel); // tempelin label judul ke panel

    // panel pembungkus buat nampilin kartu dokter
    JPanel wrapperPanel = new JPanel(new BorderLayout());
    wrapperPanel.setBackground(new Color(245, 245, 245));  

    // panel buat grid kartu dokter
    cardPanel = new JPanel() {
        @Override
        public Dimension getPreferredSize() {
            Dimension size = super.getPreferredSize();
            return new Dimension(getParent().getWidth(), size.height); // lebarnya ikut parent
        }
    };
    cardPanel.setLayout(new GridLayout(0, 2, 20, 20));  
    cardPanel.setBackground(new Color(250, 250, 250));  
    cardPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // kasih padding di pinggiran grid

    // scroll panel buat kartu dokter
    JScrollPane scrollPane = new JScrollPane(cardPanel);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);  
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);  
    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 2));  
    scrollPane.getVerticalScrollBar().setUnitIncrement(16); // biar scrollnya gak lambat

    wrapperPanel.add(scrollPane, BorderLayout.CENTER); // masukin scrollpane ke wrapper panel

    // tambahin komponen ke layout utama
    add(titlePanel, BorderLayout.NORTH);  // judulnya di atas
    add(wrapperPanel, BorderLayout.CENTER);  // grid kartu di tengah

    // bikin koneksi ke database
    try {
        connection = konek.GetConnection(); // nyambungin ke database
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error koneksi database: " + e.getMessage()); // //handle error
    }
}

private void loadDokterData() {
    dokterList = new ArrayList<>(); // bikin list kosong buat nampung data dokter
    try {
        String query = "SELECT nama_dokter, jam_praktek, no_telp FROM dokter"; // query buat ambil data dokter
        Statement stmt = connection.createStatement(); // bikin statement buat jalanin query
        ResultSet rs = stmt.executeQuery(query); // jalanin query, hasilnya masuk ke rs

        while (rs.next()) {
            DokterData dokter = new DokterData(
                rs.getString("nama_dokter"), // ambil nama dokter, dst
                rs.getString("jam_praktek"), 
                rs.getString("no_telp") 
            );
            dokterList.add(dokter); // tambahin dokter ke list
            addDokterCard(dokter); // bikin kartu dokter dan tempelin ke tampilan
        }

    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Error mengambil data: " + e.getMessage()); // kalau error ambil data, kasih tau
    }
}


    private void addDokterCard(DokterData dokter) {
        JPanel card = new JPanel(); 
        card.setLayout(new BorderLayout(10, 10)); //layout untuk kartu dokter
        card.setBackground(Color.WHITE); 
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(230, 230, 230), 1), 
            BorderFactory.createEmptyBorder(15, 15, 15, 15)  
        ));

        JPanel headerPanel = new JPanel(new BorderLayout(10, 0));  
        headerPanel.setOpaque(false); //buat transparan

        JLabel iconLabel = new JLabel("\uD83D\uDC68\u200D⚕️"); //icon dokter
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 32)); //ukuran font icon
        headerPanel.add(iconLabel, BorderLayout.WEST); //masukin icon ke kiri

        JLabel nameLabel = new JLabel(dokter.nama); //masukin nama dokter
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); //set font dan ukuran nama dokter
        headerPanel.add(nameLabel, BorderLayout.CENTER); // nama dokter di tengah

        JPanel infoPanel = new JPanel(); //panel untuk info tambahan dokter
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS)); //susun info tapi set vertikal
        infoPanel.setOpaque(false); //buat transparan
        infoPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); //spacing untuk info panel

        JPanel jamPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); //panel untuk jam praktek
        jamPanel.setOpaque(false); //buat transparan
        JLabel jamLabel = new JLabel("⏰ Jam Praktek: " + dokter.jamPraktek); //label untuk jam praktek
        jamLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14)); //font untuk jam praktek
        jamPanel.add(jamLabel); //add label jam praktek

        JPanel telpPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0)); //panel nomor telepon
        telpPanel.setOpaque(false);  
        JLabel telpLabel = new JLabel("📞 " + dokter.noTelp); //label untuk nomor telepon
        telpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));  
        telpPanel.add(telpLabel); //add nomor telepon ke panel

        infoPanel.add(jamPanel); //masukin jam panel ke info panel
        infoPanel.add(Box.createVerticalStrut(5));  
        infoPanel.add(telpPanel); //masukin telp panel ke info panel

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); //panel untuk tombol
        buttonPanel.setOpaque(false); //buat transparan

        JButton contactBtn = new JButton("Contact via WhatsApp"); //tombol buat chat dokter lewat WhatsApp
        contactBtn.setFont(new Font("Segoe UI", Font.BOLD, 12)); 
        contactBtn.setBackground(new Color(37, 211, 102));  
        contactBtn.setForeground(Color.WHITE); //warna teks tombol
        contactBtn.setFocusPainted(false);  
        contactBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));  
        contactBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); //ganti cursor jadi tangan saat hover
        
        contactBtn.addActionListener(new ActionListener() { //action listener untuk tombol
            @Override
            public void actionPerformed(ActionEvent e) {
                openWhatsApp(dokter.noTelp); //buka WhatsApp ke nomor dokter
            }
        });

        //efek hover pada tombol
        contactBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                contactBtn.setBackground(new Color(32, 176, 86)); //ganti warna tombol saat hover
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                contactBtn.setBackground(new Color(37, 211, 102)); //kembalikan warna tombol ke semula
            }
        });

        buttonPanel.add(contactBtn); //masukin tombol ke panel tombol

        card.add(headerPanel, BorderLayout.NORTH); //masukin header panel ke bagian atas kartu
        card.add(infoPanel, BorderLayout.CENTER); //masukin info panel ke tengah kartu
        card.add(buttonPanel, BorderLayout.SOUTH); //masukin button panel ke bawah kartu

        cardPanel.add(card); //masukin kartu dokter ke panel utama
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
