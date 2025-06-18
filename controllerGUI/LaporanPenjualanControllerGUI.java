// Merupakan file dari Controller GUI
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
    // Inisialisasi controller untuk mengambil data yang diperlukan
    private DetailPenjualanController detailController = new DetailPenjualanController();
    private PemasukanController pemasukanController = new PemasukanController();
    private PengeluaranController pengeluaranController = new PengeluaranController();

    // Method utama untuk menampilkan laporan penjualan ke dalam JTable dan ringkasan ke JLabel
    public void tampilkanLaporanPenjualan(JTable tabel, JLabel lblPemasukan, JLabel lblPengeluaran, JLabel lblLaba) {
        // Buat model tabel dengan kolom: Barang, Jumlah, Harga Satuan, Subtotal
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Barang", "Jumlah", "Harga Satuan", "Subtotal"}, 0);

        // Ambil data detail penjualan dari controller untuk hari ini
        List<DetailPenjualan> daftar = detailController.getDetailPenjualanHariIni();

        // Loop setiap data detail penjualan dan tambahkan ke dalam model tabel
        for (DetailPenjualan dp : daftar) {
            double subtotal = dp.getJumlah() * dp.getHargaSatuan(); // Hitung subtotal dari penjualan barang
            Object[] row = {
                dp.getNamaBarang(),
                dp.getJumlah(),
                dp.getHargaSatuan(),
                subtotal
            };
            model.addRow(row); // Tambahkan baris ke model
        }

        // Set model ke tabel yang ditampilkan di GUI
        tabel.setModel(model);

        // Panggil method untuk memperbarui label ringkasan keuangan
        updateRingkasan(lblPemasukan, lblPengeluaran, lblLaba);
    }

    // Method untuk menghitung dan menampilkan ringkasan total pemasukan, pengeluaran, dan laba
    private void updateRingkasan(JLabel lblPemasukan, JLabel lblPengeluaran, JLabel lblLaba) {
        // Hitung total pemasukan dari seluruh data pemasukan
        double totalPemasukan = pemasukanController.getAllPemasukan().stream()
                .mapToDouble(Pemasukan::getTotal).sum();

        // Hitung total pengeluaran dari seluruh data pengeluaran
        double totalPengeluaran = pengeluaranController.getAllPengeluaran().stream()
                .mapToDouble(Pengeluaran::getTotal).sum();

        // Hitung laba atau rugi (selisih pemasukan dan pengeluaran)
        double laba = totalPemasukan - totalPengeluaran;

        // Tampilkan hasil ke label-label di GUI
        lblPemasukan.setText("Total Pemasukan: Rp " + totalPemasukan);
        lblPengeluaran.setText("Total Pengeluaran: Rp " + totalPengeluaran);
        lblLaba.setText("Laba / Rugi: Rp " + laba);
    }
}     
