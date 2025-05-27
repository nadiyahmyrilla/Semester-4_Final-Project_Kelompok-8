package controllerGUI;

import javax.swing.*;
import controller.*;
import model.*;

import java.awt.*;
import java.text.DecimalFormat;
import java.awt.Font;
import java.awt.Image;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import controller.AuditLogController;

public class PembelianControllerGUI extends JPanel {
    private JPanel daftarBarangPanel, daftarPembelianPanel;
    private JLabel totalLabel;
    private JButton btnBayar;
    private Map<Barang, Integer> keranjang = new LinkedHashMap<>();

    public PembelianControllerGUI() {
        setLayout(new BorderLayout(10, 10));

        daftarBarangPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        JScrollPane scrollBarang = new JScrollPane(daftarBarangPanel);

        daftarPembelianPanel = new JPanel();
        daftarPembelianPanel.setLayout(new BoxLayout(daftarPembelianPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollDaftar = new JScrollPane(daftarPembelianPanel);

        JPanel kananPanel = new JPanel(new BorderLayout(5, 5));
        JPanel bawahPanel = new JPanel(new BorderLayout(5, 5));

        totalLabel = new JLabel("Total: Rp0");
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        btnBayar = new JButton("Bayar");
        btnBayar.addActionListener(e -> prosesPembelian());

        bawahPanel.add(totalLabel, BorderLayout.CENTER);
        bawahPanel.add(btnBayar, BorderLayout.SOUTH);

        kananPanel.add(scrollDaftar, BorderLayout.CENTER);
        kananPanel.add(bawahPanel, BorderLayout.SOUTH);

        add(scrollBarang, BorderLayout.CENTER);
        add(kananPanel, BorderLayout.EAST);

        tampilkanBarang();
    }

    private void tampilkanBarang() {
        BarangController bc = new BarangController();
        daftarBarangPanel.removeAll();

        for (Barang b : bc.getAllBarang()) {
            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setBorder(BorderFactory.createTitledBorder(b.getNama()));
            itemPanel.setBackground(Color.lightGray);
            
            // Untuk label dari gambar
            JLabel labelGambar;
            try {
                String path = "images/" + b.getFoto(); // pastikan ini file path yang valid
                File file = new File(path);
                if (!file.exists()) throw new Exception("File tidak ditemukan");

                ImageIcon icon = new ImageIcon(path);
                Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                labelGambar = new JLabel(new ImageIcon(img));
            } catch (Exception e) {
                labelGambar = new JLabel("No Image");
            }

            // Informasi barang
            JPanel infoPanel = new JPanel(new GridLayout(2, 1));
            JLabel labelNama = new JLabel(b.getNama());
            JLabel labelHarga = new JLabel("Harga: Rp" + new DecimalFormat("#,###.00").format(b.getHargaBeli()));
            infoPanel.add(labelNama);
            infoPanel.add(labelHarga);
            
            JButton btnTambah = new JButton("Tambah");
            btnTambah.addActionListener(e -> tambahBarang(b));

            itemPanel.add(labelGambar, BorderLayout.WEST);
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
            double subtotal = b.getHargaBeli() * jumlah;
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

    private void prosesPembelian() {
        if (keranjang.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tidak ada barang yang dibeli.");
            return;
        }

        BarangController bc = new BarangController();
        PengeluaranController pc = new PengeluaranController();

        for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
            Barang barang = entry.getKey();
            int jumlah = entry.getValue();
            double total = barang.getHargaBeli() * jumlah;

            bc.tambahStok(barang.getId(), jumlah);
            pc.tambahPengeluaran(new Pengeluaran(0, new Date(), total, barang.getId(), jumlah));
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Pembelian berhasil! Cetak struk PDF?", "Cetak Struk", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            cetakPDF();
        }

        keranjang.clear();
        updateDaftarPembelian();
    }

    private void cetakPDF() {
        Document document = new Document();
        try {
            // Buat folder jika belum ada
            String folderPath = "pdf/pembelian/";
            File folder = new File(folderPath);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            // Gabungkan path dan nama file
            String fileName = "Nota_Pembelian_" + System.currentTimeMillis() + ".pdf";
            String filePath = folderPath + fileName;

            // Gunakan filePath di sini, bukan fileName!
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("Nota Pembelian", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Tanggal: " + new Date()));
            document.add(new Paragraph(" "));

            PdfPTable table = new PdfPTable(3);
            table.addCell("Barang");
            table.addCell("Jumlah");
            table.addCell("Harga Satuan");
            table.addCell("Subtotal");

            double total = 0;
            for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
                Barang b = entry.getKey();
                int jumlah = entry.getValue();
                double subtotal = b.getHargaBeli() * jumlah;
                total += subtotal;

                table.addCell(b.getNama());
                table.addCell(String.valueOf(jumlah));
                table.addCell("Rp" + new DecimalFormat("#,###.00").format(subtotal));
            }

            document.add(table);
            document.add(new Paragraph(" "));
            document.add(new Paragraph("Total: Rp" + new DecimalFormat("#,###.00").format(total)));

            document.close();

            JOptionPane.showMessageDialog(this, "Struk disimpan sebagai PDF");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal mencetak PDF: " + e.getMessage());
        }
    }

}
