/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inprod.pojo.domain;

/**
 *
 * @author chry
 */
public class Configuration {
    private int _id;    
    private String _commonName;
    private String _sourceIpAddress;
    private int _sourcePort;
    private String _destinationIpAddress;
    private int _destinationPort;
    private int _ipv6;
    private int _goodThreshold;
    private int _mediumThreshold;
    
    public int getId() {
        return _id;
    }
    
    public void setId(int id) {
        _id = id;
    }

    public String getCommonName() {
        return _commonName;
    }
    
    public void setCommonName(String commonName) {
        _commonName = commonName;
    }

    public String getSourceIpAddress() {
        return _sourceIpAddress;
    }
    
    public void setSourceIpAddress(String sourceIpAddress) {
        _sourceIpAddress = sourceIpAddress;
    }

    public int getSourcePort() {
        return _sourcePort;
    }
    
    public void setSourcePort(int sourcePort) {
        _sourcePort = sourcePort;
    }

    public String getDestinationIpAddress() {
        return _destinationIpAddress;
    }
    
    public void setDestinationIpAddress(String destinationIpAddress) {
        _destinationIpAddress = destinationIpAddress;
    }

    public int getDestinationPort() {
        return _destinationPort;
    }
    
    public void setDstinationPort(int destinationPort) {
        _destinationPort = destinationPort;
    }

    public int getIpv6() {
        return _ipv6;
    }
    
    public void setIpv6(int ipv6) {
        _ipv6 = ipv6;
    }

    public int getGoodThreshold() {
        return _goodThreshold;
    }
    
    public void setGoodThreshold(int goodThreshold) {
        _goodThreshold = goodThreshold;
    }
    
    public int getMediumThreshold() {
        return _mediumThreshold;
    }
    
    public void setMediumThreshold(int mediumThreshold) {
        _mediumThreshold = mediumThreshold;
    }
}
