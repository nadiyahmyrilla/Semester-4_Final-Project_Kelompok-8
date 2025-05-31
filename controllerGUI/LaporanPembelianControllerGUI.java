package controllerGUI;

import controller.BarangController;
import controller.PemasukanController;
import controller.PengeluaranController;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import model.Pemasukan;
import model.Pengeluaran;

public class LaporanPembelianControllerGUI {

    private PengeluaranController pengeluaranController = new PengeluaranController();
    private PemasukanController pemasukanController = new PemasukanController();

    public void tampilkanLaporanPembelian(JTable tabel, JLabel lblPemasukan, JLabel lblPengeluaran, JLabel lblLaba) {
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nama Barang", "Jumlah", "Total"}, 0);

        List<Pengeluaran> daftar = pengeluaranController.getPengeluaranHariIni();
        BarangController barangController = new BarangController();

        for (Pengeluaran p : daftar) {
            String namaBarang = barangController.getNamaBarangById(p.getBarangId());
            Object[] row = {
                namaBarang,
                p.getJumlah(),
                p.getTotal()
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
