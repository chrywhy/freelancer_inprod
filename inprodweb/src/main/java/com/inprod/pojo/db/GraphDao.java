package com.inprod.pojo.db;

import com.inprod.exception.AppException;
import com.inprod.log.AppLog;
import com.inprod.pojo.domain.Graph;
import com.inprod.pojo.domain.KameNet;
import com.inprod.sql.DbUtil;
import com.inprod.sql.DbUtil.QueryResource;
import java.sql.Connection;

/**
 *
 * @author Albert Wang
 */
public class GraphDao extends AbstractDao {
    private static final GraphDao _instance = new GraphDao();
    private GraphDao() {
        super("Graph");
    }
            
    public static GraphDao getInstance(Connection conn) {
        _instance.setConnection(conn);
        return _instance;
    }

    public void createTable() {
        String sqlCmd = "CREATE TABLE " + FullTableName + " (" +  "\n" +
                        "    CommonName varchar(100) NOT NULL," +  "\n" +
                        "    SynDate date NOT NULL," +  "\n" +
                        "    SynHour int(11) NOT NULL," +  "\n" +
                        "    SynNMinutes int(11) NOT NULL," +  "\n" +
                        "    SynNMinutes int(11) NOT NULL," +  "\n" +
                        "    NumNMinutes int(11) NOT NULL," +  "\n" +
                        "    Good int(11) NOT NULL," +  "\n" +
                        "    Medium int(11)  NOT NULL," +  "\n" +
                        "    Bad int(11)  NOT NULL," +  "\n" +
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
    
    public void addRecord(Graph graph) {
        AppLog.info("==================== new graph item packet " + " ==============");
        String sqlcmd = "INSERT " + FullTableName + "(CommonName, SynDate, SynHour, SynNMinutes, NumNMinutes, Good, Mediuem, Bad, IPv6)" + "\n" +
                        "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        QueryResource qsr = null;
        try {
            qsr = DbUtil.executeUpdate(_conn, 
                             sqlcmd, 
                             graph.getCommonName(), 
                             graph.getSynDate(), 
                             graph.getSynHour(),
                             graph.getSynNMinute(),
                             graph.getNumNMinute(),
                             graph.getGood(),
                             graph.getMedium(),
                             graph.getBad(),
                             graph.getIPv6());
        } finally {
            DbUtil.closeQuietly(qsr);
        }
//        Logger.info(kameNet.toString());
    }
}
