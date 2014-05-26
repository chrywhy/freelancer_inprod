package com.inprod.pojo.db;

import com.inprod.sql.DbUtil;
import com.inprod.util.InprodConf;
import java.sql.Connection;

public abstract class AbstractDao {
    public static final String DbName = InprodConf.getDbName();
    public final String TableName;
    public final String FullTableName;
    
    protected Connection _conn;
    
    public AbstractDao(String tableName) {
        TableName = tableName;
        FullTableName = DbName + "." + TableName;
    }

    public void setConnection(Connection conn) {
        _conn = conn;
    }
    
    public boolean tableExist() {
        return DbUtil.tableExist(_conn, DbName, TableName);
    }
}
