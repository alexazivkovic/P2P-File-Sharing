/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import db.DBConnection;
import domain.CFile;
import domain.Peer;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import packages.Operation;
import packages.Request;
import packages.Response;
import packages.ResponseCode;

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
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                Request req = (Request) in.readObject();
                Response res = new Response();
                switch(req.getOp()){
                    case Operation.Login:
                        if(DBConnection.getInstance().login((Peer)req.getObj())){
                            System.out.println("Ulogovan");
                            res.setRc(ResponseCode.OK);
                        }
                        else{
                            System.out.println("Greska: logovanje, handler");
                            res.setRc(ResponseCode.ERROR);
                        }
                        break;
                        
                    case Operation.Register:
                        if(DBConnection.getInstance().register((Peer)req.getObj())){
                            System.out.println("Registrovan");
                            res.setRc(ResponseCode.OK);
                        }
                        else{
                            System.out.println("Gresk: registrovanje, handlera");
                            res.setRc(ResponseCode.ERROR);
                        }
                        break;
                        
                    case Operation.UpdateIPPort:
                        if(DBConnection.getInstance().updateIPPort((Peer)req.getObj())){
                            System.out.println("Updejtovano");
                            res.setRc(ResponseCode.OK);
                        }
                        else{
                            System.out.println("Greska: updejtovanje ip&port, handler");
                            res.setRc(ResponseCode.ERROR);
                        }
                        break;
                        
                    case Operation.ShareFile:
                        if(DBConnection.getInstance().share((CFile)req.getObj())){
                            System.out.println("Serovano");
                            res.setRc(ResponseCode.OK);
                        }
                        else{
                            System.out.println("Greska: serovanje fajla, hendler");
                            res.setRc(ResponseCode.ERROR);
                        }
                        break;
                        
                    case Operation.RemoveFile:
                        if(DBConnection.getInstance().remove((CFile)req.getObj())){
                            System.out.println("Uklonjeno");
                            res.setRc(ResponseCode.OK);
                        }
                        else{
                            System.out.println("Greska: uklanjanje fajla, hendler");
                            res.setRc(ResponseCode.ERROR);
                        }
                        break;
                        
                    case Operation.GetSharedFiles:
                        try{
                            List<CFile> files = DBConnection.getInstance().getSharedFiles((Peer)req.getObj());
                            if(files!=null){
                                System.out.println("Popunjena tabela, handler");
                                res.setRc(ResponseCode.OK);
                                res.setObj(files);
                            }
                            else{
                                System.out.println("Greska: files je null, hanlder");
                                res.setRc(ResponseCode.ERROR);
                            }
                        } catch(Exception e){
                            System.out.println("Greska sa bazom" + e.getMessage());
                        }
                        break;
                        
                    case Operation.GetAllFiles:
                        try{
                            List<CFile> files = DBConnection.getInstance().getAllFiles(null);
                            if(files!=null){
                                System.out.println("Dostavljena lista, handler");
                                res.setRc(ResponseCode.OK);
                                res.setObj(files);
                            }
                            else{
                                System.out.println("Greska: files je null, hanlder");
                                res.setRc(ResponseCode.ERROR);
                            }
                        } catch(Exception e){
                            System.out.println("Greska sa bazom" + e.getMessage());
                        }
                        break;
                        
                    case Operation.GetFile:
                        try{
                            Peer p = DBConnection.getInstance().getFile((CFile)req.getObj());
                            if(p!=null){
                                System.out.println("Dostavljena adresa, handler");
                                res.setRc(ResponseCode.OK);
                                res.setObj(p);
                            }
                            else{
                                System.out.println("Greska: files je null, hanlder");
                                res.setRc(ResponseCode.ERROR);
                            }
                        } catch(Exception e){
                            System.out.println("Greska sa bazom" + e.getMessage());
                        }
                        break;
                        
                    case Operation.GetPath:
                        try{
                            String path = DBConnection.getInstance().getPath((Long)req.getObj());
                            if(path!=null){
                                System.out.println("Dostavljen path, handler");
                                res.setRc(ResponseCode.OK);
                                res.setObj(path);
                            }
                            else{
                                System.out.println("Greska: path je null, hanlder");
                                res.setRc(ResponseCode.ERROR);
                            }
                        } catch(Exception e){
                            System.out.println("Greska sa bazom" + e.getMessage());
                        }
                        break;
                }
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                out.writeObject(res);
            } catch (IOException ex) {
                Logger.getLogger(PeerHandler.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(PeerHandler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
}
