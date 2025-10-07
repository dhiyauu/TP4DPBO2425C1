//Saya Dhiya Ulhaq dengan NIM 2407716 mengerjakan Tugas Praktikum 4 (Java Swing GUI) dalam mata kuliah desain & pemrograman berorientasi objek untuk keberkahan-Nya maka saya tidak akan melakukan kecurangan seperti yang telah di spesifikasikan//

public class Product {
    private String id;
    private String nama;
    private double harga;
    private String kategori;
    private boolean organik;

    public Product(String id, String nama, double harga, String kategori, boolean organik) {
        this.id = id;
        this.nama = nama;
        this.harga = harga;
        this.kategori = kategori;
        this.organik = organik;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setHarga(double harga) {
        this.harga = harga;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setOrganik(boolean organik) { this.organik = organik; }

    public String getId() {
        return this.id;
    }

    public String getNama() {
        return this.nama;
    }

    public double getHarga() {
        return this.harga;
    }

    public String getKategori() {
        return this.kategori;
    }

    public boolean isOrganik() { return this.organik; }
}