/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package packages;

import java.io.Serializable;

/**
 *
 * @author a_ziv
 */
public class Request implements Serializable{
    private Operation op;
    private Object obj;

    public Request(Operation op, Object obj) {
        this.op = op;
        this.obj = obj;
    }

    public Operation getOp() {
        return op;
    }

    public void setOp(Operation op) {
        this.op = op;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
    
    
    
}
