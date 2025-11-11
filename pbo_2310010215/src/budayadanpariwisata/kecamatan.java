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
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author DIMZY
 */
public class kecamatan {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010215";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_NAMAKECAMATAN=null;
    public boolean duplikasi=false;
    
    public kecamatan(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpankecamatan01(String id, String nm){
        try {
            String sqlsimpan="insert into kecamatan(id, namakecamatan) VALUES"
                    + " ('"+id+"', '"+nm+"')";
            String sqlcari="select*from kecamatan where id='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Kecamatan sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
        public void simpankecamatan02(String id, String nm) {
    try {
        String sqlsimpan = "INSERT INTO kecamatan(id, namakecamatan) VALUES (?, ?)";
        String sqlcari = "SELECT * FROM kecamatan WHERE id=?";

        PreparedStatement cari = Koneksidb.prepareStatement(sqlcari);
        cari.setString(1, id);
        ResultSet data = cari.executeQuery();

        if (data.next()) {
            JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
            this.duplikasi = true;
            this.CEK_NAMAKECAMATAN = data.getString("namakecamatan");
        } else {
            this.duplikasi = false;
            this.CEK_NAMAKECAMATAN = null;
            PreparedStatement perintah = Koneksidb.prepareStatement(sqlsimpan);
            perintah.setString(1, id);
            perintah.setString(2, nm);
            perintah.executeUpdate();

            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, e.getMessage());
    }
}


     
     public void ubahKecamatan(String id, String nm){
        try {
            String sqlubah="UPDATE kecamatan SET namakecamatan=? WHERE id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, nm);
                perintah.setString(2, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
     
      public void hapusKecamatan(String id){
        try {
            String sqlhapus="delete from kecamatan where id=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
      
       public void tampilDataKecamatan(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Kecamatan");
            modeltabel.addColumn("Nama Kecamatan");
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



