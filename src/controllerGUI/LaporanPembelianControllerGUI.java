// Merupakan file dari Controller GUI
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

    // Inisialisasi controller untuk mengakses data pengeluaran dan pemasukan
    private PengeluaranController pengeluaranController = new PengeluaranController();
    private PemasukanController pemasukanController = new PemasukanController();

    // Fungsi untuk menampilkan laporan pembelian (pengeluaran) ke tabel dan menghitung ringkasan
    public void tampilkanLaporanPembelian(JTable tabel, JLabel lblPemasukan, JLabel lblPengeluaran, JLabel lblLaba) {
        // Membuat model tabel dengan kolom: Nama Barang, Jumlah, dan Total
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Nama Barang", "Jumlah", "Total"}, 0);

        // Mengambil daftar pengeluaran hari ini
        List<Pengeluaran> daftar = pengeluaranController.getPengeluaranHariIni();
        BarangController barangController = new BarangController(); // Untuk mengambil nama barang

        // Mengisi tabel dengan data pengeluaran
        for (Pengeluaran p : daftar) {
            String namaBarang = barangController.getNamaBarangById(p.getBarangId()); // Ambil nama barang berdasarkan ID
            Object[] row = {
                namaBarang,
                p.getJumlah(),
                p.getTotal()
            };
            model.addRow(row); // Tambahkan baris ke model tabel
        }

        // Set model ke tabel yang ditampilkan di GUI
        tabel.setModel(model);

        // Perbarui label ringkasan pemasukan, pengeluaran, dan laba
        updateRingkasan(lblPemasukan, lblPengeluaran, lblLaba);
    }

    // Fungsi untuk menghitung dan menampilkan ringkasan pemasukan, pengeluaran, dan laba
    private void updateRingkasan(JLabel lblPemasukan, JLabel lblPengeluaran, JLabel lblLaba) {
        // Hitung total pemasukan dari seluruh data pemasukan
        double totalPemasukan = pemasukanController.getAllPemasukan().stream()
                .mapToDouble(Pemasukan::getTotal).sum();

        // Hitung total pengeluaran dari seluruh data pengeluaran
        double totalPengeluaran = pengeluaranController.getAllPengeluaran().stream()
                .mapToDouble(Pengeluaran::getTotal).sum();

        // Hitung laba atau rugi (selisih pemasukan dan pengeluaran)
        double laba = totalPemasukan - totalPengeluaran;

        // Tampilkan hasil ringkasan ke label
        lblPemasukan.setText("Total Pemasukan: Rp " + totalPemasukan);
        lblPengeluaran.setText("Total Pengeluaran: Rp " + totalPengeluaran);
        lblLaba.setText("Laba / Rugi: Rp " + laba);
    }
}
