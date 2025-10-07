# TP4DPBO2425C1
## Janji
Saya Dhiya Ulhaq dengan NIM 2407716 mengerjakan Tugas Praktikum 4 (Java Swing GUI) dalam mata kuliah desain & pemrograman berorientasi objek untuk keberkahan-Nya maka saya tidak akan melakukan kecurangan seperti yang telah di spesifikasikan. Aamiin.

## Penjelasan Desain Program

Program ini adalah aplikasi CRUD sederhana berbasis Java Swing untuk mengelola data produk makanan, sehingga dibuat kelas yang bernama `Product`.

### Atribut

Di dalamnya terdapat beberapa atribut, diantaranya:
1. ID : Identitas unik tiap produk agar membedakan produk jika nama produk sama.
2. Nama : Identitas deskriptif saat menampilkan daftar produk.
3. Harga : Harga dipakai saat menampilkan produk, transaksi, atau perhitungan.
4. Kategori : Untuk mengelompokkan produk ke dalam “jenis” yang sejenis pada produk.
5. Organik : Untuk menandai apakah produk itu organik (true) atau non-organik (false).

### Getter & Setter

Terdapat getter dan setter untuk akses/mengubah data produk, diantaranya:

`getId()` : Mengambil nilai ID produk

`getNama()` : Mengambil nama produk

`getHarga()` : Mengambil harga produk

`getKategori()` : Mengambil kategori produk.

`isOrganik()` : Membaca nilai field private organik pada kelas `Product`.

`setId(String id)` : Mengubah nilai ID produk.

`setNama(string nama)` : Mengubah nama produk.

`setHarga(double harga)` : Mengubah harga produk.

`setKategori(String kategori)` : Mengubah kategori produk.

`setOrganik(boolean organik)` : Mengubah tanda apakah produk itu organik (true) atau non-organik (false).

### Alokasi `ArrayList<Product> listProduct`

Menyimpan semua produk dalam bentuk objek `Product`.

### Desain UI

<img src="E:\Dhiya Ulhaq\Tugas\UPI\Desain Pemrograman Berorientasi Objek\Praktikum\TP4\Desain UI.png" width="600"/>

Terdapat komponen di `ProductMenu` yang berisikan: 

- *JTextField* untuk mengisi **id/nama/harga**.
- *JComboBox* untuk memilih **kategori** produk.
- *JRadioButton* untuk memilih **opsi organik** (Ya/Tidak).
- *JTable* untuk menampilkan daftar produk.
- *Tombol Add/Cancel/Delete* yang berasal dari CRUD.
- Label judul.

Tabel tidak mengedit langsung, ia menampilkan salinan data dari *listProduct* lewat `DefaultTableModel` yang dibuat setiap kali `setTable()` dipanggil.

## Alur Program

### a. Main Membuat Instance `new ProductMenu().`
```java
public static void main(String[] args) {
        // buat object window
        ProductMenu menu = new ProductMenu();
        ...
```
### b. Panggil `populateList()`
```java
private void populateList() {
        listProduct.add(new Product("M001", "Bayam Segar 250g", 8000.0,  "Sayur", true));
        listProduct.add(new Product("M002", "Brokoli 300g", 14000.0, "Sayur", true));
        listProduct.add(new Product("M003", "Apel Fuji 1kg", 35000.0, "Buah", true));
        ...
```
Konstruktor `ProductMenu` memanggil `populateList()` untuk mengisi 15 produk yang terdiri dari id, nama, harga, kategori, dan jenis organik/anorganik.

### c. Atur Opsi ComboBox dan ButtonGroup
```java
// atur isi combo box
String[] kategoriData = { "???", "Sayur", "Buah", "Daging & Unggas", "Makanan Laut", "Susu & Olahan", "Roti & Bakery", "Makanan Beku", "Makanan Kaleng", "Camilan/Snack" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // Kelompokkan radio agar saling eksklusif
        organikGroup = new ButtonGroup();
        if (organikYes != null) organikGroup.add(organikYes);
        if (organikNo  != null) organikGroup.add(organikNo);

        // Default: Tidak
        if (organikNo != null) organikNo.setSelected(true);
```
Mengatur isi `KategoricComboBox` untuk kategori produk makanan serta membuat `ButtonGroup` untuk opsi organik pada *RadioButton*.

### d. Tambah Data
```java
public void insertData() {
    try {
                // ambil value dari textfield dan combobox
                String id = idField.getText();
                String nama = namaField.getText();
                double harga = Double.parseDouble(hargaField.getText());
                String kategori = kategoriComboBox.getSelectedItem().toString();
                boolean organik = (organikYes != null && organikYes.isSelected());
                ...
```
Menambahkan data setelah menekan tombol "Add", maka Item baru muncul di baris paling bawah tabel.

### e. Ubah Data
```java
public void updateData() {
        try {
            // ambil data dari form
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            boolean organik = (organikYes != null && organikYes.isSelected());
            ...
```
Mengubah data dengan menekan baris di tabel (`MouseListener`/`ListSelectionListener`) lalu bisa mengubah isi data tersebut.

### g. Hapus Data
```java
public void deleteData() {
        // hapus data dari list
        listProduct.remove(selectedIndex);

        // update tabel
        productTable.setModel(setTable());

        // bersihkan form
        clearForm();
        ...
```
Menghapus data dengan menekan baris di tabel (`MouseListener`/`ListSelectionListener`) lalu akan mendapatkan konfirmasi terlebih dahulu dari `deleteButton.addActionListener(new ActionListener()` untuk menghapus data produk.

### h. Tombol *Cancel*
```java
public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);
        ...
```
Tombol ini saat ditekan akan selalu kembali ke mode Add dan siap input baru karena menggunakan controller `clearForm()`.
## Dokumentasi
<img src="E:\Dhiya Ulhaq\Tugas\UPI\Desain Pemrograman Berorientasi Objek\Praktikum\TP4\Dokumentasi TP4.mp4" width="600"/>