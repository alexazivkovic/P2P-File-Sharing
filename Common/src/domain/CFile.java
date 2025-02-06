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
public class CFile implements Serializable{
    private Long id;
    private String name;
    private String path;
    private String peer;

    public CFile(String name, String path, String peer) {
        this.name = name;
        this.path = path;
        this.peer = peer;
    }
    
    public CFile(String path, String peer) {
        this.path = path;
        this.peer = peer;
    }
    
    public CFile() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getPeer() {
        return peer;
    }

    public void setPeer(String peer) {
        this.peer = peer;
    }

    @Override
    public String toString() {
        return id + ". " + name + ", " + peer;
    }
}
