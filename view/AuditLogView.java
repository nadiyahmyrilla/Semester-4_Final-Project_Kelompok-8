package view;

import controller.AuditLogController;
import model.AuditLog;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AuditLogView extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private AuditLogController logController = new AuditLogController();

    public AuditLogView() {
        setTitle("Audit Log - Bening Work");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        initComponents();
        loadLog();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Tabel log
        tableModel = new DefaultTableModel(new Object[]{"ID", "Tanggal", "Aksi"}, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnHapus = new JButton("Hapus Log Terpilih");
        btnHapus.addActionListener(e -> hapusLogTerpilih());

        panelBottom.add(btnHapus);

        add(scrollPane, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void loadLog() {
        tableModel.setRowCount(0);
        List<AuditLog> logList = logController.getSemuaLog();
        for (AuditLog log : logList) {
            tableModel.addRow(new Object[]{
                log.getId(),
                log.getTanggal(),
                log.getAksi()
            });
        }
    }

    //hapus log
    private void hapusLogTerpilih() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int idLog = (int) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Yakin ingin menghapus log ini?", "Konfirmasi Hapus",
                    JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                logController.hapusLogById(idLog);
                loadLog();
                JOptionPane.showMessageDialog(this, "Log berhasil dihapus.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih log yang ingin dihapus terlebih dahulu.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AuditLogView().setVisible(true));
    }
}
