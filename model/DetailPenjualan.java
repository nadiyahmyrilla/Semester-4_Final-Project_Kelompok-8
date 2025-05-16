package model;

public class DetailPenjualan {
    private int id;
    private int penjualan_id;
    private int barang_id;
    private int jumlah;
    private double harga_satuan;
    private String namaBarang;

    public DetailPenjualan(int id, int penjualan_id, int barang_id, int jumlah, double harga_satuan) {
        this.id = id;
        this.penjualan_id = penjualan_id;
        this.barang_id = barang_id;
        this.jumlah = jumlah;
        this.harga_satuan = harga_satuan;
    }

    // Getter dan Setter
    public int getId() { return id; }
    public int getPenjualan_id() { return penjualan_id; }
    public int getBarang_id() { return barang_id; }
    public int getJumlah() { return jumlah; }
    public double getHargaSatuan() { return harga_satuan; }

    public String getNamaBarang() { return namaBarang; }
    public void setNamaBarang(String namaBarang) { this.namaBarang = namaBarang; }
}
