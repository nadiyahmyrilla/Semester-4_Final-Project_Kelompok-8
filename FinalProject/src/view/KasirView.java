package view;

import controllerGUI.CicilanControllerGUI;
import controllerGUI.PembelianControllerGUI;
import controllerGUI.PenjualanControllerGUI;

import javax.swing.*;
import java.awt.*;

public class KasirView extends JFrame {

    private JPanel panelPenjualan, panelPembelian, panelCicilan;
    private CardLayout cardLayout;

    public KasirView() {
        setTitle("Kasir - Bening Work");
        setSize(800, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new FlowLayout());
        JButton btnPenjualan = new JButton("Penjualan");
        JButton btnPembelian = new JButton("Pembelian");
        JButton btnCicilan = new JButton("Cicilan");

        panelTop.add(btnPenjualan);
        panelTop.add(btnPembelian);
        panelTop.add(btnCicilan);
        add(panelTop, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        JPanel contentPanel = new JPanel(cardLayout);

        panelPenjualan = new PenjualanControllerGUI();
        panelPembelian = new PembelianControllerGUI();
        panelCicilan = new CicilanControllerGUI(); // Ganti dengan panel

        contentPanel.add(panelPenjualan, "Penjualan");
        contentPanel.add(panelPembelian, "Pembelian");
        contentPanel.add(panelCicilan, "Cicilan");

        add(contentPanel, BorderLayout.CENTER);

        btnPenjualan.addActionListener(e -> cardLayout.show(contentPanel, "Penjualan"));
        btnPembelian.addActionListener(e -> cardLayout.show(contentPanel, "Pembelian"));
        btnCicilan.addActionListener(e -> cardLayout.show(contentPanel, "Cicilan"));

        setLocationRelativeTo(null);
        setVisible(true);
    }
}
