package com.inprod.sql;

import com.inprod.exception.AppException;
import com.inprod.log.AppLog;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

final public class DbUtil {
    /**
     * Shall we audit using of DB connections
     */
    static public volatile boolean AUDIT = true;
    static public volatile boolean DEBUG = false;

    static public final int MAXACTIVE = 10;
    static public final int MAXIDLE = 50;
    static public final int MAXWAIT = 180;
    static public final String VALUDATIONQUERY = "/* ping */ SELECT 1";
    static public final boolean REMOVEABANDONED = true;
    static public final boolean LOGABANDONED = true;
    static public final String DRIVER = "com.mysql.jdbc.Driver";

    static public String basePath = "";
    static private BasicDataSource _ds = null;

    public static class QueryResource {
        public PreparedStatement pstmt = null;
        public ResultSet rs = null;
        public int rt = 0;
    }
    
    public static void initConnectionPool(String host, int port, String username, String password) {
        _ds = new BasicDataSource();

        _ds.setMaxActive(MAXACTIVE);
        _ds.setMaxIdle(MAXIDLE);
        _ds.setMaxWait(MAXWAIT);
        _ds.setUsername(username);
        _ds.setPassword(password);
        _ds.setDriverClassName(DRIVER);
        _ds.setValidationQuery(VALUDATIONQUERY);
        String URL = "jdbc:mysql://" + host + ":" + port + "?dumpQueriesOnException=true&amp;useUnicode=true&amp;characterEncoding=UTF-8";
        _ds.setUrl(URL);
        _ds.setRemoveAbandoned(REMOVEABANDONED);
        _ds.setLogAbandoned(LOGABANDONED);
    }

    public static Connection getConnection() {
        try {
            Connection conn = _ds.getConnection();
            if (conn == null) {
                throw new AppException("Unable to get DB connection");
            }
            try {
                if (!conn.getAutoCommit()) {
                    conn.setAutoCommit(true);
                }

                return conn;
            } catch (Exception e) {
                throw new AppException("Unable to get DB connection");
            }
        }
        catch (Exception e) {
            AppLog.error("Failed to get db connection", e);
            return null;
        }
    }

