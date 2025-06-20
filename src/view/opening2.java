package view;

import javax.swing.JOptionPane;
import javax.swing.*;
import view.login;

public class opening2 extends javax.swing.JFrame {

    public opening2() {
        initComponents();
        setBackground(new java.awt.Color(0, 0, 0, 0));
        startLoading();
    }

    //Fungsi untuk Loading
    private void startLoading() {
        new Thread(() -> {
            opening2 sp = opening2.this; 
            sp.setVisible(true);

        try {
            for (int i = 0; i <= 100; i++) {
            Thread.sleep(100);
            sp.LoadingValue.setText(i + "%");
            
            if (i==10){ 
            sp.LoadingLabel.setText("Turning On Modules...");
            }

            if (i==20){
            sp.LoadingLabel.setText("Loading Modules...");
            }

            if (i==50) {
            sp.LoadingLabel.setText("Connecting to Database..."); 
            }

            if (i==70){ 
            sp.LoadingLabel.setText("Connecting Successful!");
            }

            if (i==80){ 
            sp.LoadingLabel.setText("Launching Application");
            }

            sp.LoadingBar.setValue(i);
        }

            } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
            } 

            dispose();
            new login().setVisible(true);
            }).start();
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BackGroundPanel = new javax.swing.JPanel();
        uASPBO1 = new view.UASPBO();
        Logo = new javax.swing.JLabel();
        LoadingBar = new javax.swing.JProgressBar();
        LoadingLabel = new javax.swing.JLabel();
        LoadingValue = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        BackGroundPanel.setBackground(new java.awt.Color(51, 255, 255));
        BackGroundPanel.setOpaque(false);
        BackGroundPanel.setPreferredSize(new java.awt.Dimension(900, 500));

        uASPBO1.setBackground(new java.awt.Color(255, 235, 202));
        uASPBO1.setRoundBottomLeft(70);
        uASPBO1.setRoundBottomRight(70);
        uASPBO1.setRoundTopLeft(100);
        uASPBO1.setRoundTopRight(100);

        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/logo_merah-removebg-preview.png"))); // NOI18N

        LoadingLabel.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LoadingLabel.setText("Loading....");

        LoadingValue.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        LoadingValue.setText("0 %");

        javax.swing.GroupLayout uASPBO1Layout = new javax.swing.GroupLayout(uASPBO1);
        uASPBO1.setLayout(uASPBO1Layout);
        uASPBO1Layout.setHorizontalGroup(
            uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO1Layout.createSequentialGroup()
                .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(uASPBO1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(LoadingLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LoadingBar, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(uASPBO1Layout.createSequentialGroup()
                        .addGap(860, 860, 860)
                        .addComponent(LoadingValue, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(uASPBO1Layout.createSequentialGroup()
                    .addGap(146, 146, 146)
                    .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 660, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(114, Short.MAX_VALUE)))
        );
        uASPBO1Layout.setVerticalGroup(
            uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uASPBO1Layout.createSequentialGroup()
                .addContainerGap(407, Short.MAX_VALUE)
                .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LoadingLabel)
                    .addGroup(uASPBO1Layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(LoadingBar, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LoadingValue))
                .addGap(43, 43, 43))
            .addGroup(uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(uASPBO1Layout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addComponent(Logo, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(80, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout BackGroundPanelLayout = new javax.swing.GroupLayout(BackGroundPanel);
        BackGroundPanel.setLayout(BackGroundPanelLayout);
        BackGroundPanelLayout.setHorizontalGroup(
            BackGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BackGroundPanelLayout.createSequentialGroup()
                .addComponent(uASPBO1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20))
        );
        BackGroundPanelLayout.setVerticalGroup(
            BackGroundPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uASPBO1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BackGroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(BackGroundPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    //Main Program
    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(opening2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(opening2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(opening2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(opening2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
           
        java.awt.EventQueue.invokeLater(() -> {
            new opening2().setVisible(true);
        });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BackGroundPanel;
    private javax.swing.JProgressBar LoadingBar;
    private javax.swing.JLabel LoadingLabel;
    private javax.swing.JLabel LoadingValue;
    private javax.swing.JLabel Logo;
    private view.UASPBO uASPBO1;
    // End of variables declaration//GEN-END:variables
}
