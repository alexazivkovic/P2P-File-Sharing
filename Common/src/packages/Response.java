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
public class Response implements Serializable{
    private ResponseCode rc;
    private Object obj;

    public Response() {
    }
    
    public Response(ResponseCode rc) {
        this.rc = rc;
    }

    public Response(ResponseCode rc, Object obj) {
        this.rc = rc;
        this.obj = obj;
    }

    public ResponseCode getRc() {
        return rc;
    }

    public void setRc(ResponseCode rc) {
        this.rc = rc;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
    
    
}