    public static void closeQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.close();
            }
            catch (Exception ignore) {
             // quiet
            }
        }
    }

    public static void closeQuietly(QueryResource qrs) {
        if (qrs != null) {
            DbUtil.closeQuietly(qrs.pstmt);
            DbUtil.closeQuietly(qrs.rs);
        }
    }
    
    private static void closeQuietly(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        }
        catch (SQLException e) { // NOPMD
            // quiet
        }
    }
    
    private static void closeQuietly(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        }
        catch (SQLException e) { // NOPMD
            // quiet
        }
    }

    
    public static void rollbackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            }
            catch (Exception ignore) {
             // quiet
            }
        }
    }

    public static void setAutoCommit(Connection conn) throws SQLException {
        if (!conn.getAutoCommit()) {
            conn.setAutoCommit(true);
        }
    }
    
    private static void _setParams(PreparedStatement pstmt, Object... params) throws SQLException {
        int i = 0;
        for (Object o : params) {
            if (o == null) {
                pstmt.setString(i + 1, null);
            }
            else if (o instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) o);
            }
            else if (o instanceof Long) {
                pstmt.setLong(i + 1, (Long) o);
            }
            else if (o instanceof byte[]) {
                pstmt.setBytes(i + 1, (byte[]) o);
            }
            else {
                pstmt.setString(i + 1, o.toString());
            }

            i++;
        }
    }
    
    public static QueryResource executeQuery(Connection conn, String sql, Object... params) {
        QueryResource qrs = new QueryResource();

        try {
            qrs.pstmt = conn.prepareStatement(sql);
            _setParams(qrs.pstmt, params);
            qrs.rs = qrs.pstmt.executeQuery();
            return qrs;
        } catch (SQLException e) {
            throw new AppException(sql, e);
        }
    }
    
    public static QueryResource executeUpdate(Connection conn, String sql, Object... params) {        
        QueryResource qrs = new QueryResource();
        try {
            qrs.pstmt = conn.prepareStatement(sql);
            _setParams(qrs.pstmt, params);
            qrs.rt = qrs.pstmt.executeUpdate();
            return qrs;
        } catch (SQLException e) {
            throw new AppException(sql, e);
        }
    }
    
    public static boolean next(QueryResource qrs) throws SQLException {
        if (qrs == null || qrs.rs == null) {
            AppLog.error("SQL Error: Null ResordSet");
            throw new AppException("SQL Error, Null ResordSet");
        }
        try {
            return qrs.rs.next();
        }
        catch (SQLException e) {
            throw new AppException("SQL Error", e);
        }
    }
    
    public static int getInt(QueryResource qrs, String columnLabel) throws SQLException {
        if (qrs == null || qrs.rs == null) {
            AppLog.error("SQL Error: Null ResordSet");
            throw new AppException("SQL Error, Null ResordSet");
        }
        try {
            return qrs.rs.getInt(columnLabel);
        }
        catch (SQLException e) {
            throw new AppException("SQL Error", e);
        }
    }
    
    public static int getInt(QueryResource qrs, int columnIndex) throws SQLException {
        if (qrs == null || qrs.rs == null) {
            AppLog.error("SQL Error: Null ResordSet");
            throw new AppException("SQL Error, Null ResordSet");
        }
        try {
            return qrs.rs.getInt(columnIndex);
        }
        catch (SQLException e) {
            throw new AppException("Failed to read Database data", e);
        }
    }
    
    public static String getString(QueryResource qrs, String columnLabel) throws SQLException {
        if (qrs == null || qrs.rs == null) {
            AppLog.error("SQL Error: Null ResordSet");
            throw new AppException("SQL Error, Null ResordSet");
        }
        try {
            return qrs.rs.getString(columnLabel);
        }
        catch (SQLException e) {
            throw new AppException("Failed to read Database data", e);
        }
    }
    
    public static String getString(QueryResource qrs, int columnIndex) throws SQLException {
        if (qrs == null || qrs.rs == null) {
            AppLog.error("SQL Error: Null ResordSet");
            throw new AppException("SQL Error, Null ResordSet");
        }
        try {
            return qrs.rs.getString(columnIndex);
        }
        catch (SQLException e) {
            throw new AppException("Failed to read Database data", e);
        }
    }
    
    public static long getLong(QueryResource qrs, String columnLabel) throws SQLException {
        if (qrs == null || qrs.rs == null) {
            AppLog.error("SQL Error: Null ResordSet");
            throw new AppException("SQL Error, Null ResordSet");
        }
        try {
            return qrs.rs.getLong(columnLabel);
        }
        catch (SQLException e) {
            throw new AppException("Failed to read Database data", e);
        }
    }
    
    public static long getLong(QueryResource qrs, int columnIndex) throws SQLException {
        if (qrs == null || qrs.rs == null) {
            AppLog.error("SQL Error: Null ResordSet");
            throw new AppException("SQL Error, Null ResordSet");
        }
        try {
            return qrs.rs.getLong(columnIndex);
        }
        catch (SQLException e) {
            throw new AppException("Failed to read Database data", e);
        }
    }
    
    public static boolean tableExist(Connection conn, String dbName, String tableName) {
        String sqlcmd = "SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES " + "\n" +
                        "WHERE TABLE_SCHEMA='"+ dbName + "' and TABLE_NAME='" + tableName + "'";
        DbUtil.QueryResource qsr = DbUtil.executeQuery(conn, sqlcmd);
        try {
            return DbUtil.next(qsr);
        } catch (Exception e) {
            throw new AppException("Failed to get table infomation", e);
        } finally {
            DbUtil.closeQuietly(qsr);
        }
    }
}
