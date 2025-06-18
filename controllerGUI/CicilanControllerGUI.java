// Merupakan file dari Controller GUI
package controllerGUI;

import controller.AuditLogController;
import controller.CicilanController;
import controller.PemasukanController;
import controller.PenjualanController;
import model.Cicilan;
import model.Penjualan;
import style.BlurPopupDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.awt.event.ActionListener;

public class CicilanControllerGUI extends JPanel {
    // Inisialisasi controller yang dibutuhkan
    private PenjualanController pc = new PenjualanController();
    private CicilanController cc = new CicilanController();
    private PemasukanController pec = new PemasukanController();
    private JPanel listPanel; // Panel untuk menampung daftar kartu cicilan

    // Konstruktor utama GUI cicilan
    public CicilanControllerGUI() {
        setLayout(new BorderLayout()); // Menggunakan layout border
        setBackground(new Color(255, 235, 202)); // Warna latar belakang

        // Tombol untuk memuat ulang data hutang
        JButton btnRefresh = new JButton("Muat Ulang Hutang");
        btnRefresh.setBackground(new Color(102, 51, 0));
        btnRefresh.setForeground(new Color(225, 235, 202));
        btnRefresh.addActionListener(e -> loadData()); // Aksi tombol
        add(btnRefresh, BorderLayout.NORTH); // Ditempatkan di bagian atas

        // Panel untuk menampilkan daftar hutang
        listPanel = new JPanel();
        listPanel.setBackground(new Color(255, 235, 202));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(153, 0, 0), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        JScrollPane scroll = new JScrollPane(listPanel); // Scroll untuk daftar hutang
        add(scroll, BorderLayout.CENTER); // Tambahkan ke panel utama

        loadData(); // Muat data pertama kali saat panel dibuat
    }

    // Fungsi untuk memuat data penjualan yang belum lunas dan menampilkannya
    private void loadData() {
        listPanel.removeAll(); // Hapus konten sebelumnya

        // Ambil daftar penjualan dengan status "belum lunas"
        ArrayList<Penjualan> hutangList = pc.getPenjualanByStatus("belum lunas");
        for (Penjualan p : hutangList) {
            double dibayar = cc.getTotalCicilanByPenjualan(p.getId()); // Total cicilan yang sudah dibayar
            double sisa = p.getTotal() - dibayar; // Hitung sisa hutang

            // Panel individual untuk tiap penjualan
            JPanel card = new JPanel(new BorderLayout(10, 10));
            card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(153, 0, 0), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            card.setBackground(new Color(255, 235, 202));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

            // Informasi pelanggan dan hutang
            JLabel info = new JLabel("<html><b>Nama Pelanggan:</b> " + p.getNamaPelanggan() +
                    "<br><b>Tanggal:</b> " + p.getTanggal() +
                    "<br><b>Total:</b> Rp" + p.getTotal() +
                    "<br><b>Sisa Hutang:</b> Rp" + sisa + "</html>");
            card.add(info, BorderLayout.WEST); // Tampilkan di sisi kiri

            // Panel untuk form pembayaran cicilan
            JPanel panelBayar = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
            panelBayar.setBackground(new Color(255, 235, 202));
            panelBayar.setMaximumSize(new Dimension(500, 80));

            // Field untuk input jumlah cicilan
            JTextField tfJumlah = new JTextField();
            tfJumlah.setMaximumSize(new Dimension(500, 80));
            tfJumlah.setBackground(new Color(147, 83, 12));

            // Tombol untuk membayar cicilan
            JButton btnBayar = new JButton("Bayar");
            btnBayar.setMaximumSize(new Dimension(300, 250));
            btnBayar.setBackground(new Color(153, 0, 0));
            btnBayar.setForeground(new Color(225,235,202));

            // Aksi ketika tombol bayar ditekan
            btnBayar.addActionListener(e -> {
                try {
                    double jumlah = Double.parseDouble(tfJumlah.getText());
                    if (jumlah <= 0 || jumlah > sisa) {
                        throw new NumberFormatException();
                    }

                    // Tambahkan cicilan baru ke database
                    new CicilanController().tambahCicilan(
                        new Cicilan(0, p.getId(), new Date(), jumlah)
                    );

                    // Catat log pembayaran cicilan
                    new AuditLogController().catatLog(
                        "Pembayaran cicilan Rp" + jumlah +
                        " untuk penjualan ID: " + p.getId() +
                        " oleh pelanggan: " + p.getNamaPelanggan()
                    );

                    // Hitung sisa setelah pembayaran
                    double sisaBaru = sisa - jumlah;

                    // Jika lunas, update status dan catat pemasukan
                    if (sisaBaru <= 0.0001) {
                        pc.updateStatusPenjualan(p.getId(), "lunas");
                        pec.tambahPemasukan(new model.Pemasukan(0, new Date(), jumlah, "Pelunasan hutang ID: " + p.getId()));

                        // Catat log hutang lunas
                        new AuditLogController().catatLog(
                            "Hutang lunas untuk penjualan ID: " + p.getId() +
                            " oleh pelanggan: " + p.getNamaPelanggan()
                        );

                        // Tampilkan dialog lunas
                        Window window = SwingUtilities.getWindowAncestor(this);
                        if (window instanceof JFrame frame) {
                            new BlurPopupDialog(
                                frame,
                                "Lunas",
                                "Hutang telah lunas",
                                new String[]{"OK"},
                                new ActionListener[]{evt -> {}}
                            ).setVisible(true);
                        }
                    } else {
                        // Tampilkan dialog sisa hutang
                        Window window = SwingUtilities.getWindowAncestor(this);
                        if (window instanceof JFrame frame) {
                            new BlurPopupDialog(
                                frame,
                                "Hutang",
                                "Pembayaran diterima. Sisa hutang: " + sisaBaru,
                                new String[]{"OK"},
                                new ActionListener[]{evt -> {}}
                            ).setVisible(true);
                        }
                    }

                    loadData(); // Perbarui tampilan data

                } catch (NumberFormatException ex) {
                    // Jika input salah, tampilkan dialog kesalahan
                    Window window = SwingUtilities.getWindowAncestor(this);
                    if (window instanceof JFrame frame) {
                        new BlurPopupDialog(
                            frame,
                            "Invalid",
                            "Anda salah menginputkan jumlah",
                            new String[]{"OK"},
                            new ActionListener[]{evt -> {}}
                        ).setVisible(true);
                    }
                }
            });

            // Tambahkan komponen input dan tombol ke panel bayar
            panelBayar.add(tfJumlah);
            panelBayar.add(btnBayar);

            // Tambahkan panel bayar ke sisi kanan kartu
            card.add(panelBayar, BorderLayout.EAST);

            // Tambahkan kartu ke list panel
            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(10)); // Spasi antar kartu
        }

        listPanel.revalidate(); // Update tampilan
        listPanel.repaint();   // Gambar ulang panel
    }
}
