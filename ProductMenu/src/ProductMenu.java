//Saya Dhiya Ulhaq dengan NIM 2407716 mengerjakan Tugas Praktikum 4 (Java Swing GUI) dalam mata kuliah desain & pemrograman berorientasi objek untuk keberkahan-Nya maka saya tidak akan melakukan kecurangan seperti yang telah di spesifikasikan//

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Locale;

public class ProductMenu extends JFrame {
    public static void main(String[] args) {
        // buat object window
        ProductMenu menu = new ProductMenu();

        // atur ukuran window
        menu.setSize(700, 600);

        // letakkan window di tengah layar
        menu.setLocationRelativeTo(null);

        // isi window
        menu.setContentPane(menu.mainPanel);

        // ubah warna background
        menu.getContentPane().setBackground(Color.WHITE);

        // tampilkan window
        menu.setVisible(true);

        // agar program ikut berhenti saat window diclose
        menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    // index baris yang diklik
    private int selectedIndex = -1;
    // list untuk menampung semua produk
    private ArrayList<Product> listProduct;

    private JPanel mainPanel;
    private JTextField idField;
    private JTextField namaField;
    private JTextField hargaField;
    private JTable productTable;
    private JButton addUpdateButton;
    private JButton cancelButton;
    private JComboBox<String> kategoriComboBox;
    private JButton deleteButton;
    private JLabel titleLabel;
    private JLabel idLabel;
    private JLabel namaLabel;
    private JLabel hargaLabel;
    private JLabel kategoriLabel;
    private JLabel organikLabel;
    private JRadioButton organikYes;
    private JRadioButton organikNo;
    private ButtonGroup organikGroup;

    // constructor
    public ProductMenu() {
        // inisialisasi listProduct
        listProduct = new ArrayList<>();

        // isi listProduct
        populateList();

        // isi tabel produk
        productTable.setModel(setTable());

        // ubah styling title
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 20f));

        // atur isi combo box
        String[] kategoriData = { "???", "Sayur", "Buah", "Daging & Unggas", "Makanan Laut", "Susu & Olahan", "Roti & Bakery", "Makanan Beku", "Makanan Kaleng", "Camilan/Snack" };
        kategoriComboBox.setModel(new DefaultComboBoxModel<>(kategoriData));

        // Kelompokkan radio agar saling eksklusif
        organikGroup = new ButtonGroup();
        if (organikYes != null) organikGroup.add(organikYes);
        if (organikNo  != null) organikGroup.add(organikNo);

