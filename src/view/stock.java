package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

import controller.BarangController;
import controllerGUI.PembelianControllerGUI;
import controllerGUI.PenjualanControllerGUI;
import model.Barang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import style.BlurPopupDialog;

public class stock extends javax.swing.JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private DefaultTableModel model;
    private javax.swing.JTextField textFieldHargaBeli;
    private javax.swing.JTextField textFieldStok;
    private javax.swing.JButton JButton2;
    private int Id = 1;
    private File selectedImageFile = null;

    private DefaultTableModel dm;
    private TableRowSorter<DefaultTableModel> tr;
    public static stock instance;

    public stock() {
        instance = this;
        initComponents();
        tampilkanData();
        aktifkanTombol();

        //Untuk merefresh data otomatis di stock, supaya pengurangan atau penambahan barang di kasir terdeteksi
        addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                refreshBarangDiStock();
            }

            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }

            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        
        //Membuaat isi cell rata tengah
        jTable1.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                label.setHorizontalAlignment(SwingConstants.CENTER);
                return label;
            }
        });

        //Memformat header tabel
        jTable1.getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = (JLabel) super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                label.setBackground(new Color(159, 2, 2));  // warna biru
                label.setForeground(new Color(237, 219, 195)); // teks cream
                label.setHorizontalAlignment(JLabel.CENTER); // opsional: teks di tengah
                label.setFont(label.getFont().deriveFont(Font.BOLD)); // font tebal
                label.setOpaque(true);                           // supaya background muncul
                return label;
            }
        });
        // Buat latar form transparan (kalau didukung Look & Feel)
        setBackground(new java.awt.Color(0, 0, 0, 0));

        // Hilangkan grid di dalam tabel
        jTable1.setShowGrid(false);
        jTable1.setIntercellSpacing(new java.awt.Dimension(0, 0));
        jTable1.setOpaque(false);
        ((DefaultTableCellRenderer) jTable1.getDefaultRenderer(Object.class)).setOpaque(false);

        // Buat scroll pane transparan dan hilangkan border
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        jScrollPane1.setBorder(null);
        jScrollPane1.getViewport().setBorder(null);

    }

    //Method untuk memfilter menggunakan search
    private void filter(String query) {
        TableRowSorter<DefaultTableModel> tr = (TableRowSorter<DefaultTableModel>) jTable1.getRowSorter();

        if (query == null || query.trim().isEmpty()) {
            tr.setRowFilter(null); // Hapus filter jika query kosong
        } else {
            // [TEMPATKAN KODE FILTER DI SINI]
            List<RowFilter<Object, Object>> filters = new ArrayList<>();
            for (int i = 0; i < jTable1.getColumnCount(); i++) {
                filters.add(RowFilter.regexFilter("(?i)" + query, i));
            }
            tr.setRowFilter(RowFilter.orFilter(filters));
        }
    }

    //Method untuk menampilkan PopUp pesan
    private void showBlurDialog(String message) {
        java.awt.EventQueue.invokeLater(() -> {
            JFrame parent = new JFrame();
            String title = "Pemberitahuan";

            String[] buttonLabels = {"OK"};

            ActionListener[] buttonActions = {
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        Component source = (Component) e.getSource();
                        Window dialog = SwingUtilities.getWindowAncestor(source);
                        dialog.dispose();
                    }
                }
            };

            BlurPopupDialog dialog = new BlurPopupDialog(parent, title, message, buttonLabels, buttonActions);
            dialog.setLocationRelativeTo(null);
            dialog.setVisible(true);
        });
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uASPBO1 = new view.UASPBO();
        uASPBO4 = new view.UASPBO();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        uASPBO3 = new view.UASPBO();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jButtonTambahBarang = new javax.swing.JButton();
        jTextFieldStok = new javax.swing.JTextField();
        jTextFieldHargaBeli = new javax.swing.JTextField();
        jTextFieldNamaBarang = new javax.swing.JTextField();
        jButtonTambahGambar = new javax.swing.JButton();
        jLabelNamaGambar = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jTextFieldSearch = new javax.swing.JTextField();

        uASPBO1.setBackground(new java.awt.Color(255, 235, 202));
        uASPBO1.setPreferredSize(new java.awt.Dimension(812, 485));
        uASPBO1.setRoundBottomLeft(100);
        uASPBO1.setRoundBottomRight(100);

        uASPBO4.setBackground(new java.awt.Color(242, 146, 148));
        uASPBO4.setRoundBottomLeft(50);
        uASPBO4.setRoundBottomRight(50);
        uASPBO4.setLayout(new java.awt.CardLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nama", "Harga Beli", "Harga Jual", "Stok"
            }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        }
    );
    jTable1.setOpaque(false);
    jScrollPane1.setViewportView(jTable1);

    uASPBO4.add(jScrollPane1, "card2");

    uASPBO3.setBackground(new java.awt.Color(255, 235, 202));

    jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
    jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
    jLabel10.setText("TAMBAH BARANG BARU");

    jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel11.setText("Harga Beli :");

    jLabel12.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel12.setText("Nama Barang :");

    jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
    jLabel13.setText("Stok :");

    jButtonTambahBarang.setBackground(new java.awt.Color(153, 0, 0));
    jButtonTambahBarang.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
    jButtonTambahBarang.setForeground(new java.awt.Color(255, 235, 202));
    jButtonTambahBarang.setText("Tambah Barang");
    jButtonTambahBarang.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonTambahBarangActionPerformed(evt);
        }
    });

    jTextFieldStok.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldStokActionPerformed(evt);
        }
    });

    jTextFieldHargaBeli.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldHargaBeliActionPerformed(evt);
        }
    });

    jTextFieldNamaBarang.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldNamaBarangActionPerformed(evt);
        }
    });

    jButtonTambahGambar.setBackground(new java.awt.Color(153, 0, 0));
    jButtonTambahGambar.setForeground(new java.awt.Color(255, 235, 202));
    jButtonTambahGambar.setText("Tambah Gambar");
    jButtonTambahGambar.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButtonTambahGambarActionPerformed(evt);
        }
    });

    jLabelNamaGambar.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    jLabelNamaGambar.setText("Belum ada Gambar");

    javax.swing.GroupLayout uASPBO3Layout = new javax.swing.GroupLayout(uASPBO3);
    uASPBO3.setLayout(uASPBO3Layout);
    uASPBO3Layout.setHorizontalGroup(
        uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(uASPBO3Layout.createSequentialGroup()
            .addGroup(uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(uASPBO3Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                        .addComponent(jButtonTambahGambar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(uASPBO3Layout.createSequentialGroup()
                            .addGap(20, 20, 20)
                            .addGroup(uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jTextFieldStok, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldHargaBeli, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jTextFieldNamaBarang, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(uASPBO3Layout.createSequentialGroup()
                            .addGap(18, 18, 18)
                            .addComponent(jLabelNamaGambar))))
                .addGroup(uASPBO3Layout.createSequentialGroup()
                    .addGap(68, 68, 68)
                    .addComponent(jButtonTambahBarang, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );
    uASPBO3Layout.setVerticalGroup(
        uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(uASPBO3Layout.createSequentialGroup()
            .addGap(12, 12, 12)
            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE)
            .addGap(12, 12, 12)
            .addGroup(uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(uASPBO3Layout.createSequentialGroup()
                    .addComponent(jTextFieldNamaBarang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextFieldHargaBeli, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jTextFieldStok, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(uASPBO3Layout.createSequentialGroup()
                    .addComponent(jLabel12)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel13)
                    .addGap(6, 6, 6)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jButtonTambahGambar)
                .addComponent(jLabelNamaGambar))
            .addGap(32, 32, 32)
            .addComponent(jButtonTambahBarang)
            .addContainerGap())
    );

    jLabel1.setFont(new java.awt.Font("Britannic Bold", 0, 48)); // NOI18N
    jLabel1.setForeground(new java.awt.Color(153, 0, 0));
    jLabel1.setText("STOK");

    jLabel14.setFont(new java.awt.Font("Serif", 0, 14)); // NOI18N
    jLabel14.setText("Search...");

    jTextFieldSearch.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextFieldSearchActionPerformed(evt);
        }
    });
    jTextFieldSearch.addKeyListener(new java.awt.event.KeyAdapter() {
        public void keyReleased(java.awt.event.KeyEvent evt) {
            jTextFieldSearchKeyReleased(evt);
        }
    });

    javax.swing.GroupLayout uASPBO1Layout = new javax.swing.GroupLayout(uASPBO1);
    uASPBO1.setLayout(uASPBO1Layout);
    uASPBO1Layout.setHorizontalGroup(
        uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(uASPBO1Layout.createSequentialGroup()
            .addGap(28, 28, 28)
            .addComponent(uASPBO4, javax.swing.GroupLayout.PREFERRED_SIZE, 442, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO1Layout.createSequentialGroup()
                .addContainerGap(586, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(153, 153, 153))
            .addGroup(uASPBO1Layout.createSequentialGroup()
                .addGap(481, 481, 481)
                .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(uASPBO1Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addComponent(uASPBO3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)))
    );
    uASPBO1Layout.setVerticalGroup(
        uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(uASPBO1Layout.createSequentialGroup()
            .addGap(25, 25, 25)
            .addComponent(uASPBO4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addContainerGap(58, Short.MAX_VALUE))
        .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO1Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(jLabel1)
                .addGap(50, 50, 50)
                .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(uASPBO3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE)))
    );

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(0, 0, 0)
            .addComponent(uASPBO1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addGap(0, 0, 0)
            .addComponent(uASPBO1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 0, Short.MAX_VALUE))
    );
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonTambahBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTambahBarangActionPerformed

    }//GEN-LAST:event_jButtonTambahBarangActionPerformed

    private void jTextFieldStokActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldStokActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldStokActionPerformed

    private void jTextFieldHargaBeliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldHargaBeliActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldHargaBeliActionPerformed

    private void jTextFieldNamaBarangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldNamaBarangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldNamaBarangActionPerformed

    private void jTextFieldSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchActionPerformed

    private void jTextFieldSearchKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldSearchKeyReleased
        if (jTextFieldSearch.getText() == null) {
            return;
        }
        String query = jTextFieldSearch.getText().toLowerCase();
        filter(query);        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldSearchKeyReleased

    private void jButtonTambahGambarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonTambahGambarActionPerformed
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showOpenDialog(null);
        if (option == JFileChooser.APPROVE_OPTION) {
            selectedImageFile = fileChooser.getSelectedFile();
            jLabelNamaGambar.setText(selectedImageFile.getName());
        }
    }//GEN-LAST:event_jButtonTambahGambarActionPerformed

    //Untuk merefresh data barang di stock
    public void refreshBarangDiStock() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0); // Menghapus semua baris dulu

        BarangController bc = new BarangController();
        ArrayList<Barang> daftar = bc.getAllBarang();

        for (Barang b : daftar) {
            model.addRow(new Object[]{
                b.getId(),
                b.getNama(),
                b.getHargaBeli(),
                b.getHargaJual(),
                b.getStok(),
                b.getFoto()
            });
        }
    }

    //Untuk menampilkan data
    private void tampilkanData() {
        // Buat model tabel baru dengan kolom yang sesuai
        model = new DefaultTableModel(
                new Object[]{"ID", "Nama", "Harga Beli", "Harga Jual", "Stok"}, 0
        );

        BarangController controller = new BarangController();
        List<Barang> listBarang = controller.getAllBarang();

        for (Barang b : listBarang) {
            model.addRow(new Object[]{
                b.getId(),
                b.getNama(),
                b.getHargaBeli(),
                b.getHargaJual(),
                b.getStok()
            });
        }

        jTable1.setModel(model);

        TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);
        jTable1.setRowSorter(sorter);
    }

    // Tambahan fungsi untuk mengaktifkan tombol
    private void aktifkanTombol() {

        // Tombol Tambah Barang
        jButtonTambahBarang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                String nama = jTextFieldNamaBarang.getText().trim();
                String hargaBeliText = jTextFieldHargaBeli.getText().trim();
                String stokText = jTextFieldStok.getText().trim();

                if (nama.isEmpty() || hargaBeliText.isEmpty() || stokText.isEmpty()) {
                    showBlurDialog("Harap isi semua kolom terlebih dahulu!");
                    return;
                }
                if (selectedImageFile == null) {
                    showBlurDialog("Silakan pilih gambar terlebih dahulu!");
                    return;
                }

                try {
                    double hargaBeli = Double.parseDouble(hargaBeliText);
                    double parsedHarga = Double.parseDouble(hargaBeliText);
                    int stok = Integer.parseInt(stokText);
                    String namaGambar = selectedImageFile.getName();

                    double hargaJual = parsedHarga * 1.15;

                    BarangController controller = new BarangController();
                    boolean success = controller.tambahBarang(nama, hargaBeli, hargaJual, stok, namaGambar);

                    if (success) {
                        jTextFieldNamaBarang.setText("");
                        jTextFieldHargaBeli.setText("");
                        jTextFieldStok.setText("");
                        tampilkanData();
                        showBlurDialog("Barang berhasil ditambahkan!");
                        jLabelNamaGambar.setText("Belum ada gambar");
                        selectedImageFile = null;
                        PenjualanControllerGUI.refreshBarangDiKasir();
                        PembelianControllerGUI.refreshBarangDiKasir();

                    } else {
                        showBlurDialog("Gagal menambahkan barang!");
                    }

                } catch (NumberFormatException e) {
                    showBlurDialog("Harga dan Stok harus berupa angka!");
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonTambahBarang;
    private javax.swing.JButton jButtonTambahGambar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabelNamaGambar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextFieldHargaBeli;
    private javax.swing.JTextField jTextFieldNamaBarang;
    private javax.swing.JTextField jTextFieldSearch;
    private javax.swing.JTextField jTextFieldStok;
    private view.UASPBO uASPBO1;
    private view.UASPBO uASPBO3;
    private view.UASPBO uASPBO4;
    // End of variables declaration//GEN-END:variables

}
