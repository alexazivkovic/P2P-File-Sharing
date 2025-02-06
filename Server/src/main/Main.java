/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import forms.MainForm;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import util.PeerHandler;

/**
 *
 * @author a_ziv
 */
public class Main {
    public static void main(String[] args) throws IOException {
        new MainForm().setVisible(true);
    }
}
