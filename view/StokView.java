package view;

import controller.BarangController;
import model.Barang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

public class StokView extends JFrame {
    private DefaultTableModel model;
    private JTable table;
    private File selectedImageFile = null;

    public StokView() {
        setTitle("Stok Barang");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabel barang
        String[] columnNames = {"ID", "Nama", "Harga Beli", "Harga Jual", "Stok"};
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Panel form input
        JPanel formPanel = new JPanel(new GridLayout(6, 2, 5, 5));
        JTextField tfNama = new JTextField();
        JTextField tfHargaBeli = new JTextField();
        JTextField tfStok = new JTextField();
        JButton btnPilihGambar = new JButton("Pilih Gambar");
        JLabel lblGambarDipilih = new JLabel("Belum ada gambar");
        JButton btnTambah = new JButton("Tambah Barang");

        formPanel.setBorder(BorderFactory.createTitledBorder("Tambah Barang Baru"));
        formPanel.add(new JLabel("Nama Barang:"));
        formPanel.add(tfNama);
        formPanel.add(new JLabel("Harga Beli:"));
        formPanel.add(tfHargaBeli);
        formPanel.add(new JLabel("Stok:"));
        formPanel.add(tfStok);
        formPanel.add(btnPilihGambar);
        formPanel.add(lblGambarDipilih);
        formPanel.add(new JLabel(""));
        formPanel.add(btnTambah);

        add(formPanel, BorderLayout.SOUTH);

        // Pilih Gambar
        btnPilihGambar.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int option = fileChooser.showOpenDialog(this);
            if (option == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = fileChooser.getSelectedFile();
                lblGambarDipilih.setText(selectedImageFile.getName());
            }
        });

        // Aksi tambah
        btnTambah.addActionListener(e -> {
            try {
                String nama = tfNama.getText().trim();
                double hargaBeli = Double.parseDouble(tfHargaBeli.getText().trim());
                int stok = Integer.parseInt(tfStok.getText().trim());

                if (selectedImageFile == null) {
                    JOptionPane.showMessageDialog(this, "Pilih gambar terlebih dahulu.");
                    return;
                }

                // Copy gambar ke folder images/
                String folderPath = "images/";
                File folder = new File(folderPath);
                if (!folder.exists()) folder.mkdirs();

                String namaGambar = System.currentTimeMillis() + "_" + selectedImageFile.getName();
                File dest = new File(folder, namaGambar);
                Files.copy(selectedImageFile.toPath(), dest.toPath());

                double hargaJual = hargaBeli * 1.15;

                BarangController controller = new BarangController();
                boolean sukses = controller.tambahBarang(nama, hargaBeli, hargaJual, stok, namaGambar);

                if (sukses) {
                    JOptionPane.showMessageDialog(this, "Barang berhasil ditambahkan!");
                    tfNama.setText("");
                    tfHargaBeli.setText("");
                    tfStok.setText("");
                    lblGambarDipilih.setText("Belum ada gambar");
                    selectedImageFile = null;
                    loadBarang();
                } else {
                    JOptionPane.showMessageDialog(this, "Gagal menambahkan barang.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Input tidak valid.");
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan gambar.");
            }
        });

        loadBarang();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadBarang() {
        model.setRowCount(0);
        BarangController controller = new BarangController();
        ArrayList<Barang> list = controller.getAllBarang();
        for (Barang b : list) {
            model.addRow(new Object[]{
                b.getId(), b.getNama(), b.getHargaBeli(), b.getHargaJual(), b.getStok()
            });
        }
    }
}
