/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inprod;

import com.inprod.log.AppLog;
import com.inprod.util.InprodConf;
import java.net.URL;

/**
 *
 * @author chry
 */
public class Test {
    public static void main(String[] args) {
        URL url = InprodConf.class.getResource("/");
        AppLog.info("" + url);
    }
}
