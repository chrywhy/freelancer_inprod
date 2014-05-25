package com.freelancer.inprod.pojo.db;

import com.freelancer.inprod.exception.AppException;
import com.freelancer.inprod.log.Logger;
import com.freelancer.inprod.pojo.domain.KameNet;
import com.freelancer.inprod.sql.DbUtil;
import com.freelancer.inprod.sql.DbUtil.QueryResource;
import java.sql.Connection;

/**
 *
 * @author Albert Wang
 */
public class ConfigurationDao {
    public static final String DbName = "inprod";
    public static final String TableName = "Configuration";
    public static final String FullTableName = DbName + "." + TableName;
    private static final ConfigurationDao _instance = new ConfigurationDao();
    private Connection _conn;
    private ConfigurationDao() {
    }
            
    public static ConfigurationDao getInstance(Connection conn) {
        _instance.setConnection(conn);
        return _instance;
    }

    public void setConnection(Connection conn) {
        _conn = conn;
    }
    
    public boolean tableExist() {
        return DbUtil.tableExist(_conn, DbName, TableName);
    }
    
    public void createTable() {
        String sqlCmd = "CREATE TABLE " + FullTableName + " (" +  "\n" +
                        "    ID int(11) NOT NULL," +  "\n" +
                        "    CommonName varchar(100) NOT NULL," +  "\n" +
                        "    SourceIPAddress varchar(40) DEFAULT NULL," +  "\n" +
                        "    SourcePort int(11) NOT NULL," +  "\n" +
                        "    DestinationIPAddress varchar(40) NOT NULL," +  "\n" +
                        "    DestinationPort int(11) NOT NULL," +  "\n" +
                        "    IPv6 tinyint(4) NOT NULL," +  "\n" +
                        "    UNIQUE KEY ID (ID) USING BTREE" +  "\n" +
                        "    ) ENGINE=INNODB DEFAULT CHARSET=latin1";
//        Logger.info(sqlCmd);
        QueryResource qsr = null;
        try {
            qsr = DbUtil.executeUpdate(_conn, sqlCmd);
        } finally {
            DbUtil.closeQuietly(qsr);
        }
    }
    
    public int getMaxId() {
        String sqlCmd = "SELECT MAX(ID) AS maxID FROM " + FullTableName;
        QueryResource qsr = null;
        try {
            qsr = DbUtil.executeQuery(_conn, sqlCmd);
            if (DbUtil.next(qsr)) {
                return DbUtil.getInt(qsr, "maxID");
            } else {
                return 0;
            }
        } catch (Exception e) {
            throw new AppException("Can not get maximum ID value", e);
        } finally {
            DbUtil.closeQuietly(qsr);
        }
    }
    
    public void addRecord(KameNet kameNet) {
        Logger.info("==================== new configuration item packet " + kameNet.getPacket() + " ==============");
        String sqlcmd = "INSERT " + FullTableName + "(ID, CommonName, SourceIPAddress, SourcePort, DestinationIPAddress, DestinationPort, IPv6)" + "\n" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?)";
        QueryResource qsr = null;
        try {
            qsr = DbUtil.executeUpdate(_conn, 
                             sqlcmd, 
                             kameNet.getId(), 
                             kameNet.getCommonName(), 
                             kameNet.getSourceIpAddress(), 
                             kameNet.getSourcePort(),
                             kameNet.getDestinationIpAddress(),
                             kameNet.getDestinationPort(),
                             kameNet.getIPv6());
        } finally {
            DbUtil.closeQuietly(qsr);
        }
//        Logger.info(kameNet.toString());
    }
}
