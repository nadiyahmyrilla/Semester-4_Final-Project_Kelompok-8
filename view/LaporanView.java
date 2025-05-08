// view/LaporanView.java
package view;

import javax.swing.*;

public class LaporanView extends JFrame {
    public LaporanView() {
        setTitle("Laporan Keuangan");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea("Laporan Keuangan akan ditampilkan di sini.");
        add(new JScrollPane(area));

        setVisible(true);
    }
}
