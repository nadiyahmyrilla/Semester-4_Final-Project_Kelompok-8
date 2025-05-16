package controllerGUI;

import javax.swing.*;
import controller.*;
import model.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.awt.Font;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

public class PenjualanControllerGUI extends JPanel {
    private JPanel daftarBarangPanel, daftarPembelianPanel;
    private JLabel totalLabel;
    private JButton btnBayar;
    private JTextField tfNamaPelanggan;
    private Map<Barang, Integer> keranjang = new LinkedHashMap<>();

    public PenjualanControllerGUI() {
        setLayout(new BorderLayout(10, 10));

        daftarBarangPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JScrollPane scrollBarang = new JScrollPane(daftarBarangPanel);

        daftarPembelianPanel = new JPanel();
        daftarPembelianPanel.setLayout(new BoxLayout(daftarPembelianPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollDaftar = new JScrollPane(daftarPembelianPanel);

        JPanel kananPanel = new JPanel(new BorderLayout(5, 5));

        // Input nama pelanggan
        JPanel atasPanel = new JPanel(new BorderLayout(5, 5));
        JLabel lblNama = new JLabel("Nama Pelanggan: ");
        tfNamaPelanggan = new JTextField();
        atasPanel.add(lblNama, BorderLayout.WEST);
        atasPanel.add(tfNamaPelanggan, BorderLayout.CENTER);

        JPanel bawahPanel = new JPanel(new BorderLayout(5, 5));
        totalLabel = new JLabel("Total: Rp0");
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        btnBayar = new JButton("Bayar");
        btnBayar.addActionListener(e -> prosesBayar());

        bawahPanel.add(totalLabel, BorderLayout.CENTER);
        bawahPanel.add(btnBayar, BorderLayout.SOUTH);

        kananPanel.add(atasPanel, BorderLayout.NORTH);
        kananPanel.add(scrollDaftar, BorderLayout.CENTER);
        kananPanel.add(bawahPanel, BorderLayout.SOUTH);

        add(scrollBarang, BorderLayout.CENTER);
        add(kananPanel, BorderLayout.EAST);

        tampilkanBarang();
    }

    private void tampilkanBarang() {
        BarangController bc = new BarangController();
        for (Barang b : bc.getAllBarang()) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createTitledBorder(b.getNama()));
            itemPanel.setBackground(Color.lightGray);

            JLabel labelHarga = new JLabel("Harga: Rp" + new DecimalFormat("#,###.00").format(b.getHargaJual()));
            JButton btnTambah = new JButton("Tambah");

            btnTambah.addActionListener(e -> tambahBarang(b));

            itemPanel.add(labelHarga, BorderLayout.CENTER);
            itemPanel.add(btnTambah, BorderLayout.EAST);

            daftarBarangPanel.add(itemPanel);
        }

        revalidate();
        repaint();
    }

    private void tambahBarang(Barang barang) {
        String input = JOptionPane.showInputDialog(this, "Masukkan jumlah untuk " + barang.getNama());
        if (input == null || input.isBlank()) return;

        try {
            int jumlah = Integer.parseInt(input);
            if (jumlah <= 0) throw new NumberFormatException();

            keranjang.put(barang, keranjang.getOrDefault(barang, 0) + jumlah);
            updateDaftarPembelian();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah tidak valid.");
        }
    }

