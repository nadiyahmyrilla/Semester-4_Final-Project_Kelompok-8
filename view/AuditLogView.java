// view/AuditLogView.java
package view;

import javax.swing.*;

public class AuditLogView extends JFrame {
    public AuditLogView() {
        setTitle("Audit Log");
        setSize(400, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JTextArea area = new JTextArea("Audit Log ditampilkan di sini.");
        add(new JScrollPane(area));

        setVisible(true);
    }
}
