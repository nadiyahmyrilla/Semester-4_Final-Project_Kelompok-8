/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllerGUI;

import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;
import model.DetailPenjualan;
import controller.DetailPenjualanController;
import controller.PemasukanController;
import controller.PengeluaranController;
import java.util.List;
import model.Pemasukan;
import model.Pengeluaran;

public class LaporanPenjualanControllerGUI {
    private DetailPenjualanController detailController = new DetailPenjualanController();
    private PemasukanController pemasukanController = new PemasukanController();
    private PengeluaranController pengeluaranController = new PengeluaranController();

    public void tampilkanLaporanPenjualan(JTable tabel, JLabel lblPemasukan, JLabel lblPengeluaran, JLabel lblLaba) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Barang", "Jumlah", "Harga Satuan", "Subtotal"}, 0);

        List<DetailPenjualan> daftar = detailController.getDetailPenjualanHariIni();
        for (DetailPenjualan dp : daftar) {
            double subtotal = dp.getJumlah() * dp.getHargaSatuan();
            Object[] row = {
                dp.getNamaBarang(),
                dp.getJumlah(),
                dp.getHargaSatuan(),
                subtotal
            };
            model.addRow(row);
        }
                tabel.setModel(model);

            updateRingkasan(lblPemasukan, lblPengeluaran, lblLaba);
    }

    private void updateRingkasan(JLabel lblPemasukan, JLabel lblPengeluaran, JLabel lblLaba) {
        double totalPemasukan = pemasukanController.getAllPemasukan().stream()
                .mapToDouble(Pemasukan::getTotal).sum();

        double totalPengeluaran = pengeluaranController.getAllPengeluaran().stream()
                .mapToDouble(Pengeluaran::getTotal).sum();

        double laba = totalPemasukan - totalPengeluaran;

        lblPemasukan.setText("Total Pemasukan: Rp " + totalPemasukan);
        lblPengeluaran.setText("Total Pengeluaran: Rp " + totalPengeluaran);
        lblLaba.setText("Laba / Rugi: Rp " + laba);
    }
    }     
