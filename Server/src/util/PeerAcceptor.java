/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author a_ziv
 */
public class PeerAcceptor extends Thread{
    private ServerSocket sc;

    public PeerAcceptor(ServerSocket sc) {
        this.sc = sc;
    }
    
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) { // Check for interrupt signal
            try {
                // Accept client connections
                Socket socket = sc.accept();
                // Handle each connection in a new thread
                new PeerHandler(socket).start();
            } catch (IOException ex) {
                // If the server socket is closed or an interrupt happens, break out of the loop
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("PeerAcceptor thread interrupted. Stopping...");
                    break;
                }
                // Log the error
                Logger.getLogger(PeerAcceptor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        // Close the ServerSocket gracefully when exiting the loop
        try {
            if (!sc.isClosed()) {
                sc.close();
            }
        } catch (IOException ex) {
            Logger.getLogger(PeerAcceptor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
