/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inprod;
import com.inprod.service.FileHandleRequest;
import com.inprod.sql.DbUtil;

/**
 *
 * @author Albert Wang
 */
public class App {
    public static void main(String args[]) {
        String synFileName = "SYNKame.net";
        String rspFileName = "RSPKame.net";
        String host = "192.168.1.108";
        int port = 3306;
        String username = "root";
        String password = "";        
        DbUtil.initConnectionPool(host, port, username, password);

        FileHandleRequest request = new FileHandleRequest(synFileName, rspFileName, 5, 100000, 200000);
        FileUploader inProd = new FileUploader(request);
        inProd.generateTables();
    }
}
