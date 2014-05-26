/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.freelancer.inprod.pojo.db;

import com.freelancer.inprod.log.Logger;
import com.freelancer.inprod.pojo.domain.KameNet;
import com.freelancer.inprod.sql.DbUtil;
import java.sql.Connection;
import java.sql.Date;

/**
 *
 * @author Albert Wang
 */
public class HttpResponseDao {
    public static final String DbName = "inprod";
    public static final String TableName = "HTTPResponse";
    public static final String FullTableName = DbName + "." + TableName;
    private static final HttpResponseDao _instance = new HttpResponseDao();
    private Connection _conn;
    private HttpResponseDao() {
    }
            
    public static HttpResponseDao getInstance(Connection conn) {
        _instance.setConnection(conn);
        return _instance;
    }

    public void setConnection(Connection conn) {
        _instance._conn = conn;
    }
    
    public boolean tableExist() {
        return DbUtil.tableExist(_conn, DbName, TableName);
    }
    
    public void createTable() {
        String sqlCmd = "CREATE TABLE " + FullTableName + " (" +  "\n" +
                        "    ID int(11) NOT NULL," +  "\n" +
                        "    SYNPacket int(11) NOT NULL," +  "\n" +
                        "    SynDate date NOT NULL," +  "\n" +
                        "    SynHour int(11) NOT NULL," +  "\n" +
                        "    SynMinutes int(11) NOT NULL," +  "\n" +
                        "    SynSeconds int(11) NOT NULL," +  "\n" +
                        "    SynMilliseconds int(11) NOT NULL," +  "\n" +
                        "    SynMicroseconds int(11) NOT NULL," +  "\n" +
                        "    SynNanoseconds int(11) NOT NULL," +  "\n" +
                        "    ResponsePacket int(11) NOT NULL," +  "\n" +
                        "    ResponseDate date NOT NULL," +  "\n" +
                        "    ResponseHour int(11) NOT NULL," +  "\n" +
                        "    ResponseMinutes int(11) NOT NULL," +  "\n" +
                        "    ResponseSeconds int(11) NOT NULL," +  "\n" +
                        "    ResponseMilliseconds int(11) NOT NULL," +  "\n" +
                        "    ResponseMicroseconds int(11) NOT NULL," +  "\n" +
                        "    ResponseNanoseconds int(11) NOT NULL," +  "\n" +
                        "    Difference bigint(20) NOT NULL," +  "\n" +
                        "    KEY SYNPacket (SYNPacket) USING BTREE," +  "\n" +
                        "    KEY ResponsePacket (ResponsePacket) USING BTREE" +  "\n" +
                        ") ENGINE=INNODB DEFAULT CHARSET=latin1";
//        Logger.info(sqlCmd);
        DbUtil.QueryResource qsr = null;
        try {
            qsr = DbUtil.executeUpdate(_conn, sqlCmd);
        } finally {
            DbUtil.closeQuietly(qsr);
        }
    }

    public void addRecord(KameNet synKameNet, KameNet rspKameNet) {
        Logger.info("==================== new HTTPResponse packet " + rspKameNet.getPacket() + " for Syn Package " + synKameNet.getPacket() + " ==============");
        String sqlcmd = "INSERT " + FullTableName + "( ID,"                       //1
                     +  "\n                            SYNPacket,"                //2
                     +  "\n                            SynDate,"                  //3
                     +  "\n                            SynHour,"                  //4
                     +  "\n                            SynMinutes,"               //5
                     +  "\n                            SynSeconds,"               //6
                     +  "\n                            SynMilliseconds,"          //7
                     +  "\n                            SynMicroseconds,"          //8
                     +  "\n                            SynNanoseconds,"           //9
                     +  "\n                            ResponsePacket,"           //10
                     +  "\n                            ResponseDate,"             //11
                     +  "\n                            ResponseHour,"             //12
                     +  "\n                            ResponseMinutes,"          //13
                     +  "\n                            ResponseSeconds,"          //14
                     +  "\n                            ResponseMilliseconds,"     //15
                     +  "\n                            ResponseMicroseconds,"     //16
                     +  "\n                            ResponseNanoseconds,"      //17
                     +  "\n                            Difference)"               //18
                     +  "\n               VALUES(?, "         //1
                     +  "\n                      ?, "         //2
                     +  "\n                      ?, "         //3
                     +  "\n                      ?, "         //4
                     +  "\n                      ?, "         //5
                     +  "\n                      ?, "         //6
                     +  "\n                      ?, "         //7
                     +  "\n                      ?, "         //8
                     +  "\n                      ?, "         //9
                     +  "\n                      ?, "         //10
                     +  "\n                      ?, "         //11
                     +  "\n                      ?, "         //12
                     +  "\n                      ?, "         //13
                     +  "\n                      ?, "         //14
                     +  "\n                      ?, "         //15
                     +  "\n                      ?, "         //16
                     +  "\n                      ?, "         //17
                     +  "\n                      ?)";         //18
        DbUtil.QueryResource qsr = null;
        try {
            Date synDate = synKameNet.getDate();
            Date rspDate = rspKameNet.getDate();
            long nanoDiff = 0;
            if (rspKameNet.getPacket() > 0) {   //If it is not empty one, the calculate the time difference in Nanos
                long millDiff = rspDate.getTime() - synDate.getTime();
                long microDiff = millDiff * 1000 + rspKameNet.getMicroSec() - synKameNet.getMicroSec();
                nanoDiff = microDiff * 1000 + rspKameNet.getNanoSec() - synKameNet.getNanoSec();
            }

//            Logger.info("" + synKameNet.getSecond() + "  " + rspKameNet.getSecond());
            qsr = DbUtil.executeUpdate(_conn, 
                             sqlcmd, 
                             synKameNet.getId(),            //1
                             synKameNet.getPacket(),        //2
                             synDate,                       //3
                             synKameNet.getHour(),          //4
                             synKameNet.getMinute(),        //5
                             synKameNet.getSecond(),        //6
                             synKameNet.getMillSec(),       //7
                             synKameNet.getMicroSec(),      //8
                             synKameNet.getNanoSec(),       //9
                             rspKameNet.getPacket(),        //10
                             rspDate,                       //11
                             rspKameNet.getHour(),          //12
                             rspKameNet.getMinute(),        //13
                             rspKameNet.getSecond(),        //14
                             rspKameNet.getMillSec(),       //15
                             rspKameNet.getMicroSec(),      //16
                             rspKameNet.getNanoSec(),       //17
                             nanoDiff                       //18
                      );
        } finally {
            DbUtil.closeQuietly(qsr);
        }
    }
}
