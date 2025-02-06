/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import communication.Communicator;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import packages.Operation;
import packages.Request;
import packages.Response;

/**
 *
 * @author a_ziv
 */
public class PeerHandler extends Thread{
    private Socket socket = null;
    
    public PeerHandler(Socket socket){
        this.socket=socket;
    }

    @Override
    public void run() {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                System.out.println(socket.toString());
                String line = reader.readLine();
                System.out.println(line);
                long id = Long.parseLong(line);
                System.out.println(id);
                Response res = Communicator.getInstance().send(new Socket("localhost", 9009), new Request(Operation.GetPath, id));
                String filePath = (String)res.getObj();
                System.out.println(filePath);
                System.out.println("File Path: " + filePath);  // Check if the file path is correct
                File file = new File(filePath);
                if (!file.exists()) {
                    System.out.println("File not found: " + filePath);
                    return;
                }
                BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(file));
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(socket.getOutputStream());

                byte[] buffer = new byte[4096];  // Buffer to send data in chunks
                int bytesRead;

                // Read the file and send it to the server
                while ((bytesRead = bufferedInputStream.read(buffer)) != -1) {
                    bufferedOutputStream.write(buffer, 0, bytesRead);
                }

                bufferedOutputStream.flush();  // Ensure all data is sent
                bufferedOutputStream.close();
                System.out.println("File sent successfully.");
            } catch (Exception ex) {
                System.out.println(ex.toString());
            }
        }
}
