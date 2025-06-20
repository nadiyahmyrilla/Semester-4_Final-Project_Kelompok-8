package view;
        
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class koneksi {
    private String url="jdbc:mysql://localhost:3306/bening_work";
    private String DB_USER = "root";
    private String DB_PASSWORD = "";
    private Connection con;

    public void connect(){
        try {
            con = DriverManager.getConnection(url, DB_USER, DB_PASSWORD);
            System.out.println("Koneksi Berhasil");
        }catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    public Connection getCon() {
        return con;
    }
}
