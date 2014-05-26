package com.inprod.listener;

import com.inprod.log.AppLog;
import com.inprod.service.ServiceManager;
import com.inprod.sql.DbUtil;
import com.inprod.util.InprodConf;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class Listener
        implements ServletContextListener {
    private static long _startedOn = 0L;

    public void contextDestroyed(ServletContextEvent event) {
        AppLog.info("Stopping service manager ...");
        ServiceManager.getInstance().stop();

        AppLog.info("Inprod stopped");
    }

    public void contextInitialized(ServletContextEvent event) {
        _startedOn = System.currentTimeMillis() / 1000L;
        AppLog.info("Starting service manager ...");
        DbUtil.initConnectionPool(InprodConf.getDbHost(), InprodConf.getDbPort(), InprodConf.getDbUser(), InprodConf.getDbPassword());
        ServiceManager.getInstance().start();
        AppLog.info("Inprod started");
    }

    public long getStartedOn() {
        return _startedOn;
    }
}
