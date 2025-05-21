package model;

import java.util.Date;

public class AuditLog {
    private int id;
    private Date tanggal;
    private String aksi;

    public AuditLog(int id, Date tanggal, String aksi) {
        this.id = id;
        this.tanggal = tanggal;
        this.aksi = aksi;
    }

    public int getId() { 
        return id; 
    }
    public Date getTanggal() { 
        return tanggal; 
    }
    public String getAksi() { 
        return aksi; 
    }
}
