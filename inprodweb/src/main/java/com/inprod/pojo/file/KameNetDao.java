/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inprod.pojo.file;

import com.inprod.exception.AppException;
import com.inprod.log.AppLog;
import com.inprod.pojo.domain.KameNet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author chry
 */
public class KameNetDao {
    public enum Field {
        packet(1), month(2), day(3), year(4), hour(5), minute(6), second(7), millSec(8), 
        MicroSec(9), nanoSec(10), sourceIpAddress(11), destinationIpAddress(12),
        sourcePort(13), destinationPort(14);
        
        public static final String regex = "^([0-9]+)\\s+(\\D+)\\s+(\\d+),\\s+(\\d+)\\s+(\\d+):(\\d+):(\\d+)\\.(\\d{3})(\\d{3})(\\d{3})\\s+(\\S+)\\s+(\\S+)\\s+(\\d+)\\s+(\\d+)\\s*$";
        public static final Pattern pattern = Pattern.compile(regex);        
        public final int group;
        private Field(int group) {
            this.group = group;
        }
        
        public void setKameNet(KameNet kameNet, String strVal) {
            switch (this) {
                case packet: 
                    kameNet.setPacket(Integer.parseInt(strVal));
                    break;
                case month:
                    int val = 0;
                    if("Feb".equalsIgnoreCase(strVal)) {
                        val = 1;
                    } else if ("Mar".equalsIgnoreCase(strVal)) {
                        val = 2;
                    } else if ("Apr".equalsIgnoreCase(strVal)) {
                        val = 3;
                    } else if ("May".equalsIgnoreCase(strVal)) {
                        val = 4;
                    } else if ("Jun".equalsIgnoreCase(strVal)) {
                        val = 5;
                    } else if ("Jul".equalsIgnoreCase(strVal)) {
                        val = 6;
                    } else if ("Aug".equalsIgnoreCase(strVal)) {
                        val = 7;
                    } else if ("Sep".equalsIgnoreCase(strVal)) {
                        val = 8;
                    } else if ("Oct".equalsIgnoreCase(strVal)) {
                        val = 9;
                    } else if ("Nov".equalsIgnoreCase(strVal)) {
                        val = 10;
                    } else if ("Dec".equalsIgnoreCase(strVal)) {
                        val = 11;
                    }
                    kameNet.setMonth(val);
                    break;
                case day:
                    kameNet.setDay(Integer.parseInt(strVal));
                    break;
                case year:
                    kameNet.setYear(Integer.parseInt(strVal));
                    break;
                case hour:
                    kameNet.setHour(Integer.parseInt(strVal));
                    break;
                case minute:
                    kameNet.setMinute(Integer.parseInt(strVal));
                    break;
                case second: 
                    kameNet.setSecond(Integer.parseInt(strVal));
                    break;
                case millSec: 
                    kameNet.setMillSec(Integer.parseInt(strVal));
                    break;
                case MicroSec:
                    kameNet.setMicroSec(Integer.parseInt(strVal));
                    break;
                case nanoSec:
                    kameNet.setNanoSec(Integer.parseInt(strVal));
                    break;
                case sourceIpAddress:
                    kameNet.setSourceIpAddress(strVal);
                    break;
                case destinationIpAddress:
                    kameNet.setDestinationIpAddress(strVal);
                    break;
                case sourcePort:
                    kameNet.setSourcePort(Integer.parseInt(strVal));
                    break;
                case destinationPort:
                    kameNet.setDestinationPort(Integer.parseInt(strVal));
                    break;
                default:
                    throw new AppException("Internal error : Invalid field name");
            }
        }
    }    

    public static KameNet create(int id, String line) {
        KameNet kameNet = null;
        Matcher matcher = Field.pattern.matcher(line);
        if (matcher.find()) {
            kameNet = new KameNet(id);
            for (Field field : Field.values()) {
                field.setKameNet(kameNet, matcher.group(field.group));
//                Logger.info(field + " : " + matcher.group(field.group));
            }
        }
        return kameNet;
    }
}
