/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import communication.Communicator;
import domain.CFile;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import packages.Operation;
import packages.Request;
import packages.Response;

/**
 *
 * @author a_ziv
 */
public class ComboFiller extends Thread{
    private JComboBox box;
    
    public ComboFiller(JComboBox box){
        this.box = box;
    }

    @Override
    public void run() {
        
            try {
                Response res = Communicator.getInstance().send(new Socket("localhost", 9009), new Request(Operation.GetAllFiles, null));
                box.removeAllItems();
                for(CFile file : (List<CFile>) res.getObj()){
                    box.addItem(file);
                }
            } catch (Exception e) {
                Logger.getLogger(ComboFiller.class.getName()).log(Level.SEVERE, null, e);
            }
        
    }
}
