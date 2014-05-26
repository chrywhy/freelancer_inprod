package com.inprod.pojo.db;

import com.inprod.exception.AppException;
import com.inprod.log.AppLog;
import com.inprod.pojo.domain.KameNet;
import com.inprod.sql.DbUtil;
import com.inprod.sql.DbUtil.QueryResource;
import java.sql.Connection;

/**
 *
 * @author Albert Wang
 */
public class ConfigurationDao extends AbstractDao {
    private static final ConfigurationDao _instance = new ConfigurationDao();
    private ConfigurationDao() {
        super("Configuration");
    }
            
    public static ConfigurationDao getInstance(Connection conn) {
        _instance.setConnection(conn);
        return _instance;
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
                        "    GoodThreshold int(11) DEFAULT 100000," +  "\n" +
                        "    MediumThreshold int(11)  DEFAULT 200000," +  "\n" +
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
        AppLog.info("==================== new configuration item packet " + kameNet.getPacket() + " ==============");
        String sqlcmd = "INSERT " + FullTableName + "("
                + "ID, "                    //1
                + "CommonName, "            //2
                + "SourceIPAddress, "       //3
                + "SourcePort, "            //4
                + "DestinationIPAddress, "  //5
                + "DestinationPort, "       //6
                + "IPv6, "                  //7
                + "GoodThreshold, "         //8
                + "MediumThreshold)" + "\n" +   //9
                       "VALUES(?, "    //1
                            + "?, "    //2
                            + "?, "    //3
                            + "?, "    //4
                            + "?, "    //5
                            + "?, "    //6
                            + "?, "    //7
                            + "?, "    //8
                            + "?)";     //9
        QueryResource qsr = null;
        try {
            qsr = DbUtil.executeUpdate(_conn, 
                             sqlcmd, 
                             kameNet.getId(),                       //1
                             kameNet.getCommonName(),               //2
                             kameNet.getSourceIpAddress(),          //3
                             kameNet.getSourcePort(),               //4
                             kameNet.getDestinationIpAddress(),     //5
                             kameNet.getDestinationPort(),          //6
                             kameNet.getIPv6(),                     //7
                             kameNet.getGoodThreshold(),            //8
                             kameNet.getMediumThreshold());          //9
        } finally {
            DbUtil.closeQuietly(qsr);
        }
//        Logger.info(kameNet.toString());
    }
}
