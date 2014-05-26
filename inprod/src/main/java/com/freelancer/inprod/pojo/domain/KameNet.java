/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelancer.inprod.pojo.domain;

import java.sql.Date;
import java.util.Calendar;

/**
 *
 * @author Albert Wang
 */
public class KameNet {
    private final int _id;
    private int _packet = 0;    
    private String _commonName = "";    
    // timestamp
    private int _month = 0;
    private int _day = 0;
    private int _year = 0;
    private int _hour = 0;
    private int _minute = 0;
    private int _second = 0;
    private int _millSec = 0;
    private int _microSec = 0;
    private int _nanoSec = 0;
    // Loaded / Checked with Configuration
    private String _sourceIpAddress = "";
    private String _destinationIpAddress = "";
    private int _sourcePort = 0;
    private int _destinationPort = 0;
    
    private int _machedRspCount = 0;    //used in SynKame.net to record the mached count with RSPKame.net

    public KameNet () {
        _id = 0;
    }
    
    public KameNet (int id) {
        _id = id;
    }

    public void increaseMachedRspCount() {
        _machedRspCount++;
    }
    
    public void negativeMachedRspCount() {
        _machedRspCount = 0 - _machedRspCount;
    }

    public int getMachedRspCount() {
        return _machedRspCount;
    }

    public int getId() {
        return _id;
    }
    
    public int getPacket() {
        return _packet;
    }
    
    public void setPacket(int packet) {
        _packet = packet;
    }

    public String getCommonName() {
        return _commonName;
    }
    
    public String setCommonName(String commonName) {
        return _commonName = commonName;
    }
    
    public int getMonth() {
        return _month;
    }
    
    public void setMonth(int month) {
        _month = month;
    }

    public int getDay() {
        return _day;
    }
    
    public void setDay(int day) {
        _day = day;
    }

    public int getYear() {
        return _year;
    }
    
    public void setYear(int year) {
        _year = year;
    }

    public Date getDate() {
        Calendar c = Calendar.getInstance();
        c.set(_year, _month, _day, _hour, _minute, _second); 
        c.set(Calendar.MILLISECOND, _millSec);
        return new Date(c.getTimeInMillis());
    }
    
    public int getHour() {
        return _hour;
    }
    
    public void setHour(int hour) {
        _hour = hour;
    }

    public int getMinute() {
        return _minute;
    }
    
    public void setMinute(int minute) {
        _minute = minute;
    }

    public int getSecond() {
        return _second;
    }
    
    public void setSecond(int second) {
        _second = second;
    }

    public int getMillSec() {
        return _millSec;
    }
    
    public void setMillSec(int millSec) {
        _millSec = millSec;
    }

    public int getMicroSec() {
        return _microSec;
    }
    
    public void setMicroSec(int microSec) {
        _microSec = microSec;
    }

    public int getNanoSec() {
        return _nanoSec;
    }
    
    public void setNanoSec(int nanoSec) {
        _nanoSec = nanoSec;
    }

    public String getSourceIpAddress() {
        return _sourceIpAddress;
    }
    
    public void setSourceIpAddress(String sourceIpAddress) {
        _sourceIpAddress = sourceIpAddress;
    }

    public String getDestinationIpAddress() {
        return _destinationIpAddress;
    }
    
    public void setDestinationIpAddress(String destinationIpAddress) {
        _destinationIpAddress = destinationIpAddress;
    }

    public int getSourcePort() {
        return _sourcePort;
    }
    
    public void setSourcePort(int sourcePort) {
        _sourcePort = sourcePort;
    }

    public int getDestinationPort() {
        return _destinationPort;
    }
    
    public void setDestinationPort(int destinationPort) {
        _destinationPort = destinationPort;
    }
    
    public int getIPv6() {
        if (_sourceIpAddress.indexOf(":") > 0) {
            return 1;
        }
        return 0;
    }
    
    public String toString() {                      //debug purpose
        return  "id=" + _id + "\n" +
                "pacaket=" + _packet + "\n" +    
                "month=" + _month  + "\n" +
                "day=" + _day  + "\n" +
                "year=" + _year  + "\n" +
                "hour=" + _hour  + "\n" +
                "minute=" + _minute  + "\n" +
                "second=" + _second  + "\n" +
                "millSec=" + _millSec  + "\n" +
                "microSec=" + _microSec  + "\n" +
                "nanoSec=" + _nanoSec  + "\n" +
                "sourceIpAddress=" + _sourceIpAddress  + "\n" +
                "destinationIpAddress=" + _destinationIpAddress  + "\n" +
                "sourcePort=" + _sourcePort  + "\n" +
                "destinationPort=" + _destinationPort;
    }
}
