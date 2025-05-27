-- Database --
CREATE DATABASE bening_work;

-- Tabel pengguna untuk login (opsional)
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    PASSWORD VARCHAR(255)
);

-- Tabel barang
CREATE TABLE barang (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nama VARCHAR(100),
    harga_beli DECIMAL(10,2),
    harga_jual DECIMAL(10,2),
    stok INT
);
ALTER TABLE barang ADD COLUMN foto VARCHAR(255); -- Run terpisah karena variabel tambahan baru --

-- Tabel pemasukan
CREATE TABLE pemasukan (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tanggal DATE,
    total DECIMAL(10,2),
    keterangan TEXT
);

-- Tabel pengeluaran
CREATE TABLE pengeluaran (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tanggal DATE,
    total DECIMAL(10,2),
    barang_id INT,
    jumlah INT,
    FOREIGN KEY (barang_id) REFERENCES barang(id)
);

-- Tabel penjualan (kasir)
CREATE TABLE penjualan (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tanggal DATE,
    total DECIMAL(10,2),
    jenis_pembayaran ENUM('tunai', 'hutang'),
    status_hutang ENUM('lunas', 'belum lunas'),
    nama_pelanggan VARCHAR(100)
);

-- Detail penjualan
CREATE TABLE detail_penjualan (
    id INT PRIMARY KEY AUTO_INCREMENT,
    penjualan_id INT,
    barang_id INT,
    jumlah INT,
    harga_satuan DECIMAL(10,2),
    FOREIGN KEY (penjualan_id) REFERENCES penjualan(id),
    FOREIGN KEY (barang_id) REFERENCES barang(id)
);

-- Cicilan hutang
CREATE TABLE cicilan (
    id INT PRIMARY KEY AUTO_INCREMENT,
    penjualan_id INT,
    tanggal DATE,
    jumlah_bayar DECIMAL(10,2),
    FOREIGN KEY (penjualan_id) REFERENCES penjualan(id)
);

-- Tabel log audit
CREATE TABLE audit_log (
    id INT PRIMARY KEY AUTO_INCREMENT,
    tanggal TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    aksi TEXT
);

-- DATA DAMMY ---
-- users
INSERT INTO users (username, PASSWORD) VALUES
('admin', 'admin123'), 
('kasir1', 'password1');

-- barang
INSERT INTO barang (nama, harga_beli, harga_jual, stok) VALUES
('Semen (10 gr)', 2000, 2300, 100),
('Baut (5 gr)', 1500, 1725, 200),
('Selotip Hitam', 1000, 1150, 150),
('Kawat (2 m)', 1000, 1150, 100);

-- pemasukan
INSERT INTO pemasukan (tanggal, total, keterangan) VALUES
('2025-05-01', 50000, 'Penjualan harian tanggal 1'),
('2025-05-02', 65000, 'Penjualan harian tanggal 2');

-- pengeluaran
INSERT INTO pengeluaran (tanggal, total, barang_id, jumlah) VALUES
('2025-05-01', 40000, 1, 20),
('2025-05-02', 30000, 2, 20);

-- penjualan
INSERT INTO penjualan (tanggal, total, jenis_pembayaran, status_hutang, nama_pelanggan) VALUES
('2025-05-01', 15000, 'tunai', 'lunas','w'),
('2025-05-02', 30000, 'hutang', 'belum lunas''w');

-- detail_penjualan
INSERT INTO detail_penjualan (penjualan_id, barang_id, jumlah, harga_satuan) VALUES
(1, 1, 2, 3450),
(1, 2, 3, 2875),
(2, 3, 5, 1150),
(2, 4, 4, 2070);

-- cicilan
INSERT INTO cicilan (penjualan_id, tanggal, jumlah_bayar) VALUES
(2, '2025-05-03', 10000),
(2, '2025-05-04', 5000);

-- audit_log
INSERT INTO audit_log (aksi) VALUES
('Login oleh admin'),
('Penjualan oleh kasir1 (tunai)'),
('Penjualan oleh kasir1 (hutang)'),
('Tambah stok oleh admin'),
('Edit harga barang Pulpen oleh admin');
