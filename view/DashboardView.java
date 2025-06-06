// view/DashboardView.java
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class DashboardView extends JFrame {
    public DashboardView() {
        setTitle("Dashboard - Bening Work");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        JButton btnKasir = new JButton("Kasir");
        JButton btnStok = new JButton("Stok Barang");
        JButton btnLaporan = new JButton("Laporan Keuangan");
        JButton btnAudit = new JButton("Log Audit");

        btnKasir.addActionListener((ActionEvent e) -> new KasirView().setVisible(true));
        btnStok.addActionListener((ActionEvent e) -> new StokView().setVisible(true));
        btnLaporan.addActionListener((ActionEvent e) -> new LaporanView().setVisible(true));
        btnAudit.addActionListener((ActionEvent e) -> new AuditLogView().setVisible(true));

        add(btnKasir);
        add(btnStok);
        add(btnLaporan);
        add(btnAudit);

        setVisible(true);
    }
}
