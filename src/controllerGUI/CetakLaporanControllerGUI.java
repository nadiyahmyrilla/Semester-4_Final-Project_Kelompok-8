// Merupakan file dari Controller GUI
package controllerGUI;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import controller.AuditLogController;
import controller.BarangController;
import controller.DetailPenjualanController;
import controller.PemasukanController;
import controller.PengeluaranController;
import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import model.DetailPenjualan;
import model.DiagramLaporan;
import model.Pemasukan;
import model.Pengeluaran;

public class CetakLaporanControllerGUI {
    // Deklarasi controller yang dibutuhkan
    private DetailPenjualanController detailPenjualanController;
    private PengeluaranController pengeluaranController;
    private PemasukanController pemasukanController;
    private BarangController barangController;

    // Konstruktor, menginisialisasi semua controller
    public CetakLaporanControllerGUI() {
        detailPenjualanController = new DetailPenjualanController();
        pengeluaranController = new PengeluaranController();
        barangController = new BarangController();
        pemasukanController = new PemasukanController();
    }

    // Fungsi utama untuk mencetak laporan keuangan ke dalam file PDF
    public void cetakLaporanKeuangan() {
        Document document = new Document();  // Membuat objek dokumen PDF
        try {
            // Format tanggal hari ini
            String tanggal = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            String folderPath = "pdf/laporan_keuangan/";     // Folder penyimpanan PDF
            new File(folderPath).mkdirs();                   // Membuat folder jika belum ada
            String filePath = folderPath + "laporan_keuangan_" + tanggal + ".pdf"; // Nama file PDF

            // Membuat writer untuk menulis file PDF
            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open(); // Membuka dokumen untuk ditulis

            // Menambahkan judul dan tanggal laporan
            document.add(new Paragraph("Laporan Keuangan - Bening Work", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph("Tanggal: " + tanggal));
            document.add(Chunk.NEWLINE); // Spasi kosong

            // Membuat tabel untuk detail penjualan hari ini
            document.add(new Paragraph("Detail Penjualan Hari Ini:"));
            PdfPTable penjualanTable = new PdfPTable(4);
            penjualanTable.setWidthPercentage(100); // Mengatur lebar tabel
            penjualanTable.addCell("Barang");
            penjualanTable.addCell("Jumlah");
            penjualanTable.addCell("Harga Satuan");
            penjualanTable.addCell("Subtotal");

            // Mengambil data penjualan hari ini
            List<DetailPenjualan> daftarpenjualan = detailPenjualanController.getDetailPenjualanHariIni();
            for (DetailPenjualan dp : daftarpenjualan) {
                penjualanTable.addCell(dp.getNamaBarang());
                penjualanTable.addCell(String.valueOf(dp.getJumlah()));
                penjualanTable.addCell(String.valueOf(dp.getHargaSatuan()));
                penjualanTable.addCell(String.valueOf(dp.getJumlah() * dp.getHargaSatuan()));
            }

            document.add(penjualanTable); // Menambahkan tabel ke dokumen
            document.add(Chunk.NEWLINE);  // Spasi

            // Membuat tabel pengeluaran
            document.add(new Paragraph("Detail Pengeluaran Hari Ini:"));
            PdfPTable pengeluaranTable = new PdfPTable(3);
            pengeluaranTable.setWidthPercentage(100);
            pengeluaranTable.addCell("Barang");
            pengeluaranTable.addCell("Jumlah");
            pengeluaranTable.addCell("Subtotal");

            // Mengambil data pengeluaran hari ini
            BarangController barangController = new BarangController(); // Controller lokal
            List<Pengeluaran> daftarPengeluaran = pengeluaranController.getPengeluaranHariIni();

            // Menambahkan data pengeluaran ke tabel
            for (Pengeluaran p : daftarPengeluaran) {
                String namaBarang = barangController.getNamaBarangById(p.getBarangId());
                pengeluaranTable.addCell(namaBarang);
                pengeluaranTable.addCell(String.valueOf(p.getJumlah()));
                pengeluaranTable.addCell(String.valueOf(p.getTotal()));
            }

            document.add(pengeluaranTable); // Menambahkan tabel ke dokumen
            document.add(Chunk.NEWLINE);    // Spasi

            // Perhitungan laba / rugi
            double totalPemasukanHariIni = pemasukanController.getPemasukanHariIni().stream()
                    .mapToDouble(Pemasukan::getTotal).sum();
            double totalPengeluaranHariIni = pengeluaranController.getPengeluaranHariIni().stream()
                    .mapToDouble(Pengeluaran::getTotal).sum();
            double laba = totalPemasukanHariIni - totalPengeluaranHariIni;
            double persentaseLaba = totalPemasukanHariIni != 0 ? (laba / totalPemasukanHariIni) * 100 : 0;

            // Tabel ringkasan perhitungan laba/rugi
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

            document.add(summaryTable); // Tambahkan ke dokumen

            // Menambahkan diagram batang (opsional visualisasi)
            try {
                DiagramLaporan diagram = new DiagramLaporan();
                List<DetailPenjualan> penjualanHariIni = detailPenjualanController.getDetailPenjualanHariIni();
                diagram.getDiagramPDF(document, penjualanHariIni, totalPemasukanHariIni, totalPengeluaranHariIni);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Gagal menambahkan diagram ke PDF: " + ex.getMessage());
            }

            document.close(); // Menutup dokumen setelah selesai
            new AuditLogController().catatLog("Laporan Keuangan dicetak"); // Mencatat aktivitas

            // Notifikasi bahwa laporan berhasil dibuat
            JOptionPane.showMessageDialog(null, "Laporan berhasil disimpan ke:\n" + filePath);

        } catch (Exception e) {
            // Menampilkan pesan error jika proses gagal
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal ekspor PDF: " + e.getMessage());
        }
    }
}