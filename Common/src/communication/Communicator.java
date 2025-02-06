/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import packages.Request;
import packages.Response;

/**
 *
 * @author a_ziv
 */
public class Communicator {
    private static Communicator instance;
    
    private Communicator(){}
    
    public static Communicator getInstance() throws IOException{
        if(instance==null){
            instance = new Communicator();
        }
        return instance;
    }
    
    public Response send(Socket socket, Request req) throws IOException, ClassNotFoundException{
        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        out.writeObject(req);
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        Response res = (Response)in.readObject();
        return res;
    }
}
