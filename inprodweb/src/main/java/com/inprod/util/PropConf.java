/*
*****************************************************************************************************************
* Module Introduction:
* Common module to read property file configurations
*
* Wang Huiyu   2012/07/08   Initial Version         
* Wang Huiyu   2012/08/01   Make auto reload configurable, 
*                           and add set methods, it only take effective when auto reload is disabled         
*****************************************************************************************************************
*/
package com.inprod.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * read web application configuration.
 */
public class PropConf {
    public static final long EXPIRE_TIME_MILLIS = 15 * 1000 * 60;  //15 minutes
    private final String _file;
    private final Properties _prop;
    private long _expireTime;
    private long _lastReloadEpoch;
    private boolean _autoReload;

    public PropConf(String file) throws IOException {
        this(file, EXPIRE_TIME_MILLIS);
    }

    public PropConf(String file, long expireTime) throws IOException {
        _prop = new Properties();
        _lastReloadEpoch = 0;
        _file = file;
        _expireTime = expireTime;
        _autoReload = true;
        reload();
    }

    public PropConf(Properties props) {
        _prop = props;
        _lastReloadEpoch = 0;
        _file = null;
        _expireTime = 0;
        _autoReload = false;
    }

    public boolean isAutoReload() {
        return _autoReload;
    }

    public void enableAutoReload() {
        _autoReload = true;
    }

    public void disableAutoReload() {
        _autoReload = false;
    }

    public void reload() throws IOException {
        if (_file != null) {
            FileInputStream fis = new FileInputStream(new File(_file));
            _prop.clear();
            _prop.load(fis);
            _initConfControl();
            _lastReloadEpoch = System.currentTimeMillis();
        }
    }

    protected void _initConfControl() {
    }

    public boolean confExpired() {
        if (isAutoReload()) {
            return System.currentTimeMillis() - _lastReloadEpoch > _expireTime;
        }
        else {
            return false;
        }
    }

    public String getStringValue(String key) throws IOException {
        if (confExpired()) {
            reload();
        }
        String val = _prop.getProperty(key);
        return val == null ? null : val.trim();
    }

    public String getStringValue(String key, String defVal) {
        String val;
        try {
            val = getStringValue(key);
            if (val == null) {
                val = defVal;
            }
        }
        catch (Throwable e) {
            val = defVal;
        }
        return val;
    }

    public Integer getIntValue(String key) throws NumberFormatException, IOException {
        return Integer.valueOf(getStringValue(key));
    }

    public Integer getIntValue(String key, int defVal) {
        Integer val;
        try {
            val = getIntValue(key);
            if (val == null) {
                val = defVal;
            }
        }
        catch (Throwable e) {
            val = defVal;
        }
        return val;
    }

    public Long getLongValue(String key) throws NumberFormatException, IOException {
        return Long.valueOf(getStringValue(key));
    }

    public Long getLongValue(String key, long defVal) {
        Long val;
        try {
            val = getLongValue(key);
            if (val == null) {
                val = defVal;
            }
        }
        catch (Throwable e) {
            val = defVal;
        }
        return val;
    }

    public boolean getBoolValue(String key) throws IOException {
        String val = getStringValue(key);
        if (val == null) {
            throw new IOException("Invalid boolean value");
        }
        val = val.trim();
        if (val.equalsIgnoreCase("true")) {
            return true;
        }
        else if (val.equalsIgnoreCase("false")) {
            return false;
        }
        else {
            throw new IOException("Invalid boolean value");
        }
    }

    public boolean getBoolValue(String key, boolean defVal) {
        boolean val;
        try {
            val = getBoolValue(key);
        }
        catch (Throwable e) {
            val = defVal;
        }
        return val;
    }

    public Properties getProperties() {
        return _prop;
    }
}
