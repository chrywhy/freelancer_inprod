package com.inprod.service;

import com.inprod.log.AppLog;
import com.inprod.util.InprodConf;
import org.json.JSONObject;

/**
 *
 * @author AlbertWang
 */
public class FileHandleRequest {
    private final String _synFilename;
    private final String _rspFilename;
    private final int _timeSpan;
    private final int _goodThreshold;
    private final int _mediumThreshold;

    public String getSynFilename() {
        return _synFilename;
    }
    
    public String getRspFilename() {
        return _rspFilename;
    }
        
    public int getTimeSpan() {
        return _timeSpan;
    }
    
    public int getThresholdGood() {
        return _goodThreshold;
    }
    
    public int getThresholdMedium() {
        return _mediumThreshold;
    }
    
    public FileHandleRequest(final String synFilename,
                               final String rspFilename,
                               int timeSpan,
                               int goodThreshold,
                               int mediumThreshold) {
        _synFilename = synFilename;
        _rspFilename = rspFilename;
        _timeSpan = timeSpan;
        _goodThreshold = goodThreshold;
        _mediumThreshold = mediumThreshold;
    }
    
    public FileHandleRequest() {
        _synFilename = InprodConf.getSynFileName();
        _rspFilename = InprodConf.getRspFileName();
        _timeSpan = InprodConf.getTimeSpan();
        _goodThreshold = InprodConf.getGoodThreshold();
        _mediumThreshold = InprodConf.getMediumThreshold();
    }
    
    public FileHandleRequest(String jsonFormatParam) {
        JSONObject j = new JSONObject(jsonFormatParam);
        String key = "file.syn";
        String strVal;
        int intVal;        
        try {
            strVal = j.getString(key);
        } catch (Exception e) {
            strVal = InprodConf.getSynFileName();
            AppLog.warn("No " + key + " parameter, use default in configuration");
        }
        _synFilename = strVal;
               
        key = "file.rsp";
        String rspFileName;
        try {
            strVal = j.getString(key);
        } catch (Exception e) {
            strVal = InprodConf.getRspFileName();
            AppLog.warn("No " + key + " parameter, use default in configuration");
        }
        _rspFilename = strVal;
        
        key = "time.span";
        try {
            intVal = j.getInt(key);
        } catch (Exception e) {
            intVal = InprodConf.getTimeSpan();
            AppLog.warn("No " + key + " parameter, use default in configuration");
        }
        _timeSpan = intVal;

        key = "threshold.good";
        try {
            intVal = j.getInt(key);
        } catch (Exception e) {
            intVal = InprodConf.getGoodThreshold();
            AppLog.warn("No " + key + " parameter, use default in configuration");
        }
        _goodThreshold = intVal;

        key = "threshold.medium";
        try {
            intVal = j.getInt(key);
        } catch (Exception e) {
            intVal = InprodConf.getMediumThreshold();
            AppLog.warn("No " + key + " parameter, use default in configuration");
        }
        _mediumThreshold = intVal;
    }
    
    public String toString() {
        return "synFilename = " + _synFilename
             + "\n" + "rspFilename = " + _rspFilename
             + "\n" + "timeSpan = " + _timeSpan
             + "\n" + "goodThreshold = " + _goodThreshold
             + "\n" + "mediumThreshold = " + _mediumThreshold;
    }
}