        // Default: Tidak
        if (organikNo != null) organikNo.setSelected(true);

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // saat tombol add/update ditekan
        addUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (selectedIndex == -1) {
                    insertData();
                } else {
                    updateData();
                }
            }
        });

        // saat tombol delete ditekan
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO: tambahkan konfirmasi sebelum menghapus data
                // Pastikan ada baris yang terpilih
                if (selectedIndex < 0 || selectedIndex >= listProduct.size()) {
                    JOptionPane.showMessageDialog(
                            ProductMenu.this,
                            "Pilih dulu data yang akan dihapus.",
                            "Tidak ada seleksi",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    return;
                }

                // Ambil info produk untuk ditampilkan di konfirmasi
                Product p = listProduct.get(selectedIndex);
                String pesan = String.format(
                        "Yakin ingin menghapus data ini?\n"
                );

                // Tampilkan konfirmasi dengan tombol kustom
                Object[] opsi = {"Hapus", "Batal"};
                int pilih = JOptionPane.showOptionDialog(
                        ProductMenu.this,      // parent
                        pesan,                  // message
                        "Konfirmasi Hapus",     // title
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        opsi,
                        opsi[1]                 // default: Batal
                );

                if (pilih == 0) { // "Hapus" ditekan
                    // Lakukan proses hapus
                    deleteData();
                    // Opsional: kembalikan fokus ke tabel
                    productTable.requestFocusInWindow();
                } else {
                    // Batal: tidak melakukan apa-apa (opsional beri info singkat)
                    productTable.requestFocusInWindow();
                }
            }
        });
        // saat tombol cancel ditekan
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearForm();
            }
        });
        // saat salah satu baris tabel ditekan
        productTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                // ubah selectedIndex menjadi baris tabel yang diklik
                selectedIndex = productTable.getSelectedRow();

                // simpan value textfield dan combo box
                String curId = productTable.getModel().getValueAt(selectedIndex, 1).toString();
                String curNama = productTable.getModel().getValueAt(selectedIndex, 2).toString();
                String curHarga = productTable.getModel().getValueAt(selectedIndex, 3).toString();
                String curKategori = productTable.getModel().getValueAt(selectedIndex, 4).toString();

                // ubah isi textfield dan combo box
                idField.setText(curId);
                namaField.setText(curNama);
                hargaField.setText(curHarga);
                kategoriComboBox.setSelectedItem(curKategori);

                //Tombol radio
                setOrganikRadioSafe(listProduct.get(selectedIndex));

                // ubah button "Add" menjadi "Update"
                addUpdateButton.setText("Update");

                // tampilkan button delete
                deleteButton.setVisible(true);

            }
        });
    }

    public final DefaultTableModel setTable() {
        // tentukan kolom tabel
        Object[] cols = { "No", "ID produk", "Nama", "Harga", "Kategori", "Organik?" };

        // buat objek tabel dengan kolom yang sudah dibuat
        DefaultTableModel tmp = new DefaultTableModel( null, cols);

        // isi tabel dengan listProduct
        for (int i = 0; i < listProduct.size(); i++) {
            Object[] row = { i + 1,
                    listProduct.get(i).getId(),
                    listProduct.get(i).getNama(),
                    String.format(Locale.US, "%.2f", listProduct.get(i).getHarga()),
                    listProduct.get(i).getKategori(),
                    listProduct.get(i).isOrganik() ? "Ya" : "Tidak"
            };
            tmp.addRow(row);
        }

        return tmp; // return juga harus diganti
    }

    public void insertData() {
        try {
            // ambil value dari textfield dan combobox
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            boolean organik = (organikYes != null && organikYes.isSelected());

            // tambahkan data ke dalam list
            listProduct.add(new Product(id, nama, harga, kategori, organik));

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Insert berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil ditambahkan");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog( null, "Harga harus berupa angka!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void updateData() {
        try {
            // ambil data dari form
            String id = idField.getText();
            String nama = namaField.getText();
            double harga = Double.parseDouble(hargaField.getText());
            String kategori = kategoriComboBox.getSelectedItem().toString();
            boolean organik = (organikYes != null && organikYes.isSelected());

            // ubah data produk di list
            listProduct.get(selectedIndex).setId(id);
            listProduct.get(selectedIndex).setNama(nama);
            listProduct.get(selectedIndex).setHarga(harga);
            listProduct.get(selectedIndex).setKategori(kategori);
            listProduct.get(selectedIndex).setOrganik(organik);

            // update tabel
            productTable.setModel(setTable());

            // bersihkan form
            clearForm();

            // feedback
            System.out.println("Update berhasil");
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog( null, "Harga harus berupa angka!", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

    }

    public void deleteData() {
        // hapus data dari list
        listProduct.remove(selectedIndex);

        // update tabel
        productTable.setModel(setTable());

        // bersihkan form
        clearForm();

        // feedback
        System.out.println("Delete berhasil");
        JOptionPane.showMessageDialog(null, "Data berhasil dihapus");

    }

    public void clearForm() {
        // kosongkan semua texfield dan combo box
        idField.setText("");
        namaField.setText("");
        hargaField.setText("");
        kategoriComboBox.setSelectedIndex(0);

        // reset radio: default Tidak
        if (organikNo != null) organikNo.setSelected(true);

        // ubah button "Update" menjadi "Add"
        addUpdateButton.setText("Add");

        // sembunyikan button delete
        deleteButton.setVisible(false);

        // ubah selectedIndex menjadi -1 (tidak ada baris yang dipilih)
        selectedIndex = -1;

    }

    private void setOrganikRadioSafe(Product p) {
        if (organikYes == null || organikNo == null) return; // belum ter-bind, skip
        if (p != null && p.isOrganik()) {
            organikYes.setSelected(true);
        } else {
            organikNo.setSelected(true);
        }
    }


    // panggil prosedur ini untuk mengisi list produk
    private void populateList() {
        listProduct.add(new Product("M001", "Bayam Segar 250g",          8000.0,  "Sayur", true));
        listProduct.add(new Product("M002", "Brokoli 300g",              14000.0, "Sayur", true));
        listProduct.add(new Product("M003", "Apel Fuji 1kg",             35000.0, "Buah",  true));
        listProduct.add(new Product("M004", "Pisang Cavendish 1 sisir",  28000.0, "Buah",  true));
        listProduct.add(new Product("M005", "Dada Ayam Fillet 500g",     45000.0, "Daging & Unggas", true));
        listProduct.add(new Product("M006", "Daging Sapi Has Dalam 500g",95000.0, "Daging & Unggas", true));
        listProduct.add(new Product("M007", "Ikan Salmon Fillet 250g",   85000.0, "Makanan Laut", true));
        listProduct.add(new Product("M008", "Udang Vannamei 500g",       60000.0, "Makanan Laut", true));
        listProduct.add(new Product("M009", "Susu UHT Full Cream 1L",    12000.0, "Susu & Olahan", false));
        listProduct.add(new Product("M010", "Keju Cheddar 170g",         28000.0, "Susu & Olahan", false));
        listProduct.add(new Product("M011", "Roti Tawar",                15000.0, "Roti & Bakery", false));
        listProduct.add(new Product("M012", "Croissant",                 12000.0, "Roti & Bakery", false));
        listProduct.add(new Product("M013", "Nugget Ayam 500g",          38000.0, "Makanan Beku", false));
        listProduct.add(new Product("M014", "Sarden Kaleng 155g",        18000.0, "Makanan Kaleng", false));
        listProduct.add(new Product("M015", "Keripik Kentang 68g",       14000.0, "Camilan/Snack", false));
    }
}