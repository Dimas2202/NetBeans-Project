/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package budayadanpariwisata;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;
import java.sql.ResultSetMetaData;


/**
 *
 * @author DIMZY
 */
public class kategori {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010215";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_JUDUL, CEK_GAMBAR, CEK_BODY=null;
    public boolean duplikasi=false;
    
    public kategori(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpankategori01(String id, String nm, String judul, String gambar, String body){
        try {
            String sqlsimpan="insert into kategori(idkategori, judul, gambar, body) VALUES"
                    + " ('"+id+"', '"+nm+"', '"+judul+"', '"+gambar+"', '"+body+"')";
            String sqlcari="select*from kategori where idkategori='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Kategori sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
        public void simpankategori02(String id, String judul, String gambar, String body) {
    try {
        String sqlsimpan = "INSERT INTO kategori(idkategori, judul, gambar, body) VALUES (?, ?, ?, ?)";
        String sqlcari = "SELECT * FROM kategori WHERE idkategori=?";

        PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
        cari.setString(1, id);
        ResultSet data = cari.executeQuery();

        if (data.next()) {
            JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
            this.duplikasi = true;
            this.CEK_JUDUL = data.getString("judul");
            this.CEK_GAMBAR = data.getString("gambar");
            this.CEK_BODY = data.getString("body");
        } else {
            this.duplikasi = false;
            this.CEK_JUDUL = null;
            this.CEK_GAMBAR = null;
            this.CEK_BODY = null;

            PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
            perintah.setString(1, id);
            perintah.setString(2, judul);
            perintah.setString(3, gambar);
            perintah.setString(4, body);
            perintah.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}


     
     public void ubahKategori(String id, String judul, String gambar, String body){
        try {
            String sqlubah="UPDATE kategori SET judul=?, gambar=?, body=? WHERE idkategori=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, judul);
                perintah.setString(2, gambar);
                perintah.setString(3, body);
                perintah.setString(4, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
     
      public void hapusKategori(String id){
        try {
            String sqlhapus="delete from kategori where idkategori=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
      
       public void tampilDataKategori(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Kategori");
            modeltabel.addColumn("Judul");
            modeltabel.addColumn("Gambar");
            modeltabel.addColumn("Body");
            while(data.next()){
                Object[] row = new Object[jumlahkolom];
                for(int i=1; i<=jumlahkolom; i++){
                    row[i-1]=data.getObject(i);
                }
                modeltabel.addRow(row);
            }
            komponentabel.setModel(modeltabel);
        } catch (Exception e) {
        }
    }    
}



