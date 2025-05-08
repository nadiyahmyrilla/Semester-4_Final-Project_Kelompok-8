// view/StokView.java
package view;

import controller.BarangController;
import model.Barang;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;

public class StokView extends JFrame {
    public StokView() {
        setTitle("Stok Barang");
        setSize(600, 400);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        String[] columnNames = {"ID", "Nama", "Harga Beli", "Harga Jual", "Stok"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(model);

        BarangController controller = new BarangController();
        ArrayList<Barang> list = controller.getAllBarang();

        for (Barang b : list) {
            model.addRow(new Object[]{b.getId(), b.getNama(), b.getHargaBeli(), b.getHargaJual(), b.getStok()});
        }

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        setVisible(true);
    }
}
