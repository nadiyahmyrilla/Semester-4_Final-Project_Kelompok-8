package view;

import controller.AuditLogController;
import controller.UserController;
import model.User;
import style.BlurPopupDialog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.net.URL;

public class login extends javax.swing.JFrame {

    public login() {
        initComponents();
        setLocationRelativeTo(null);
        resizeLogo();
        setBackground(new java.awt.Color(0, 0, 0, 0)); // Alpha = 0 untuk transparan total
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel4 = new javax.swing.JPanel();
        uASPBO3 = new view.UASPBO();
        jLabel2 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        txtpassword = new javax.swing.JTextField();
        Login = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        uASPBO1 = new view.UASPBO();
        logo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        uASPBO2 = new view.UASPBO();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);

        jPanel4.setBackground(new java.awt.Color(204, 204, 204));
        jPanel4.setFont(new java.awt.Font("DeVinne Txt BT", 1, 12)); // NOI18N
        jPanel4.setOpaque(false);
        jPanel4.setPreferredSize(new java.awt.Dimension(1080, 720));

        uASPBO3.setBackground(new java.awt.Color(242, 146, 148));
        uASPBO3.setRoundBottomRight(100);

        jLabel2.setFont(new java.awt.Font("Cambria", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 235, 202));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("LOGIN");

        txtusername.setBackground(new java.awt.Color(237, 219, 195));
        txtusername.setFont(new java.awt.Font("DeVinne Txt BT", 1, 12)); // NOI18N
        txtusername.setForeground(new java.awt.Color(159, 2, 22));
        txtusername.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtusernameActionPerformed(evt);
            }
        });

        txtpassword.setBackground(new java.awt.Color(237, 219, 195));
        txtpassword.setFont(new java.awt.Font("DeVinne Txt BT", 1, 12)); // NOI18N
        txtpassword.setForeground(new java.awt.Color(159, 2, 22));
        txtpassword.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtpassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtpasswordActionPerformed(evt);
            }
        });

        Login.setBackground(new java.awt.Color(159, 2, 22));
        Login.setFont(new java.awt.Font("Britannic Bold", 0, 18)); // NOI18N
        Login.setForeground(new java.awt.Color(255, 235, 202));
        Login.setText("Login");
        Login.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                LoginActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Britannic Bold", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 235, 202));
        jLabel3.setText("Username");

        jLabel4.setFont(new java.awt.Font("Britannic Bold", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 235, 202));
        jLabel4.setText("Password");

        javax.swing.GroupLayout uASPBO3Layout = new javax.swing.GroupLayout(uASPBO3);
        uASPBO3.setLayout(uASPBO3Layout);
        uASPBO3Layout.setHorizontalGroup(
            uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uASPBO3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uASPBO3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(219, 219, 219))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uASPBO3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(165, 165, 165))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uASPBO3Layout.createSequentialGroup()
                .addContainerGap(121, Short.MAX_VALUE)
                .addGroup(uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(109, 109, 109))
        );
        uASPBO3Layout.setVerticalGroup(
            uASPBO3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uASPBO3Layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(jLabel2)
                .addGap(55, 55, 55)
                .addComponent(jLabel3)
                .addGap(15, 15, 15)
                .addComponent(txtusername, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addComponent(jLabel4)
                .addGap(15, 15, 15)
                .addComponent(txtpassword, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(Login, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(146, Short.MAX_VALUE))
        );

        uASPBO1.setBackground(new java.awt.Color(255, 235, 202));
        uASPBO1.setPreferredSize(new java.awt.Dimension(1080, 600));
        uASPBO1.setRoundBottomLeft(100);

        logo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/asset/logo_merah.png"))); // NOI18N

        jPanel1.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout uASPBO1Layout = new javax.swing.GroupLayout(uASPBO1);
        uASPBO1.setLayout(uASPBO1Layout);
        uASPBO1Layout.setHorizontalGroup(
            uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1348, 1348, 1348)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 11, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        uASPBO1Layout.setVerticalGroup(
            uASPBO1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO1Layout.createSequentialGroup()
                .addGap(128, 128, 128)
                .addComponent(logo, javax.swing.GroupLayout.PREFERRED_SIZE, 313, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(126, Short.MAX_VALUE))
        );

        uASPBO2.setBackground(new java.awt.Color(216, 46, 48));
        uASPBO2.setRoundTopLeft(100);
        uASPBO2.setRoundTopRight(100);

        jLabel1.setFont(new java.awt.Font("Calisto MT", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(228, 202, 152));
        jLabel1.setText("Sistem Toko Bangunan");

        jLabel5.setBackground(new java.awt.Color(237, 219, 195));
        jLabel5.setFont(new java.awt.Font("Calisto MT", 1, 48)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(228, 202, 152));
        jLabel5.setText("BENING WORK");

        javax.swing.GroupLayout uASPBO2Layout = new javax.swing.GroupLayout(uASPBO2);
        uASPBO2.setLayout(uASPBO2Layout);
        uASPBO2Layout.setHorizontalGroup(
            uASPBO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, uASPBO2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(410, 410, 410))
        );
        uASPBO2Layout.setVerticalGroup(
            uASPBO2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uASPBO2Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(uASPBO2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(uASPBO1, javax.swing.GroupLayout.PREFERRED_SIZE, 525, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 555, Short.MAX_VALUE))))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addGap(0, 521, Short.MAX_VALUE)
                    .addComponent(uASPBO3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(uASPBO2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(uASPBO1, javax.swing.GroupLayout.PREFERRED_SIZE, 583, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(128, Short.MAX_VALUE)
                    .addComponent(uASPBO3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 714, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Untuk mengarahkan ke halaman home, jika proses login benar
    private void LoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_LoginActionPerformed
        String username = txtusername.getText();
        String password = txtpassword.getText();

        UserController userController = new UserController();
        User user = userController.authenticate(username, password); // pakai User, bukan boolean

        if (user != null) { //Memunculkan PopUp, jika proses login berhasil
            new BlurPopupDialog(this,
                    "Welcome!",
                    "Login berhasil sebagai <b>" + user.getUsername() + "</b>",
                    new String[]{"Continue"},
                    new ActionListener[]{
                        e -> {
                            new home().setVisible(true); //Lalu diarahkan ke Home
                            dispose();
                        }
                    }
            ).setVisible(true);
            //Memunculkan informasi jika gagal login
        } else {
            new BlurPopupDialog(this,
                    "Login Gagal",
                    "Username atau password salah.",
                    new String[]{"Coba Lagi"},
                    new ActionListener[]{
                        e -> {
                        } // Tidak melakukan apa pun, hanya menutup popup
                    }
            ).setVisible(true);
        }

    }//GEN-LAST:event_LoginActionPerformed

    private void txtpasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtpasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtpasswordActionPerformed

    private void txtusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusernameActionPerformed

    //Untuk resize logo
    private void resizeLogo() {
        URL imageURL = getClass().getResource("/asset/logo_merah.png");
        if (imageURL != null) {
            ImageIcon icon = new ImageIcon(imageURL);
            Image img = icon.getImage().getScaledInstance(400, 250, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(img));
            logo.setPreferredSize(new Dimension(770, 500));
            logo.revalidate();
        } else {
            System.out.println("Gambar tidak ditemukan!");
        }

    }

    //Main Frame
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Login;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel logo;
    private javax.swing.JTextField txtpassword;
    private javax.swing.JTextField txtusername;
    private view.UASPBO uASPBO1;
    private view.UASPBO uASPBO2;
    private view.UASPBO uASPBO3;
    // End of variables declaration//GEN-END:variables
}
