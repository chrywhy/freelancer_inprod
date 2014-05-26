package com.inprod.util;

import com.inprod.log.AppLog;
import java.io.File;

import java.io.IOException;
import java.net.URL;

public class InprodConf {
    private static String APP_CONF;
    private static PropConf _appConf = null;

    private static PropConf _getPropConf() throws IOException {
        String webInfPath= getWebInfPath();
        APP_CONF = webInfPath + File.separator + "app.conf";
        if (_appConf == null) {
            _appConf = new PropConf(APP_CONF);
        }
        return _appConf;
    }

    public static String getWebInfPath() {
        String path = InprodConf.class.getResource("/").getPath();
        path = path.substring(1, path.indexOf("classes"));
        AppLog.debug("path=" + path);
        return path;
    }
    
    public static String getDbHost() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getStringValue("db.host", "127.0.0.1");
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return "";
    }

    public static int getDbPort() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getIntValue("db.port", 3306);
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return 3306;
    }

    public static String getDbUser() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getStringValue("db.user", "root");
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return "root";
    }

    public static String getDbPassword() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getStringValue("db.password", "");
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return "";
    }

    public static String getDbName() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getStringValue("db.name", "inprod");
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return "inprod";
    }

    public static String getSynFileName() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getStringValue("file.syn", "SYNKame.net");
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return "SYNKame.net";
    }

    public static String getRspFileName() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getStringValue("file.rsp", "RSPKame.net");
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return "RSPKame.net";
    }

    public static int getTimeSpan() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getIntValue("time.span", 5);
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return 5;
    }

    public static int fileLoadInterval() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getIntValue("file.load.interval", 5);
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return 5;
    }
    
    public static int getGoodThreshold() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getIntValue("threshold.good", 100000);
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return 100000;
    }

    public static int getMediumThreshold() {
        try {
            PropConf propConf = _getPropConf();
            return propConf.getIntValue("threshold.medium", 200000);
        }
        catch (Throwable e) {
            AppLog.error("", e);
        }
        return 200000;
    }
}
