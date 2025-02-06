/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author a_ziv
 */
public class Peer implements Serializable{
    private String username;
    private String password;
    private String ip;
    private int port;

    public Peer(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Peer(String username, String password, String ip) {
        this.username = username;
        this.password = password;
        this.ip = ip;
    }

    public Peer() {
    }
    
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    
    
}
