/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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
/**
 *
 * @author aurel
 */
public class CetakLaporanControllerGUI {
    


    private DetailPenjualanController detailPenjualanController;
    private PengeluaranController pengeluaranController;
    private PemasukanController pemasukanController;
    private BarangController barangController;

    public CetakLaporanControllerGUI() {
        detailPenjualanController = new DetailPenjualanController();
        pengeluaranController = new PengeluaranController();
        barangController = new BarangController();
        pemasukanController = new PemasukanController();
    }

    public void cetakLaporanKeuangan() {
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
                JOptionPane.showMessageDialog(null, "Gagal menambahkan diagram ke PDF: " + ex.getMessage());
            }

            document.close();
            new AuditLogController().catatLog("Laporan Keuangan dicetak");

            JOptionPane.showMessageDialog(null, "Laporan berhasil disimpan ke:\n" + filePath);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Gagal ekspor PDF: " + e.getMessage());
        }
    }
}