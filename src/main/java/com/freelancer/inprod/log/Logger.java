package com.freelancer.inprod.log;

import com.freelancer.inprod.exception.AppException;

/**
 *
 * @author Albert Wang
 */
public class Logger {
    public static void info(String msg) {
        System.out.println(msg);
    }
    
    public static void exception(String msg, Exception e) {
        AppException appException = new AppException(msg, e);
        appException.printStackTrace(System.err);
        throw new AppException(msg, e);
    }

    public static void error(String msg) {
        System.err.println(msg);
    }
    
    public static void error(String msg, Exception e) {
        AppException appException = new AppException(msg, e);
        appException.printStackTrace(System.err);
    }
}
