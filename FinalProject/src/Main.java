import javax.swing.*;

import view.LoginView;

public class Main {
    public static void main(String[] args) {
        // Set UI look and feel to be consistent with the system
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create and show the login view
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new LoginView();
            }
        });
    }
}

