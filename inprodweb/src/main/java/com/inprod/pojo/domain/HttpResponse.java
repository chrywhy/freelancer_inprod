/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inprod.pojo.domain;

import java.sql.Date;

/**
 *
 * @author Albert Wang
 */
public class HttpResponse {
    private int _id;    
    
    private int _synPacaket;    
    private Date _synDate;
    private int _synHour;
    private int _synMinute;
    private int _synSecond;
    private int _synMillSec;
    private int _synMicroSec;
    private int _synNanoSec;
    
    private int _rspPacaket;    
    private String _rspDate;
    private int _rspHour;
    private int _rspMinute;
    private int _rspSecond;
    private int _rspMillSec;
    private int _rspMicroSec;
    private int _rspNanoSec;

    public int getSynPacket() {
        return _synPacaket;
    }
    
    public void setSynPacket(int synPacket) {
        _synPacaket = synPacket;
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

    public int getSynMinute() {
        return _synMinute;
    }
    
    public void setSynMinute(int synMinute) {
        _synMinute = synMinute;
    }

    public int getSynSecond() {
        return _synSecond;
    }
    
    public void setSynSecond(int synCecond) {
        _synSecond = synCecond;
    }

    public int getSynMillSec() {
        return _synMillSec;
    }
    
    public void setSynMillSec(int synMillSec) {
        _synMillSec = synMillSec;
    }

    public int getSynMicroSec() {
        return _synMicroSec;
    }
    
    public void setSynMicroSec(int synMicroSec) {
        _synMicroSec = synMicroSec;
    }

    public int getSynNanoSec() {
        return _synNanoSec;
    }
    
    public void setSynNanoSec(int synNanoSec) {
        _synNanoSec = synNanoSec;
    }

    public int getRspPacket() {
        return _rspPacaket;
    }
    
    public void setRspPacket(int rspPacket) {
        _rspPacaket = rspPacket;
    }

    public String getRspDate() {
        return _rspDate;
    }
    
    public void setRspDate(String rspDate) {
        _rspDate = rspDate;
    }

    public int getRspHour() {
        return _rspHour;
    }
    
    public void setRspHour(int rspHour) {
        _rspHour = rspHour;
    }

    public int getRspMinute() {
        return _rspMinute;
    }
    
    public void setRspMinute(int rspMinute) {
        _rspMinute = rspMinute;
    }

    public int getRspSecond() {
        return _rspSecond;
    }
    
    public void setRspSecond(int rspCecond) {
        _rspSecond = rspCecond;
    }

    public int getRspMillSec() {
        return _rspMillSec;
    }
    
    public void setRspMillSec(int rspMillSec) {
        _rspMillSec = rspMillSec;
    }

    public int getRspMicroSec() {
        return _rspMicroSec;
    }
    
    public void setRspMicroSec(int rspMicroSec) {
        _rspMicroSec = rspMicroSec;
    }

    public int getRspNanoSec() {
        return _rspNanoSec;
    }
    
    public void setRspNanoSec(int rspNanoSec) {
        _rspNanoSec = rspNanoSec;
    }
}
