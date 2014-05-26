package com.inprod.service;

import com.inprod.FileUploader;
import com.inprod.log.AppLog;

public class FileUploadTask implements Runnable {    
    public FileUploadTask() {
    }

    public void run() {
        FileHandleRequest request = ServiceManager.getInstance().getRequest();
        FileUploader fileUploader = new FileUploader(request);
        fileUploader.generateTables();
    }
}
