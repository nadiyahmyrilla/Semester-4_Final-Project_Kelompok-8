// view/LoginView.java
package view;

import controller.AuditLogController;
import controller.UserController;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginView extends JFrame {
    private JTextField tfUsername;
    private JPasswordField tfPassword;
    private JButton btnLogin;

    public LoginView() {
        setTitle("Login - Bening Work");
        setSize(300, 150);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        tfUsername = new JTextField();
        tfPassword = new JPasswordField();
        btnLogin = new JButton("Login");

        add(new JLabel("Username:"));
        add(tfUsername);
        add(new JLabel("Password:"));
        add(tfPassword);
        add(new JLabel());
        add(btnLogin);

        btnLogin.addActionListener((ActionEvent e) -> login());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void login() {
        String username = tfUsername.getText();
        String password = new String(tfPassword.getPassword());

        UserController userController = new UserController();
        User user = userController.authenticate(username, password);

        if (user != null) {
            JOptionPane.showMessageDialog(this, "Login berhasil sebagai: " + user.getUsername());
            new DashboardView(); // lanjut ke dashboard
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Login gagal. Username atau password salah.");
        }
        //Logging user
        new AuditLogController().catatLog("Login oleh pengguna: " + username);

    }
}
