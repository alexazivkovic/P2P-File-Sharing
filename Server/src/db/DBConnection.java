/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Singleton.java to edit this template
 */
package db;
import domain.CFile;
import domain.Peer;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a_ziv
 */
public class DBConnection {
    private static DBConnection instance;
    private Connection con;
    
    private DBConnection(){
        try {
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/rmt", "root", "root");
        } catch (SQLException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static DBConnection getInstance(){
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }
    
    public boolean login(Peer p){
        String uname = p.getUsername();
        String pass = p.getPassword();
        String query = "SELECT * FROM peers WHERE username=? AND password=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, uname);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (Exception ex) {
            System.out.println("Greska: "+ex.toString());
        }
        return false;
    }
    
    public boolean register(Peer p){
        String uname = p.getUsername();
        String pass = p.getPassword();
        String ip = p.getIp();
        String query = "INSERT INTO peers VALUES (?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, uname);
            ps.setString(2, pass);
            ps.setString(3, ip);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true; // Insert was successful
            } else {
                return false; // No rows were affected (unlikely for an INSERT)
            }
        } catch (Exception ex) {
            System.out.println("Greska: "+ex.toString());
            return false;
        }
    }
    
    public boolean updateIPPort(Peer p){
        String uname = p.getUsername();
        String ip = p.getIp();
        int port = p.getPort();
        System.out.println(uname+" "+ip+" "+port);
        String query = "UPDATE peers SET ip=?, port=? WHERE username=?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, ip);
            ps.setInt(2, port);
            ps.setString(3, uname);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Greska: "+ex.toString());
            return false;
        }
    }
    
    public boolean share(CFile f){
        String name = f.getName();
        String path = f.getPath();
        String peer = f.getPeer();
        String query = "INSERT INTO files (name, path, peer) VALUES (?,?,?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, path);
            ps.setString(3, peer);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true; // Insert was successful
            } else {
                return false; // No rows were affected (unlikely for an INSERT)
            }
        } catch (Exception ex) {
            System.out.println("Greska: "+ex.toString());
            return false;
        }
    }
    
    public List<CFile> getSharedFiles(Peer p){
        List<CFile> files = new ArrayList<>();
        String uname = p.getUsername();
        String query = "SELECT f.name, f.path FROM files f JOIN peers p ON f.peer=p.username WHERE p.username=?";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, uname);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                CFile file = new CFile();
                file.setName(rs.getString("name"));
                file.setPath(rs.getString("path"));
                files.add(file);
            }
        } catch (Exception ex) {
            System.out.println("Greska: dbcon getshfiles");
        }
        return files;
    }
    
    public boolean remove(CFile f){
        String path = f.getPath();
        String peer = f.getPeer();
        String query = "DELETE FROM files WHERE path=? AND peer=?";
        try{
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, path);
            ps.setString(2, peer);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            System.out.println("Greska: handler remove files");
        }
        return false;
    }
    
    public List<CFile> getAllFiles(Object o){
        String query = "SELECT * FROM files";
        List<CFile> files = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                CFile file = new CFile();
                file.setId(rs.getLong("id"));
                file.setName(rs.getString("name"));
                file.setPeer(rs.getString("peer"));
                files.add(file);
            }
        } catch (Exception ex) {
            System.out.println("Error: dbcon getAllFiles");
            ex.printStackTrace();
        }
        return files;
    }
    
    public Peer getFile(CFile file){
        String query = "SELECT p.username, p.ip, p.port FROM peers p JOIN files f ON f.peer=p.username WHERE f.peer=?";
        Peer p = new Peer();
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, file.getPeer());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                p.setUsername(rs.getString("username"));
                p.setIp(rs.getString("ip"));
                p.setPort(rs.getInt("port"));
            }
        } catch (Exception ex) {
            System.out.println("Error: dbcon getAllFiles");
            ex.printStackTrace();
        }
        return p;
    }
    
    public String getPath(Long id){
        String query = "SELECT path FROM files WHERE id=?";
        String path = "";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                path = rs.getString("path");
            }
        } catch (Exception ex) {
            System.out.println("Error: dbcon getAllFiles");
            ex.printStackTrace();
        }
        return path;
    }
}
