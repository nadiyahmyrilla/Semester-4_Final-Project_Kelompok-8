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
    private PenjualanController pc = new PenjualanController();
    private CicilanController cc = new CicilanController();
    private PemasukanController pec = new PemasukanController();
    private JPanel listPanel;

    public CicilanControllerGUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(255, 235, 202));


        JButton btnRefresh = new JButton("Muat Ulang Hutang");
        btnRefresh.setBackground(new Color(102, 51, 0));
        btnRefresh.setForeground(new Color(225, 235, 202));
        btnRefresh.addActionListener(e -> loadData());
        add(btnRefresh, BorderLayout.NORTH);

         listPanel = new JPanel();
        listPanel.setBackground(new Color(255, 235, 202));
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(153, 0, 0), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

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
           card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(153, 0, 0), 2),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
           card.setBackground(new Color(255, 235, 202));
            card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

            // Info pelanggan
            JLabel info = new JLabel("<html><b>Nama Pelanggan:</b> " + p.getNamaPelanggan() +
                    "<br><b>Tanggal:</b> " + p.getTanggal() +
                    "<br><b>Total:</b> Rp" + p.getTotal() +
                    "<br><b>Sisa Hutang:</b> Rp" + sisa + "</html>");
            card.add(info, BorderLayout.WEST);

            // Panel form bayar
             JPanel panelBayar = new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
            panelBayar.setBackground(new Color(255, 235, 202));
            panelBayar.setMaximumSize(new Dimension(500, 80));
            JTextField tfJumlah = new JTextField();
            tfJumlah.setMaximumSize(new Dimension(500, 80));
            tfJumlah.setBackground(new Color(147, 83, 12));
            JButton btnBayar = new JButton("Bayar");
            btnBayar.setMaximumSize(new Dimension(300, 250));
            btnBayar.setBackground(new Color(153, 0, 0));
            btnBayar.setForeground(new Color(225,235,202));

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

                    loadData(); // refresh tampilan
                } catch (NumberFormatException ex) {
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
