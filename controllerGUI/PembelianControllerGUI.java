// Merupakan file dari Controller GUI
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

public class PembelianControllerGUI extends JPanel {
    // Komponen utama GUI
    private JPanel daftarBarangPanel, daftarPembelianPanel;
    private JLabel totalLabel;
    private JButton btnBayar;

    // Menyimpan barang dan jumlahnya dalam keranjang (pembelian sementara)
    private Map<Barang, Integer> keranjang = new LinkedHashMap<>();

    // Konstruktor, inisialisasi dan setup layout
    public PembelianControllerGUI() {
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(255, 235, 202));

        // Panel untuk daftar barang
        daftarBarangPanel = new JPanel(new GridLayout(0, 1, 5, 5));
        daftarBarangPanel.setBackground(new Color(255, 235, 202));
        JScrollPane scrollBarang = new JScrollPane(daftarBarangPanel);
        scrollBarang.setBackground(new Color(255, 235, 202));

        // Panel daftar pembelian/keranjang
        daftarPembelianPanel = new JPanel();
        daftarPembelianPanel.setBackground(new Color(255, 235, 202));
        daftarPembelianPanel.setLayout(new BoxLayout(daftarPembelianPanel, BoxLayout.Y_AXIS));
        daftarPembelianPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(153, 0, 0), 2),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)));

        JScrollPane scrollDaftar = new JScrollPane(daftarPembelianPanel);
        scrollBarang.setBackground(new Color(255, 235, 202));
        daftarPembelianPanel.setPreferredSize(new Dimension(300, 200));

        // Panel kanan dan bawah
        JPanel kananPanel = new JPanel(new BorderLayout(5, 5));
        kananPanel.setBackground(new Color(255, 235, 202));
        JPanel bawahPanel = new JPanel(new BorderLayout(5, 5));
        bawahPanel.setBackground(new Color(255, 235, 202));

        // Label total harga
        totalLabel = new JLabel("Total: Rp0");
        totalLabel.setHorizontalAlignment(SwingConstants.CENTER);
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // Tombol bayar
        btnBayar = new JButton("Bayar");
        btnBayar.setBackground(new Color(153, 0, 0));
        btnBayar.setForeground(new Color(225, 235, 202));
        btnBayar.setFont(new Font("DeVinne Txt BT", Font.BOLD, 16));
        btnBayar.setPreferredSize(new Dimension(100, 50));
        btnBayar.addActionListener(e -> prosesPembelian());

        // Layout panel bawah
        bawahPanel.add(totalLabel, BorderLayout.CENTER);
        bawahPanel.add(btnBayar, BorderLayout.SOUTH);

        // Panel kanan untuk daftar belanja dan tombol bayar
        kananPanel.add(scrollDaftar, BorderLayout.CENTER);
        kananPanel.add(bawahPanel, BorderLayout.SOUTH);

        // Tambahkan panel ke tampilan utama
        add(scrollBarang, BorderLayout.CENTER);
        add(kananPanel, BorderLayout.EAST);

        // Tampilkan daftar barang yang tersedia
        tampilkanBarang();
    }

    // Menampilkan seluruh barang ke dalam panel sebagai kartu
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

            // Tampilkan gambar barang
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

            // Tampilkan nama dan harga
            JLabel labelNama = new JLabel(b.getNama());
            labelNama.setAlignmentX(Component.CENTER_ALIGNMENT);
            JLabel labelHarga = new JLabel("Harga: Rp" + new DecimalFormat("#,###.00").format(b.getHargaJual()));
            labelHarga.setAlignmentX(Component.CENTER_ALIGNMENT);

            // Tombol tambah ke keranjang
            JButton btnTambah = new JButton("Tambah");
            btnTambah.setBackground(new Color(153, 0, 0));
            btnTambah.setFont(new Font("DeVinne Txt BT", Font.PLAIN, 13));
            btnTambah.setForeground(Color.WHITE);
            btnTambah.setAlignmentX(Component.CENTER_ALIGNMENT);
            btnTambah.addActionListener(e -> tambahBarang(b));

            card.add(labelGambar);
            card.add(Box.createVerticalStrut(5));
            card.add(labelNama);
            card.add(labelHarga);
            card.add(Box.createVerticalStrut(5));
            card.add(btnTambah);

            daftarBarangPanel.add(card);
        }

        daftarBarangPanel.revalidate();
        daftarBarangPanel.repaint();
    }

    // Menambahkan barang ke keranjang (dengan input jumlah)
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
                        if (input == null || input.isBlank()) return;

                        try {
                            int jumlah = Integer.parseInt(input);
                            if (jumlah <= 0) throw new NumberFormatException();

                            keranjang.put(barang, keranjang.getOrDefault(barang, 0) + jumlah);
                            updateDaftarPembelian();
                        } catch (NumberFormatException ex) {
                            new BlurPopupDialog(
                                frame,
                                "Jumlah Tidak Valid",
                                "Masukkan angka yang valid untuk jumlah barang.",
                                new String[]{"OK"},
                                new ActionListener[]{evt -> {}}
                            ).setVisible(true);
                        }
                    },
                    e -> {} // Dibatalkan, tidak melakukan apa-apa
                },
                inputField
            ).setVisible(true);
        }
    }

    // Memperbarui panel daftar pembelian dengan isi keranjang terbaru
    private void updateDaftarPembelian() {
        daftarPembelianPanel.removeAll();
        double total = 0;

        for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
            Barang b = entry.getKey();
            int jumlah = entry.getValue();
            double subtotal = b.getHargaBeli() * jumlah;
            total += subtotal;

            JPanel itemPanel = new JPanel(new BorderLayout());
            itemPanel.setPreferredSize(new Dimension(300, 60));
            itemPanel.setMaximumSize(new Dimension(300, 60));
            itemPanel.setBackground(new Color(255, 235, 202));
            itemPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)));

            JLabel itemLabel = new JLabel(b.getNama() + " x" + jumlah + " = Rp" + new DecimalFormat("#,###.00").format(subtotal));
            itemLabel.setFont(new Font("Arial", Font.PLAIN, 14));
            itemLabel.setVerticalAlignment(JLabel.CENTER);
            itemPanel.add(itemLabel, BorderLayout.CENTER);

            JPanel tombolPanel = new JPanel();
            tombolPanel.setLayout(new GridLayout(2, 1, 5, 5));
            tombolPanel.setOpaque(false);
            JButton btnEdit = new JButton("Edit");
            JButton btnHapus = new JButton("Hapus");

            btnEdit.setFont(new Font("Arial", Font.PLAIN, 11));
            btnEdit.setBackground(new Color(200, 200, 200));
            btnHapus.setFont(new Font("Arial", Font.PLAIN, 11));
            btnHapus.setBackground(new Color(200, 200, 200));

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

    // Memunculkan dialog untuk mengubah jumlah barang di keranjang
    private void editJumlah(Barang barang) {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame frame) {
            JTextField inputField = new JTextField(String.valueOf(keranjang.get(barang)));

            new BlurPopupDialog(
                frame,
                "Ubah Jumlah",
                "Ubah jumlah untuk <b>" + barang.getNama() + "</b>:",
                new String[]{"OK", "Batal"},
                new ActionListener[]{
                    e -> {
                        String input = inputField.getText();
                        if (input == null || input.isBlank()) return;

                        try {
                            int jumlahBaru = Integer.parseInt(input);
                            if (jumlahBaru <= 0) {
                                keranjang.remove(barang);
                            } else {
                                keranjang.put(barang, jumlahBaru);
                            }
                            updateDaftarPembelian();
                        } catch (NumberFormatException ex) {
                            new BlurPopupDialog(
                                frame,
                                "Jumlah Tidak Valid",
                                "Masukkan angka yang valid.",
                                new String[]{"OK"},
                                new ActionListener[]{evt -> {}}
                            ).setVisible(true);
                        }
                    },
                    e -> {} // Tombol Batal
                },
                inputField
            ).setVisible(true);
        }
    }

    // Proses pembayaran dan simpan ke database (stok dan pengeluaran)
    private void prosesPembelian() {
        if (keranjang.isEmpty()) {
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame frame) {
                new BlurPopupDialog(
                    frame,
                    "Keranjang Kosong",
                    "Tidak ada barang yang dibeli.",
                    new String[]{"OK"},
                    new ActionListener[]{evt -> {}}
                ).setVisible(true);
            }
            return;
        }

        BarangController bc = new BarangController();
        PengeluaranController pc = new PengeluaranController();

        for (Map.Entry<Barang, Integer> entry : keranjang.entrySet()) {
            Barang barang = entry.getKey();
            int jumlah = entry.getValue();
            double total = barang.getHargaBeli() * jumlah;

            // Tambah stok dan catat pengeluaran
            bc.tambahStok(barang.getId(), jumlah);
            pc.tambahPengeluaran(new Pengeluaran(0, new Date(), total, barang.getId(), jumlah));
        }

        // Konfirmasi untuk mencetak PDF
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JFrame frame) {
            new BlurPopupDialog(
                frame,
                "Pembelian Berhasil",
                "Apakah Anda ingin mencetak struk dalam bentuk PDF?",
                new String[]{"Ya", "Tidak"},
                new ActionListener[]{
                    evt -> cetakPDF(),
                    evt -> {}
                }
            ).setVisible(true);
        }

        keranjang.clear();
        updateDaftarPembelian();
    }

    // Mencetak struk pembelian ke dalam file PDF
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

            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame frame) {
                new BlurPopupDialog(
                    frame,
                    "Sukses",
                    "Struk berhasil disimpan sebagai PDF.",
                    new String[]{"OK"},
                    new ActionListener[]{evt -> {}}
                ).setVisible(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
            Window window = SwingUtilities.getWindowAncestor(this);
            if (window instanceof JFrame frame) {
                new BlurPopupDialog(
                    frame,
                    "Error",
                    "Gagal mencetak PDF: " + e.getMessage(),
                    new String[]{"OK"},
                    new ActionListener[]{evt -> {}}
                ).setVisible(true);
            }
        }
    }
}
