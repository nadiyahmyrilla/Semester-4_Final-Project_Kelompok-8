package controllerGUI;

import controller.AuditLogController;
import controller.CicilanController;
import controller.PemasukanController;
import controller.PenjualanController;
import model.Cicilan;
import model.Penjualan;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class CicilanControllerGUI extends JPanel {
    private PenjualanController pc = new PenjualanController();
    private CicilanController cc = new CicilanController();
    private PemasukanController pec = new PemasukanController();
    private JPanel listPanel;

    public CicilanControllerGUI() {
        setLayout(new BorderLayout());

        JButton btnRefresh = new JButton("Muat Ulang Hutang");
        btnRefresh.addActionListener(e -> loadData());
        add(btnRefresh, BorderLayout.NORTH);

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));

        JScrollPane scroll = new JScrollPane(listPanel);
        add(scroll, BorderLayout.CENTER);

        loadData();
    }

    private void loadData() {
        listPanel.removeAll();

        ArrayList<Penjualan> hutangList = pc.getPenjualanByStatus("belum lunas");
        for (Penjualan p : hutangList) {
            double dibayar = cc.getTotalCicilanByPenjualan(p.getId());
            double sisa = p.getTotal() - dibayar;

            JPanel card = new JPanel(new BorderLayout(10, 10));
            card.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            card.setBackground(new Color(245, 245, 245));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

            // Info pelanggan
            JLabel info = new JLabel("<html><b>Nama Pelanggan:</b> " + p.getNamaPelanggan() +
                    "<br><b>Tanggal:</b> " + p.getTanggal() +
                    "<br><b>Total:</b> Rp" + p.getTotal() +
                    "<br><b>Sisa Hutang:</b> Rp" + sisa + "</html>");
            card.add(info, BorderLayout.WEST);

            // Panel form bayar
            JPanel panelBayar = new JPanel(new GridLayout(2, 1, 5, 5));
            JTextField tfJumlah = new JTextField();
            JButton btnBayar = new JButton("Bayar");

            btnBayar.addActionListener(e -> {
                try {
                    double jumlah = Double.parseDouble(tfJumlah.getText());
                    if (jumlah <= 0 || jumlah > sisa) {
                        throw new NumberFormatException();
                    }

                    new CicilanController().tambahCicilan(
                        new Cicilan(0, p.getId(), new Date(), jumlah)
                    );

                    // Logging cicilan
                    new AuditLogController().catatLog(
                        "Pembayaran cicilan Rp" + jumlah +
                        " untuk penjualan ID: " + p.getId() +
                        " oleh pelanggan: " + p.getNamaPelanggan()
                    );

                    double sisaBaru = sisa - jumlah;
                    if (sisaBaru <= 0.0001) {
                        pc.updateStatusPenjualan(p.getId(), "lunas");
                        pec.tambahPemasukan(new model.Pemasukan(0, new Date(), jumlah, "Pelunasan hutang ID: " + p.getId()));
                        
                    // Logging lunas
                    new AuditLogController().catatLog(
                        "Hutang lunas untuk penjualan ID: " + p.getId() +
                        " oleh pelanggan: " + p.getNamaPelanggan()
                    );
                        JOptionPane.showMessageDialog(this, "Hutang telah lunas.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Pembayaran diterima. Sisa hutang: Rp" + sisaBaru);
                    }

                    loadData(); // refresh tampilan
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Jumlah tidak valid.");
                }
            });

            panelBayar.add(tfJumlah);
            panelBayar.add(btnBayar);

            card.add(panelBayar, BorderLayout.EAST);

            listPanel.add(card);
            listPanel.add(Box.createVerticalStrut(10));
        }

        listPanel.revalidate();
        listPanel.repaint();
    }
}
