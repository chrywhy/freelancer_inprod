/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inprod.pojo.domain;

import java.sql.Date;

/**
 *
 * @author chry
 */
public class Graph {
    private String _commonName;
    private Date _synDate;
    private int _synHour;
    private int _synNMinutes;
    private int _numNMinutes;
    private int _good;
    private int _medium;
    private int _bad;
    private int _ipv6;
    
    public String getCommonName() {
        return _commonName;
    }
    
    public void setCommonName(String commonName) {
        _commonName = commonName;
    }

    public Date getSynDate() {
        return _synDate;
    }
    
    public void setSynDate(Date synDate) {
        _synDate = synDate;
    }

    public int getSynHour() {
        return _synHour;
    }
    
    public void setSynHour(int synHour) {
        _synHour = synHour;
    }

    public int getSynNMinute() {
        return _synNMinutes;
    }
    
    public void setSynMinute(int synMinutes) {
        _synNMinutes = synMinutes;
    }

    public int getNumNMinute() {
        return _numNMinutes;
    }
    
    public void setNumNMinute(int nunNMinutes) {
        _numNMinutes = nunNMinutes;
    }
    
    public int getIPv6() {
        return _ipv6;
    }
    
    public void setIPv6(int ipv6) {
        _ipv6 = ipv6;
    }

    public int getGood() {
        return _good;
    }
    
    public void setGood(int good) {
        _good = good;
    }

    public int getMedium() {
        return _medium;
    }
    
    public void setMedium(int medium) {
        _medium = medium;
    }

    public int getBad() {
        return _bad;
    }
    
    public void setBad(int bad) {
        _bad = bad;
    }
}
