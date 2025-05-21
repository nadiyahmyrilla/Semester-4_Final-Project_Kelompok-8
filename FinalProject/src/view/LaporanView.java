package view;

import controller.DetailPenjualanController;
import controller.PemasukanController;
import controller.PengeluaranController;
import controller.AuditLogController;
import controller.BarangController;
import model.DetailPenjualan;
import model.Pemasukan;
import model.Pengeluaran;
import model.DiagramLaporan;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class LaporanView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private JLabel lblTotalPemasukan, lblTotalPengeluaran, lblLaba;

    private DetailPenjualanController detailPenjualanController;
    private PengeluaranController pengeluaranController;
    private PemasukanController pemasukanController;

    public LaporanView() {
        detailPenjualanController = new DetailPenjualanController();
        pengeluaranController = new PengeluaranController();
        pemasukanController = new PemasukanController();

        setTitle("Laporan Keuangan - Bening Work");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel tombol
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btnPenjualan = new JButton("Laporan Penjualan");
        JButton btnPengeluaran = new JButton("Laporan Pengeluaran");
        JButton btnCetakKeuangan = new JButton("Cetak Laporan Keuangan");

        btnPenjualan.addActionListener(e -> tampilkanLaporanPenjualan());
        btnPengeluaran.addActionListener(e -> tampilkanLaporanPengeluaran());
        btnCetakKeuangan.addActionListener(e -> exportLaporanKeuangan());

        panelTop.add(btnPenjualan);
        panelTop.add(btnPengeluaran);
        panelTop.add(btnCetakKeuangan);

        // Tabel
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        // Panel info total
        JPanel panelBottom = new JPanel(new GridLayout(3, 1));
        lblTotalPemasukan = new JLabel("Total Pemasukan: Rp 0");
        lblTotalPengeluaran = new JLabel("Total Pengeluaran: Rp 0");
        lblLaba = new JLabel("Laba / Rugi: Rp 0");

        panelBottom.add(lblTotalPemasukan);
        panelBottom.add(lblTotalPengeluaran);
        panelBottom.add(lblLaba);

        add(panelTop, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);

        // Tampilkan laporan awal (penjualan)
        tampilkanLaporanPenjualan();
    }

    //LAPORAN PEMASUKAN(Penjualan + Cicilan)
    private void tampilkanLaporanPenjualan() {
        String[] kolom = {"Barang", "Jumlah", "Harga Satuan", "Subtotal"};
        tableModel.setColumnIdentifiers(kolom);
        tableModel.setRowCount(0);

        List<DetailPenjualan> daftar = detailPenjualanController.getDetailPenjualanHariIni();
        for (DetailPenjualan dp : daftar) {
            double subtotal = dp.getHargaSatuan() * dp.getJumlah();
            Object[] row = {
                dp.getNamaBarang(),
                dp.getJumlah(),
                dp.getHargaSatuan(),
                subtotal
            };
            tableModel.addRow(row);
        }

        updateRingkasan();
    }

    //LAPORAN PENGELUARAN
    private void tampilkanLaporanPengeluaran() {
        String[] kolom = {"Nama Barang", "Jumlah", "Total"};
        tableModel.setColumnIdentifiers(kolom);
        tableModel.setRowCount(0);

        List<Pengeluaran> daftar = pengeluaranController.getPengeluaranHariIni();
        BarangController barangController = new BarangController();

        for (Pengeluaran p : daftar) {
            String namaBarang = barangController.getNamaBarangById(p.getBarangId());
            Object[] row = {
                namaBarang,
                p.getJumlah(),
                p.getTotal()
            };
            tableModel.addRow(row);
        }

        updateRingkasan();
    }

    //PERHITUNGAN ALL OF TIME(laporan singkat)
    private void updateRingkasan() {
        double totalPemasukan = pemasukanController.getAllPemasukan().stream()
                .mapToDouble(Pemasukan::getTotal).sum();

        double totalPengeluaran = pengeluaranController.getAllPengeluaran().stream()
                .mapToDouble(Pengeluaran::getTotal).sum();

        double laba = totalPemasukan - totalPengeluaran;

        lblTotalPemasukan.setText("Total Pemasukan: Rp " + totalPemasukan);
        lblTotalPengeluaran.setText("Total Pengeluaran: Rp " + totalPengeluaran);
        lblLaba.setText("Laba / Rugi: Rp " + laba);
    }

    //CETAK PDF
    private void exportLaporanKeuangan() {
        Document document = new Document();
        try {
            String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String folderPath = "pdf/laporan_keuangan/";
            new File(folderPath).mkdirs();
            String filePath = folderPath + "laporan_keuangan_" + tanggal + ".pdf";

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            document.add(new Paragraph("Laporan Keuangan - Bening Work", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Tanggal: " + tanggal));
            document.add(Chunk.NEWLINE);

            // Tabel Pemasukan(Penjualan)
            document.add(new Paragraph("Detail Penjualan Hari Ini:"));
            PdfPTable penjualanTable = new PdfPTable(4);
            penjualanTable.setWidthPercentage(100);
            penjualanTable.addCell("Barang");
            penjualanTable.addCell("Jumlah");
            penjualanTable.addCell("Harga Satuan");
            penjualanTable.addCell("Subtotal");

            List<DetailPenjualan> daftarpenjualan = detailPenjualanController.getDetailPenjualanHariIni();
            for (DetailPenjualan dp : daftarpenjualan) {
                penjualanTable.addCell(dp.getNamaBarang());
                penjualanTable.addCell(String.valueOf(dp.getJumlah()));
                penjualanTable.addCell(String.valueOf(dp.getHargaSatuan()));
                penjualanTable.addCell(String.valueOf(dp.getJumlah() * dp.getHargaSatuan()));
            }

            document.add(penjualanTable);
            document.add(Chunk.NEWLINE);

            //Tabel Pengeluaran
            document.add(new Paragraph("Detail Pengeluaran Hari Ini:"));
            PdfPTable pengeluaranTable = new PdfPTable(3);
            pengeluaranTable.setWidthPercentage(100);
            pengeluaranTable.addCell("Barang");
            pengeluaranTable.addCell("Jumlah");
            pengeluaranTable.addCell("Subtotal");

            BarangController barangController = new BarangController();
            List<Pengeluaran> daftarPengeluaran = pengeluaranController.getPengeluaranHariIni();

            for (Pengeluaran p : daftarPengeluaran) {
                String namaBarang = barangController.getNamaBarangById(p.getBarangId());

                pengeluaranTable.addCell(namaBarang);
                pengeluaranTable.addCell(String.valueOf(p.getJumlah()));
                pengeluaranTable.addCell(String.valueOf(p.getTotal()));
            }

            document.add(pengeluaranTable);
            document.add(Chunk.NEWLINE);
            // PERHITUNGAN LABA HARI INI
            double totalPemasukanHariIni = pemasukanController.getPemasukanHariIni().stream()
                    .mapToDouble(Pemasukan::getTotal).sum();
            double totalPengeluaranHariIni = pengeluaranController.getPengeluaranHariIni().stream()
                    .mapToDouble(Pengeluaran::getTotal).sum();
            double laba = totalPemasukanHariIni - totalPengeluaranHariIni;
            double persentaseLaba = totalPemasukanHariIni != 0 ? (laba / totalPemasukanHariIni) * 100 : 0;

            PdfPTable summaryTable = new PdfPTable(2);
            summaryTable.setWidthPercentage(60);
            summaryTable.addCell("Total Pemasukan Hari Ini ");
            summaryTable.addCell("Rp " + totalPemasukanHariIni);
            summaryTable.addCell("Total Pengeluaran Hari Ini");
            summaryTable.addCell("Rp " + totalPengeluaranHariIni);
            summaryTable.addCell("Laba / Rugi");
            summaryTable.addCell("Rp " + laba);
            summaryTable.addCell("Persentase Laba");
            summaryTable.addCell(String.format("%.2f%%", persentaseLaba));

            document.add(summaryTable);

            // Tambahkan Diagram Batang
            try {
                DiagramLaporan diagram = new DiagramLaporan();
                List<DetailPenjualan> penjualanHariIni = detailPenjualanController.getDetailPenjualanHariIni();
                diagram.getDiagramPDF(document, penjualanHariIni, totalPemasukanHariIni, totalPengeluaranHariIni);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Gagal menambahkan diagram ke PDF: " + ex.getMessage());
            }

            document.close();
            new AuditLogController().catatLog("Laporan Keuangan dicetak");

            JOptionPane.showMessageDialog(this, "Laporan berhasil disimpan ke:\n" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal ekspor PDF: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LaporanView().setVisible(true));
    }
}
