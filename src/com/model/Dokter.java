package com.model;


public class Dokter {

    private int id;
    private String nama;
    private String jamPraktek;
    private String noTelp;

    // Constructor
    public Dokter(int id, String nama, String jamPraktek, String noTelp) {
        this.id = id;
        this.nama = nama;
        this.jamPraktek = jamPraktek;
        this.noTelp = noTelp;
    }

    // Getter dan Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getJamPraktek() {
        return jamPraktek;
    }

    public void setJamPraktek(String jamPraktek) {
        this.jamPraktek = jamPraktek;
    }

    public String getNoTelp() {
        return noTelp;
    }

    public void setNoTelp(String noTelp) {
        this.noTelp = noTelp;
    }

    // Optional: Override toString() untuk memudahkan debugging
    @Override
    public String toString() {
        return "Dokter [id=" + id + ", nama=" + nama + ", jamPraktek=" + jamPraktek + ", noTelp=" + noTelp + "]";
    }
}
