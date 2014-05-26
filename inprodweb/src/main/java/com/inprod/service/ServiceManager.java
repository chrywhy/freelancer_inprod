package com.inprod.service;

import com.inprod.exception.AppException;
import com.inprod.log.AppLog;
import com.inprod.util.InprodConf;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ServiceManager {
    private ScheduledFuture<?> _schedule = null;
    private static final ScheduledExecutorService _executor = Executors.newScheduledThreadPool(1);
    private int _interval = InprodConf.fileLoadInterval();
    private FileHandleRequest _request = new FileHandleRequest();

    private static ServiceManager _INSTANCE = new ServiceManager();
    
    public static ServiceManager getInstance() {
        return _INSTANCE;
    }

    public void setRequest(FileHandleRequest request) {
        _request = request;
    }
    
    public FileHandleRequest getRequest() {
        return _request;
    }
    
    public int getFileLoadInterval() {
        return _interval;
    }
    
    public void setFileLoadInterval(int interval) {
        if (interval == _interval) {
            return;
        }
        _interval = interval;
        if (_schedule == null) {
            return;
        }
        _schedule.cancel(true);
        startNewSchedule();
    }
            
    private ServiceManager() {
    }
    
    public void startNewSchedule() {
        FileUploadTask task = new FileUploadTask();
        _schedule = _executor.scheduleWithFixedDelay(task, 0, _interval, TimeUnit.MINUTES);
        AppLog.info("Scheduled file upload every " + _interval + "minutes");
    }

    public void start() {
        _interval = InprodConf.fileLoadInterval();
        _request = new FileHandleRequest();
        startNewSchedule();
    }

    public void stop() {
        if (_schedule != null) {
            _schedule.cancel(true);
        }
        if (_executor != null) {
            _executor.shutdownNow();
        }
    }
}