    private void updateDaftarPembelian() {
        daftarPembelianPanel.removeAll();
        double total = 0;

        for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
            Barang b = entry.getKey();
            int jumlah = entry.getValue();
            double subtotal = b.getHargaJual() * jumlah;
            total += subtotal;

            JPanel itemPanel = new JPanel(new BorderLayout());
            JLabel itemLabel = new JLabel(b.getNama() + " x" + jumlah + " = Rp" + new DecimalFormat("#,###.00").format(subtotal));
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 14));

            JPanel tombolPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton btnEdit = new JButton("Edit");
            JButton btnHapus = new JButton("Hapus");

            btnEdit.addActionListener(e -> editJumlah(b));
            btnHapus.addActionListener(e -> {
                keranjang.remove(b);
                updateDaftarPembelian();
            });

            tombolPanel.add(btnEdit);
            tombolPanel.add(btnHapus);

            itemPanel.add(itemLabel, BorderLayout.CENTER);
            itemPanel.add(tombolPanel, BorderLayout.EAST);

            daftarPembelianPanel.add(itemPanel);
        }

        totalLabel.setText("Total: Rp" + new DecimalFormat("#,###.00").format(total));
        revalidate();
        repaint();
    }

    private void editJumlah(Barang barang) {
        String input = JOptionPane.showInputDialog(this, "Ubah jumlah untuk " + barang.getNama(), keranjang.get(barang));
        if (input == null || input.isBlank()) return;

        try {
            int jumlahBaru = Integer.parseInt(input);
            if (jumlahBaru <= 0) {
                keranjang.remove(barang);
            } else {
                keranjang.put(barang, jumlahBaru);
            }
            updateDaftarPembelian();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Jumlah tidak valid.");
        }
    }

    private void prosesBayar() {
        if (keranjang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada barang yang dibeli.");
            return;
        }

        String namaPelanggan = tfNamaPelanggan.getText().trim();
        if (namaPelanggan.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama pelanggan tidak boleh kosong.");
            return;
        }

        String[] options = {"Tunai", "Cicilan"};
        int pilihan = JOptionPane.showOptionDialog(this, "Pilih metode pembayaran", "Metode Pembayaran",
                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

        if (pilihan == JOptionPane.CLOSED_OPTION) return;

        boolean isTunai = (pilihan == 0);
        double totalPembelian = hitungTotalPembelian();
        BarangController bc = new BarangController();
        PemasukanController pec = new PemasukanController();

        //
        if (isTunai) {
        try {
        double pembayaran = Double.parseDouble(JOptionPane.showInputDialog(this, "Masukkan jumlah pembayaran (Rp)"));
        if (pembayaran >= totalPembelian) {
            double kembalian = pembayaran - totalPembelian;
            JOptionPane.showMessageDialog(this, "Pembayaran sukses! Kembalian: Rp" + new DecimalFormat("#,###.00").format(kembalian));

            //Simpan ke tabel penjualan
            PenjualanController pc = new PenjualanController();
            int idPenjualan = pc.tambahPenjualan(new Penjualan(0, new Date(), totalPembelian, "tunai", "lunas", namaPelanggan));

            if (idPenjualan == -1) {
                JOptionPane.showMessageDialog(this, "Gagal menyimpan penjualan.");
                return;
            }

            // Simpan ke detail_penjualan & kurangi stok
            for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
                Barang barang = entry.getKey();
                int jumlah = entry.getValue();
                double subtotal = barang.getHargaJual() * jumlah;

                bc.kurangiStok(barang.getId(), jumlah);
                pc.tambahDetailPenjualan(idPenjualan, barang.getId(), jumlah, barang.getHargaJual());

                pec.tambahPemasukan(new Pemasukan(0, new Date(), subtotal, "Penjualan barang: " + barang.getNama()));
            }

            // Simpan pemasukan total
            pec.tambahPemasukan(new Pemasukan(0, new Date(), totalPembelian, "Pembayaran Tunai"));

            cetakPDF("Pembayaran Tunai", 1, totalPembelian, totalPembelian, "Tunai");

        } else {
            JOptionPane.showMessageDialog(this, "Uang tidak cukup.");
            }
        } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Input pembayaran tidak valid.");
        }
    }
        else {
            PenjualanController pc = new PenjualanController();
            int idPenjualan = pc.tambahPenjualan(new Penjualan(0, new Date(), totalPembelian, "hutang", "belum lunas", namaPelanggan));

            for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
                Barang barang = entry.getKey();
                int jumlah = entry.getValue();
                bc.kurangiStok(barang.getId(), jumlah);
                pc.tambahDetailPenjualan(idPenjualan, barang.getId(), jumlah, barang.getHargaJual());
            }

            new CicilanController().tambahCicilan(new Cicilan(0, idPenjualan, new Date(), 0.0));
            JOptionPane.showMessageDialog(this, "Pembelian dicatat sebagai hutang.");
            cetakPDF("Pembelian Cicilan", 1, totalPembelian, totalPembelian, "Cicilan");
        }

        keranjang.clear();
        updateDaftarPembelian();
        tfNamaPelanggan.setText("");
    }

    private double hitungTotalPembelian() {
        double total = 0;
        for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
            Barang b = entry.getKey();
            int jumlah = entry.getValue();
            total += b.getHargaJual() * jumlah;
        }
        return total;
    }

    private void cetakPDF(String namaBarang, int jumlah, double hargaSatuan, double total, String metode) {
        Document document = new Document();
        try {
            String folderPath = "pdf/penjualan/";
            File folder = new File(folderPath);
            if (!folder.exists()) folder.mkdirs();

            String fileName = "Nota_Penjualan_" + System.currentTimeMillis() + ".pdf";
            String filePath = folderPath + fileName;

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("Nota Penjualan", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Tanggal: " + new Date()));
            document.add(new Paragraph("Nama Pelanggan: " + tfNamaPelanggan.getText().trim()));
            document.add(new Paragraph("Metode Pembayaran: " + metode));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(4);
            table.addCell("Barang");
            table.addCell("Jumlah");
            table.addCell("Harga Satuan");
            table.addCell("Subtotal");

            table.addCell(namaBarang);
            table.addCell(String.valueOf(jumlah));
            table.addCell("Rp" + new DecimalFormat("#,###.00").format(hargaSatuan));
            table.addCell("Rp" + new DecimalFormat("#,###.00").format(total));

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: Rp" + new DecimalFormat("#,###.00").format(total)));

            document.close();
            JOptionPane.showMessageDialog(this, "Struk disimpan di: " + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mencetak PDF: " + e.getMessage());
        }
    }
}
