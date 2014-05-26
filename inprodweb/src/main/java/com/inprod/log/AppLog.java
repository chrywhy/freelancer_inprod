package com.inprod.log;

import com.inprod.exception.AppException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Albert Wang
 */
public class AppLog {
    private static Logger logger = LogManager.getRootLogger();
    public static void info(String msg) {
        System.out.println("[INFO] " + msg);
    }
    
    public static void warn(String msg) {
        System.out.println("[WARN] " + msg);
    }
    
    public static void debug(String msg) {
        System.out.println("debug : " + msg);
    }
    
    public static void exception(String msg, Throwable e) {
        AppException appException = new AppException(msg, e);
        appException.printStackTrace(System.err);
        throw new AppException(msg, e);
    }

    public static void error(String msg) {
        System.err.println("[ERROR] " + msg);
    }
    
    public static void error(String msg, Throwable e) {
        AppException appException = new AppException(msg, e);
        appException.printStackTrace(System.err);
    }
}
