package view;

import controllerGUI.CicilanControllerGUI;
import controllerGUI.PembelianControllerGUI;
import controllerGUI.PenjualanControllerGUI;

import javax.swing.*;
import java.awt.*;

public class kasir extends javax.swing.JPanel {
    //medeclare cardLayout
    private CardLayout cardLayout;

    public kasir() {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
        
        //Untuk membaca cardLayout yang dibuat di NetBeans
        cardLayout = (CardLayout) contentPanel.getLayout();

        //Set layar isi konten dengan panel-panel menu kasir
        contentPanel.add(new PenjualanControllerGUI(), "Penjualan");
        contentPanel.add(new PembelianControllerGUI(), "Pembelian");
        contentPanel.add(new CicilanControllerGUI(), "Cicilan");

        // Tampilkan panel awal
        cardLayout.show(contentPanel, "Penjualan");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        uASPBO1 = new view.UASPBO();
        uASPBO2 = new view.UASPBO();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        contentPanel = new javax.swing.JPanel();

        uASPBO1.setBackground(new java.awt.Color(255, 235, 202));
        uASPBO1.setPreferredSize(new java.awt.Dimension(812, 485));
        uASPBO1.setRoundBottomLeft(100);
        uASPBO1.setRoundBottomRight(100);

        uASPBO2.setBackground(new java.awt.Color(255, 235, 202));
        uASPBO2.setRoundBottomLeft(100);
        uASPBO2.setRoundBottomRight(100);

        jButton1.setBackground(new java.awt.Color(153, 0, 0));
        jButton1.setFont(new java.awt.Font("DeVinne Txt BT", 1, 12)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 235, 202));
        jButton1.setText("Penjualan");
        jButton1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton1.setBorderPainted(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setBackground(new java.awt.Color(153, 0, 0));
        jButton2.setFont(new java.awt.Font("DeVinne Txt BT", 1, 12)); // NOI18N
        jButton2.setForeground(new java.awt.Color(255, 235, 202));
        jButton2.setText("Cicilan");
        jButton2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton2.setBorderPainted(false);
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(153, 0, 0));
        jButton3.setFont(new java.awt.Font("DeVinne Txt BT", 1, 12)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 235, 202));
        jButton3.setText("Pembelian");
        jButton3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jButton3.setBorderPainted(false);
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        contentPanel.setBackground(new java.awt.Color(255, 235, 202));
        contentPanel.setEnabled(false);
        contentPanel.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout uASPBO2Layout = new javax.swing.GroupLayout(uASPBO2);
        uASPBO2.setLayout(uASPBO2Layout);
        uASPBO2Layout.setHorizontalGroup(
            uASPBO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(160, 160, 160)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(64, Short.MAX_VALUE))
            .addGroup(uASPBO2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(contentPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 788, Short.MAX_VALUE)
                .addContainerGap())
        );
        uASPBO2Layout.setVerticalGroup(
            uASPBO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(uASPBO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 36, Short.MAX_VALUE)
                .addComponent(contentPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 348, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        javax.swing.GroupLayout uASPBO1Layout = new javax.swing.GroupLayout(uASPBO1);
        uASPBO1.setLayout(uASPBO1Layout);
        uASPBO1Layout.setHorizontalGroup(
            uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 812, Short.MAX_VALUE)
            .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(uASPBO1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(uASPBO2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        uASPBO1Layout.setVerticalGroup(
            uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 485, Short.MAX_VALUE)
            .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(uASPBO1Layout.createSequentialGroup()
                    .addComponent(uASPBO2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 29, Short.MAX_VALUE)))
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
    
    //Tombol ke panel Penjualan
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        cardLayout.show(contentPanel, "Penjualan");
    }//GEN-LAST:event_jButton1ActionPerformed

    //Tombol ke panel Cicilan
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cardLayout.show(contentPanel, "Cicilan");
    }//GEN-LAST:event_jButton2ActionPerformed

    //Tombol ke panel Pembelian
    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        cardLayout.show(contentPanel, "Pembelian");
    }//GEN-LAST:event_jButton3ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private view.UASPBO uASPBO1;
    private view.UASPBO uASPBO2;
    // End of variables declaration//GEN-END:variables
}
