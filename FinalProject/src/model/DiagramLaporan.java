package model;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.*;
import javax.imageio.ImageIO;


public class DiagramLaporan {

    public Image getDiagramBarangTerjual(List<DetailPenjualan> data) throws Exception {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Integer> map = new HashMap<>();
        for (DetailPenjualan dp : data) {
            map.merge(dp.getNamaBarang(), dp.getJumlah(), Integer::sum);
        }
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            dataset.addValue(entry.getValue(), "Barang", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Jumlah Barang Terjual Hari Ini", "Barang", "Jumlah",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        BufferedImage image = chart.createBufferedImage(500, 300);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return Image.getInstance(baos.toByteArray());
    }

    public Image getDiagramPemasukanPengeluaran(double pemasukan, double pengeluaran) throws Exception {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.addValue(pemasukan, "Jumlah", "Pemasukan");
        dataset.addValue(pengeluaran, "Jumlah", "Pengeluaran");

        JFreeChart chart = ChartFactory.createBarChart(
                "Pemasukan vs Pengeluaran Hari Ini", "Kategori", "Jumlah (Rp)",
                dataset, PlotOrientation.VERTICAL, false, true, false);

        BufferedImage image = chart.createBufferedImage(500, 300);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos);
        return Image.getInstance(baos.toByteArray());
    }

    public void getDiagramPDF(Document document,
                                     List<DetailPenjualan> penjualanHariIni,
                                     double totalPemasukan,
                                     double totalPengeluaran) throws Exception {

        Image chartBarang = getDiagramBarangTerjual(penjualanHariIni);
        chartBarang.setAlignment(Image.ALIGN_CENTER);
        document.add(new Paragraph("Diagram Jumlah Barang Terjual:"));
        document.add(chartBarang);
        document.add(Chunk.NEWLINE);

        Image chartKeuangan = getDiagramPemasukanPengeluaran(totalPemasukan, totalPengeluaran);
        chartKeuangan.setAlignment(Image.ALIGN_CENTER);
        document.add(new Paragraph("Diagram Pemasukan vs Pengeluaran:"));
        document.add(chartKeuangan);
    }
}
