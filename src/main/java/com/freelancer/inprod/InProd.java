/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelancer.inprod;

import com.freelancer.inprod.exception.AppException;
import com.freelancer.inprod.log.Logger;
import com.freelancer.inprod.pojo.db.ConfigurationDao;
import com.freelancer.inprod.pojo.db.HttpResponseDao;
import com.freelancer.inprod.pojo.domain.KameNet;
import com.freelancer.inprod.pojo.file.KameNetDao;
import com.freelancer.inprod.sql.DbUtil;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Albert Wang
 */
public class InProd {
    public static final int FILE_LOAD_BUFFER_SIZE = 10 * 1024 * 1024; //10M bytes
    public static final int PREFIX_LENGTH = 3;

    private ConfigurationDao _configurationDao = null;
    private HttpResponseDao _httpResponseDao = null;
        
    public InProd() {        
    }

    public void generateTables(String synFileName, String rspFileName) {
        Logger.info("loading " + synFileName + "....");
        Connection conn = null;
        try {
            conn = DbUtil.getConnection();
            _configurationDao = ConfigurationDao.getInstance(conn);
            _httpResponseDao = HttpResponseDao.getInstance(conn);

            Logger.info("loading " + synFileName + " and update " +  ConfigurationDao.TableName + "....");
            KameNet[] synKameNets = _loadSynKameNet(synFileName);
            
            Logger.info("loadining " + rspFileName + " and update " + HttpResponseDao.TableName + "...");
            _loadAndMatchRspKameNet(rspFileName, synKameNets);
            
            Logger.info("collect all unmached SynKame.net...");
            _collectUnmachedSynKameNet(synKameNets);
        } finally {
            DbUtil.closeQuietly(conn);
        }
        Logger.info("Done");
    }
        
    private KameNet[] _loadSynKameNet(String fileName) {
        ArrayList<KameNet> kameNetArr = new ArrayList<KameNet>();
        String commonName = fileName.substring(PREFIX_LENGTH);
        BufferedReader in = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            in = new BufferedReader(new InputStreamReader(bis, "utf-8"), FILE_LOAD_BUFFER_SIZE);
            
            if (!_configurationDao.tableExist()) {
                _configurationDao.createTable();
            }
            int nextId = _configurationDao.getMaxId() + 1;    //next ID value
            String regex = "^.+\\s-r\\s+(\\S+)\\s+.*$";
            Pattern pattern = Pattern.compile(regex);        
            boolean foundCommonName = false;
            while (in.ready()) {
                String line = in.readLine();
                if (!foundCommonName) {
                    Matcher matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        commonName = matcher.group(1);
                        int pos = commonName.indexOf(".pcapng");
                        if (pos > 0) {
                            commonName = commonName.substring(0, pos);
                        }
                        foundCommonName = true;
                    }
                }
                KameNet kameNet = KameNetDao.create(nextId, line);
                if (kameNet == null) {
                    continue;
                }
                kameNetArr.add(kameNet);
                nextId++;
                kameNet.setCommonName(commonName);
                _configurationDao.addRecord(kameNet);
            }
            KameNet[] KameNets = new KameNet[kameNetArr.size()];
            kameNetArr.toArray(KameNets);
            return KameNets;
        } catch (IOException e) {
            throw new AppException("Failed to load file : " + fileName, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private void _loadAndMatchRspKameNet(String fileName, KameNet[] synKameNets) {
        BufferedReader in = null;
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
            in = new BufferedReader(new InputStreamReader(bis, "utf-8"), FILE_LOAD_BUFFER_SIZE);
            
            if (!_httpResponseDao.tableExist()) {
                _httpResponseDao.createTable();
            }

            while (in.ready()) {
                String line = in.readLine();
                KameNet rspKameNet = KameNetDao.create(0, line);
                if (rspKameNet == null) {
                    continue;
                }
                int highestSynPacket = 0;
                int machedIndex = -1;                
                for (int j = 0; j < synKameNets.length; j++) {
                    KameNet synKameNet = synKameNets[j];
                    boolean mached = _compRspKameNet(synKameNet, rspKameNet);
                    if (mached) {
                        synKameNet.increaseMachedRspCount();
                        int synPacket = synKameNet.getPacket();
                        if (synPacket > highestSynPacket) {
                            if (machedIndex >= 0) {
                                synKameNets[machedIndex].negativeMachedRspCount();  
                                                //find a newer one, mark the old one negative to avoid trying to match it again
                            }
                            machedIndex = j;
                            highestSynPacket = synPacket;
                        }
                    }
                }
                if (machedIndex >= 0) {
                    _httpResponseDao.addRecord(synKameNets[machedIndex], rspKameNet);
                }
            }
        } catch (IOException e) {
            throw new AppException("Failed to load file : " + fileName, e);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception e) {
                }
            }
        }
    }

    private boolean _compRspKameNet(KameNet synKameNet, KameNet rspKameNet) {
        if (synKameNet.getMachedRspCount() < 0) {   //Negative count means that there is a newer one in the array
                                                    //so, needn't trying to match this one
            return false;
        }
        return synKameNet.getSourceIpAddress().equals(rspKameNet.getDestinationIpAddress())
            && synKameNet.getSourcePort() == rspKameNet.getDestinationPort()
            && synKameNet.getDestinationIpAddress().equals(rspKameNet.getSourceIpAddress())
            && synKameNet.getDestinationPort() == rspKameNet.getSourcePort()
            && synKameNet.getPacket() < rspKameNet.getPacket();
    }

    private void _collectUnmachedSynKameNet(KameNet[] synKameNets) {
        for (int i = 0; i < synKameNets.length; i++) {           
            KameNet synKameNet = synKameNets[i];
            if (synKameNet.getMachedRspCount() > 0) {
                continue;
            }
            KameNet emptyRspKameNet = new KameNet();
            _httpResponseDao.addRecord(synKameNet, emptyRspKameNet);
        }
    }
}
