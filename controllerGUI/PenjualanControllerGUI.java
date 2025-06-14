package controllerGUI;

import javax.swing.*;
import controller.*;
import model.*;
import style.BlurPopupDialog;

import java.awt.*;
import java.text.DecimalFormat;
import java.awt.Font;
import java.awt.Image;
import java.util.*;
import java.io.File;
import java.io.FileOutputStream;
import java.awt.event.ActionListener;

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
        setBackground(new Color(255, 235, 202));

        daftarBarangPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        daftarBarangPanel.setBackground(new Color(255, 235, 202));
        JScrollPane scrollBarang = new JScrollPane(daftarBarangPanel);
        scrollBarang.setBackground(new Color(255, 235, 202));

        daftarPembelianPanel = new JPanel();
        daftarPembelianPanel.setBackground(new Color(255, 235, 202));
        daftarPembelianPanel.setLayout(new BoxLayout(daftarPembelianPanel, BoxLayout.Y_AXIS));
        daftarPembelianPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(153, 0, 0), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        JScrollPane scrollDaftar = new JScrollPane(daftarPembelianPanel);
        scrollBarang.setBackground(new Color(255, 235, 202));
        daftarPembelianPanel.setPreferredSize(new Dimension(300, 200));
        JPanel kananPanel = new JPanel(new BorderLayout(5, 5));
        kananPanel.setBackground(new Color(255, 235, 202));

        // Input nama pelanggan
        JPanel atasPanel = new JPanel(new BorderLayout(5, 5));
        atasPanel.setBackground(new Color(255, 235, 202));
        JLabel lblNama = new JLabel("Nama Pelanggan: ");
        tfNamaPelanggan = new JTextField();
        tfNamaPelanggan.setBackground(new Color(153, 0, 0));
        tfNamaPelanggan.setForeground(new Color(255, 235, 202));
        tfNamaPelanggan.setFont(tfNamaPelanggan.getFont().deriveFont(Font.BOLD));
        atasPanel.add(lblNama, BorderLayout.WEST);
        atasPanel.add(tfNamaPelanggan, BorderLayout.CENTER);

        JPanel bawahPanel = new JPanel(new BorderLayout(5, 5));
        bawahPanel.setBackground(new Color(255, 235, 202));
        totalLabel = new JLabel("Total: Rp0");
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setFont(new Font("DeVinne Txt BT", Font.BOLD, 16));

        btnBayar = new JButton("Bayar");
        btnBayar.setBackground(new Color(153, 0, 0));
        btnBayar.setForeground(new Color(225, 235, 202));
        btnBayar.setFont(new Font("DeVinne Txt BT", Font.BOLD, 16));
        btnBayar.setPreferredSize(new Dimension(100, 50));
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
        daftarBarangPanel.removeAll();
        daftarBarangPanel.setLayout(new GridLayout(0, 3, 5, 5));

        for (Barang b : bc.getAllBarang()) {
            JPanel card = new JPanel();
            card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
            card.setBackground(new Color(255, 235, 202));
            card.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(153, 0, 0), 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)));

            // Label gambar
            JLabel labelGambar;
            try {
                ImageIcon icon = new ImageIcon(getClass().getResource("/images/" + b.getFoto()));
                Image img = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                labelGambar = new JLabel(new ImageIcon(img));
            } catch (Exception e) {
                labelGambar = new JLabel("No Image");
                System.out.println("Gagal load gambar: " + e.getMessage());
            }
            labelGambar.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Label nama
            JLabel labelNama = new JLabel(b.getNama());
            labelNama.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Label harga
            JLabel labelHarga = new JLabel("Harga: Rp" + new DecimalFormat("#,###.00").format(b.getHargaJual()));
            labelHarga.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Tombol tambah
            JButton btnTambah = new JButton("Tambah");
            btnTambah.setBackground(new Color(153, 0, 0));
            btnTambah.setFont(new Font("DeVinne Txt BT", Font.PLAIN, 13));
            btnTambah.setForeground(Color.WHITE);
            btnTambah.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnTambah.addActionListener(e -> tambahBarang(b));

            // Menambahkan komponen ke dalam kartu
            card.add(labelGambar);
            card.add(Box.createVerticalStrut(5));
            card.add(labelNama);
            card.add(labelHarga);
            card.add(Box.createVerticalStrut(5));
            card.add(btnTambah);

            // Menambahkan kartu ke panel utama
            daftarBarangPanel.add(card);
        }

        daftarBarangPanel.revalidate();
        daftarBarangPanel.repaint();
    }

    private void tambahBarang(Barang barang) {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame frame) {
            JTextField inputField = new JTextField();

            new BlurPopupDialog(
                    frame,
                    "Tambah Barang",
                    "Masukkan jumlah untuk <b>" + barang.getNama() + "</b>:",
                    new String[]{"OK", "Batal"},
                    new ActionListener[]{
                        e -> {
                            String input = inputField.getText();
                            if (input == null || input.isBlank()) {
                                return;
                            }

                            try {
                                int jumlahInput = Integer.parseInt(input);
                                if (jumlahInput <= 0) {
                                    throw new NumberFormatException();
                                }

                                int jumlahSekarang = keranjang.getOrDefault(barang, 0);
                                int totalJumlah = jumlahSekarang + jumlahInput;

                                if (totalJumlah > barang.getStok()) {
                                    new BlurPopupDialog(
                                            frame,
                                            "Stok Tidak Cukup",
                                            "Jumlah yang dimasukkan melebihi stok tersedia (" + barang.getStok()
                                            + ").",
                                            new String[]{"OK"},
                                            new ActionListener[]{evt -> {
                                                }}).setVisible(true);
                                    return;
                                }

                                keranjang.put(barang, totalJumlah);
                                updateDaftarPembelian();
                            } catch (NumberFormatException ex) {
                                new BlurPopupDialog(
                                        frame,
                                        "Jumlah Tidak Valid",
                                        "Masukkan angka yang valid.",
                                        new String[]{"OK"},
                                        new ActionListener[]{evt -> {
                                            }}).setVisible(true);
                            }
                        },
                        e -> {
                        } // Jika dibatalkan, tidak melakukan apa-apa
                    },
                    inputField).setVisible(true);
        }
        new AuditLogController().catatLog(
                "Tambah barang ke keranjang: " + barang.getNama());

    }

    private void updateDaftarPembelian() {
        daftarPembelianPanel.removeAll();
        double total = 0;

        for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
            Barang b = entry.getKey();
            int jumlah = entry.getValue();
            double subtotal = b.getHargaJual() * jumlah;
            total += subtotal;

            JPanel itemPanel = new JPanel();
            itemPanel.setPreferredSize(new Dimension(300, 60)); // ukuran tetap
            itemPanel.setMaximumSize(new Dimension(300, 60));
            itemPanel.setLayout(new BorderLayout());
            itemPanel.setBackground(new Color(255, 235, 202));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY), // garis bawah
                    BorderFactory.createEmptyBorder(5, 10, 5, 10)));// padding dalam

            String info = "<html><b>" + b.getNama() + "</b><br/>"
                    + jumlah + " x Rp" + new DecimalFormat("#,###.00").format(b.getHargaJual())
                    + " = Rp" + new DecimalFormat("#,###.00").format(subtotal) + "</html>";
            JLabel itemLabel = new JLabel(info);
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 12));
            itemLabel.setVerticalAlignment(JLabel.CENTER);
            itemPanel.add(itemLabel, BorderLayout.CENTER);

            // Panel tombol Edit dan Hapus
            JPanel tombolPanel = new JPanel();
            tombolPanel.setLayout(new GridLayout(2, 1, 5, 5));
            tombolPanel.setOpaque(false);

            JButton btnEdit = new JButton("Edit");
            btnEdit.setFont(new Font("Arial", Font.PLAIN, 11));
            btnEdit.setBackground(new Color(200, 200, 200));

            JButton btnHapus = new JButton("Hapus");
            btnHapus.setFont(new Font("Arial", Font.PLAIN, 11));
            btnHapus.setBackground(new Color(200, 200, 200));

            btnEdit.addActionListener(e -> editJumlah(b));
            btnHapus.addActionListener(e -> {
                keranjang.remove(b);
                updateDaftarPembelian();
            });

            tombolPanel.add(btnEdit);
            tombolPanel.add(btnHapus);

            itemPanel.add(tombolPanel, BorderLayout.EAST);

            // Tambahkan item ke daftar
            daftarPembelianPanel.add(itemPanel);
            daftarPembelianPanel.add(Box.createVerticalStrut(10)); // jarak antar item
        }

        totalLabel.setText("Total: Rp" + new DecimalFormat("#,###.00").format(total));
        daftarPembelianPanel.revalidate();
        daftarPembelianPanel.repaint();
    }

    private void editJumlah(Barang barang) {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame frame) {
            JTextField inputField = new JTextField(String.valueOf(keranjang.get(barang)));

            new BlurPopupDialog(
                    frame,
                    "Edit Jumlah",
                    "Ubah jumlah untuk <b>" + barang.getNama() + "</b>:",
                    new String[]{"OK", "Batal"},
                    new ActionListener[]{
                        e -> {
                            try {
                                int jumlahBaru = Integer.parseInt(inputField.getText());
                                if (jumlahBaru <= 0) {
                                    keranjang.remove(barang);
                                } else {
                                    if (jumlahBaru > barang.getStok()) {
                                        new BlurPopupDialog(
                                                frame,
                                                "Stok Tidak Cukup",
                                                "Jumlah yang dimasukkan melebihi stok tersedia (" + barang.getStok()
                                                + ").",
                                                new String[]{"OK"},
                                                new ActionListener[]{evt -> {
                                                    }}).setVisible(true);
                                        return;
                                    }
                                    keranjang.put(barang, jumlahBaru);
                                }
                                updateDaftarPembelian();
                            } catch (NumberFormatException ex) {
                                new BlurPopupDialog(
                                        frame,
                                        "Jumlah Tidak Valid",
                                        "Masukkan angka yang valid.",
                                        new String[]{"OK"},
                                        new ActionListener[]{evt -> {
                                            }}).setVisible(true);
                            }
                        },
                        e -> {
                        } // batal
                    },
                    inputField).setVisible(true);
        }
    }

    private void prosesBayar() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame frame) {
            if (keranjang.isEmpty()) {
                new BlurPopupDialog(
                        frame,
                        "Tidak Ada Barang",
                        "Tidak ada barang yang dibeli.",
                        new String[]{"OK"},
                        new ActionListener[]{e -> {
                            }}).setVisible(true);
                return;
            }

            String namaPelanggan = tfNamaPelanggan.getText().trim();
            if (namaPelanggan.isEmpty()) {
                new BlurPopupDialog(
                        frame,
                        "Nama Kosong",
                        "Nama pelanggan tidak boleh kosong.",
                        new String[]{"OK"},
                        new ActionListener[]{e -> {
                            }}).setVisible(true);
                return;
            }

            new BlurPopupDialog(
                    frame,
                    "Metode Pembayaran",
                    "Pilih metode pembayaran:",
                    new String[]{"Tunai", "Cicilan"},
                    new ActionListener[]{
                        e -> prosesTunai(frame),
                        e -> prosesCicilan(namaPelanggan)
                    }).setVisible(true);
        }
    }

    private void prosesTunai(JFrame frame) {
        JTextField bayarField = new JTextField();

        new BlurPopupDialog(
                frame,
                "Pembayaran Tunai",
                "Masukkan jumlah pembayaran (Rp):",
                new String[]{"Bayar", "Batal"},
                new ActionListener[]{
                    e -> {
                        try {
                            double totalPembelian = hitungTotalPembelian();
                            double pembayaran = Double.parseDouble(bayarField.getText());

                            if (pembayaran >= totalPembelian) {
                                double kembalian = pembayaran - totalPembelian;

                                new BlurPopupDialog(
                                        frame,
                                        "Pembayaran Berhasil",
                                        "Kembalian: Rp" + new DecimalFormat("#,###.00").format(kembalian),
                                        new String[]{"OK"},
                                        new ActionListener[]{evt -> {
                                            }}).setVisible(true);

                                BarangController bc = new BarangController();
                                PemasukanController pec = new PemasukanController();
                                pec.tambahPemasukan(
                                        new Pemasukan(0, new Date(), totalPembelian, "Pembayaran Tunai"));
                                cetakPDF("Pembayaran Tunai", 1, totalPembelian, totalPembelian, "Tunai");

                                // Tambahkan ini setelah sukses pembayaran
                                PenjualanController pc = new PenjualanController();
                                int idPenjualan = pc.tambahPenjualan(
                                        new Penjualan(0, new Date(), totalPembelian, "tunai", "lunas", tfNamaPelanggan.getText().trim())
                                );

                                // Tambahkan detail penjualan
                                for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
                                    Barang barang = entry.getKey();
                                    int jumlah = entry.getValue();
                                    pc.tambahDetailPenjualan(idPenjualan, barang.getId(), jumlah, barang.getHargaJual());
                                }

                                for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
                                    Barang barang = entry.getKey();
                                    int jumlah = entry.getValue();
                                    double subtotal = barang.getHargaJual() * jumlah;
                                    bc.kurangiStok(barang.getId(), jumlah);
                                    pec.tambahPemasukan(new Pemasukan(0, new Date(), subtotal,
                                            "Penjualan barang: " + barang.getNama()));
                                }

                                keranjang.clear();
                                updateDaftarPembelian();
                                tfNamaPelanggan.setText("");
                                new AuditLogController().catatLog(
                                        "Penjualan tunai oleh pelanggan: " + tfNamaPelanggan.getText().trim());
                            } else {
                                new BlurPopupDialog(
                                        frame,
                                        "Pembayaran Gagal",
                                        "Jumlah pembayaran kurang dari total.",
                                        new String[]{"OK"},
                                        new ActionListener[]{evt -> {
                                            }}).setVisible(true);
                            }
                        } catch (NumberFormatException ex) {
                            new BlurPopupDialog(
                                    frame,
                                    "Input Tidak Valid",
                                    "Masukkan angka yang valid.",
                                    new String[]{"OK"},
                                    new ActionListener[]{evt -> {
                                        }}).setVisible(true);
                        }
                    },
                    e -> {
                    }
                },
                bayarField).setVisible(true);

    }

    private void prosesCicilan(String namaPelanggan) {
        PenjualanController pc = new PenjualanController();
        BarangController bc = new BarangController();

        double totalPembelian = hitungTotalPembelian();
        int idPenjualan = pc
                .tambahPenjualan(new Penjualan(0, new Date(), totalPembelian, "hutang", "belum lunas", namaPelanggan));

        for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
            Barang barang = entry.getKey();
            int jumlah = entry.getValue();
            bc.kurangiStok(barang.getId(), jumlah);
            pc.tambahDetailPenjualan(idPenjualan, barang.getId(), jumlah, barang.getHargaJual());
        }

        new CicilanController().tambahCicilan(new Cicilan(0, idPenjualan, new Date(), 0.0));

        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame frame) {
            new BlurPopupDialog(
                    frame,
                    "Cicilan Dicatat",
                    "Pembelian dicatat sebagai hutang.",
                    new String[]{"OK"},
                    new ActionListener[]{e -> {
                        }}).setVisible(true);
        }

        cetakPDF("Pembelian Cicilan", 1, totalPembelian, totalPembelian, "Cicilan");
        keranjang.clear();
        updateDaftarPembelian();
        tfNamaPelanggan.setText("");
        new AuditLogController().catatLog(
                "Penjualan cicilan oleh pelanggan: " + namaPelanggan);

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
            if (!folder.exists()) {
                folder.mkdirs();
            }

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
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame frame) {
                new BlurPopupDialog(
                        frame,
                        "Cetak PDF",
                        "Struk disimpan di: " + filePath,
                        new String[]{"OK"},
                        new ActionListener[]{evt -> {
                            }}).setVisible(true);
            }
            new AuditLogController().catatLog(
                    "Cetak struk PDF metode: " + metode
                    + " untuk " + tfNamaPelanggan.getText().trim());

        } catch (Exception e) {
            e.printStackTrace();
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame frame) {
                new BlurPopupDialog(
                        frame,
                        "Gagal Mencetak",
                        "Gagal mencetak PDF: " + e.getMessage(),
                        new String[]{"OK"},
                        new ActionListener[]{evt -> {
                            }}).setVisible(true);
            }
        }
    }
}
