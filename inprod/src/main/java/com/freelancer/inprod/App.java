/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelancer.inprod;
import com.freelancer.inprod.sql.DbUtil;

/**
 *
 * @author Albert Wang
 */
public class App {
    public static void main(String args[]) {
        String synFileName = args[0];
        String rspFileName = args[1];
        String host = args[2];
        int port = Integer.parseInt(args[3]);
        String username = args[4];
        String password = args.length >= 6 ? args[5] : "";        
        DbUtil.initConnectionPool(host, port, username, password);
        
        InProd inProd = new InProd();
        inProd.generateTables(synFileName, rspFileName);
    }
}
