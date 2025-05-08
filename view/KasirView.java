// view/KasirView.java
package view;

import javax.swing.*;
import java.awt.*;

public class KasirView extends JFrame {
    public KasirView() {
        setTitle("Kasir - Bening Work");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panelTop = new JPanel(new FlowLayout());
        panelTop.add(new JLabel("Kasir Mode (Penjualan / Hutang / Pembelian)"));

        JTextArea area = new JTextArea("Fitur kasir akan ditampilkan di sini.");
        JScrollPane scroll = new JScrollPane(area);

        add(panelTop, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}
