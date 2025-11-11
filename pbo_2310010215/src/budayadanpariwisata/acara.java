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
public class acara {
    private Connection Koneksidb;
    private String username="root";
    private String password="";
    private String dbname="pbo_2310010215";
    private String urlKoneksi="jdbc:mysql://localhost/"+dbname;
    public String CEK_NAMA, CEK_DESKRIPSI, CEK_MULAI, CEK_BERAKHIR=null;
    public boolean duplikasi=false;
    
    public acara(){
        try {
            Driver dbdriver = new com.mysql.jdbc.Driver();
            DriverManager.registerDriver(dbdriver);
            Koneksidb=DriverManager.getConnection(urlKoneksi,username,password);
            System.out.print("Database Terkoneksi");
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,e.toString());
        }
    }
    
    public void simpanacara01(String id, String nm, String desk, String mulai, String berakhir){
        try {
            String sqlsimpan="insert into acara(idacara, nama, deskripsi, mulai, berakhir) VALUES"
                    + " ('"+id+"', '"+nm+"', '"+desk+"', '"+mulai+"', '"+berakhir+"')";
            String sqlcari="select*from acara where idacara='"+id+"'";
            Statement cari=Koneksidb.createStatement();
            ResultSet data=cari.executeQuery(sqlcari);
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID Acara sudah terdaftar ");
            } else {
                Statement perintah=Koneksidb.createStatement();
                perintah.executeUpdate(sqlsimpan);
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
     public void simpanacara02(String id, String nm, String desk, String mulai, String berakhir){
        try {
            String sqlsimpan="insert into acara(idacara, nama, deskripsi, mulai, berakhir)"
                    + " value (?, ?, ?, ?, ?)";
            String sqlcari= "select*from acara where idacara=?";
            PreparedStatement cari=Koneksidb.prepareStatement(sqlcari);
            cari.setString(1, id);
            ResultSet data = cari.executeQuery();
            if (data.next()){
                JOptionPane.showMessageDialog(null, "ID sudah terdaftar");
                this.duplikasi=true;
                this.CEK_NAMA=data.getString("nama");           
                this.CEK_DESKRIPSI=data.getString("deskripsi");           
                this.CEK_MULAI=data.getString("mulai");           
                this.CEK_BERAKHIR=data.getString("berakhir");           
            } else {
                this.duplikasi=false;
                this.CEK_NAMA=null;
                this.CEK_DESKRIPSI=null;
                this.CEK_MULAI=null;
                this.CEK_BERAKHIR=null;
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlsimpan);
                perintah.setString(1, id );
                perintah.setString(2, nm);
                perintah.setString(3, desk);
                perintah.setString(4, mulai);
                perintah.setString(5, berakhir);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil disimpan");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
     }
     
     public void ubahAcara(String id, String nm, String desk, String mulai, String berakhir){
        try {
            String sqlubah="UPDATE acara SET nama=?, deskripsi=?, mulai=?, berakhir=? WHERE idacara=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlubah);
                perintah.setString(1, nm);
                perintah.setString(2, desk);
                perintah.setString(3, mulai);
                perintah.setString(4, berakhir);
                perintah.setString(5, id);
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil diubah");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        }
     
      public void hapusAcara(String id){
        try {
            String sqlhapus="delete from acara where idacara=?";        
                PreparedStatement perintah=Koneksidb.prepareStatement(sqlhapus);
                perintah.setString(1, id );
                perintah.executeUpdate();
            JOptionPane.showMessageDialog(null, "Data berhasil dihapus");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    public void tampilDataAcara(JTable komponentabel, String SQL){
        try {
            PreparedStatement perintah = Koneksidb.prepareStatement(SQL);
            ResultSet data = perintah.executeQuery();
            ResultSetMetaData meta = data.getMetaData();
            int jumlahkolom = meta.getColumnCount();
            DefaultTableModel modeltabel = new DefaultTableModel();
            modeltabel.addColumn("ID Acara");
            modeltabel.addColumn("Nama Acara");
            modeltabel.addColumn("Deskripsi");
            modeltabel.addColumn("Mulai");
            modeltabel.addColumn("Berakhir");
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



