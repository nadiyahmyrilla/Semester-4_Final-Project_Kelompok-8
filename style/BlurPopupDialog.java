package style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BlurPopupDialog extends JDialog {

    public BlurPopupDialog(JFrame parent, String title, String message,
                           String[] buttonLabels, ActionListener[] buttonActions) {
        super(parent, true);
        setUndecorated(true);
        setSize(350, 250);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        // Simulasi efek blur dengan panel semi-transparan di parent
        JPanel glassPane = new JPanel();
        glassPane.setBackground(new Color(0, 0, 0, 100));
        glassPane.setOpaque(true);
        parent.setGlassPane(glassPane);
        glassPane.setVisible(true);

        // Panel utama
        GradientPanel panel = new GradientPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(new Color(159, 2, 100));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblMessage = new JLabel("<html><div style='text-align: center;'>" + message + "</div></html>");
        lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblMessage.setForeground(new Color(159, 2, 100));
        lblMessage.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttonPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setOpaque(false);
            }
        };
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

        final Color defaultColor = new Color(159, 2, 100);        // Default button color
        final Color hoverColor = new Color(254, 133, 124);        // Hover background
        final Color hoverTextColor = new Color(237, 219, 195);    // Hover text color
        final Color defaultTextColor = new Color(159, 2, 100);

        for (int i = 0; i < buttonLabels.length; i++) {
            JButton btn = new JButton(buttonLabels[i]);
            btn.setBackground(defaultColor);
            btn.setForeground(defaultTextColor);
            btn.setFocusPainted(false);
            btn.setContentAreaFilled(true);
            btn.setOpaque(true);
            btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
            btn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

            // Hover effect
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(hoverColor);
                    btn.setForeground(hoverTextColor);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(defaultColor);
                    btn.setForeground(defaultTextColor);
                }
            });

            final int index = i;
            btn.addActionListener(e -> {
                buttonActions[index].actionPerformed(e);
                glassPane.setVisible(false); // Matikan efek blur
                dispose();
            });
            buttonPanel.add(btn);
        }

        buttonPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        // Tambahkan glue dan komponen ke panel utama
        panel.add(Box.createVerticalGlue());
        panel.add(lblTitle);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(lblMessage);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(buttonPanel);
        panel.add(Box.createVerticalGlue());
        add(panel, BorderLayout.CENTER);

        setShape(new java.awt.geom.RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
    }
}

// Panel dengan gradasi 5 warna
class GradientPanel extends JPanel {
    private final Color[] colors = {
        Color.decode("#eddbc3"),
        Color.decode("#f29294")
    };

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        int height = getHeight();
        float sectionHeight = height / (float)(colors.length - 1);
        for (int i = 0; i < colors.length - 1; i++) {
            int yStart = (int)(i * sectionHeight);
            int yEnd = (int)((i + 1) * sectionHeight);
            GradientPaint gp = new GradientPaint(0, yStart, colors[i], 0, yEnd, colors[i + 1]);
            g2.setPaint(gp);
            g2.fillRect(0, yStart, getWidth(), yEnd - yStart);
        }
        g2.dispose();
    }
}