package com.inprod.util;

import com.inprod.exception.AppException;
import com.inprod.log.AppLog;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileUtil {
    static public boolean exists(String path) {
        File f = new File(path);
        return f.exists();
    }

    static public String readFileToString(String fileName) {
        File file = new File(fileName);
        FileReader fileReader = null;
        String ret = null;
        try {
            fileReader = new FileReader(fileName);
            int size = (int) file.length();
            char[] buf = new char[size];
            fileReader.read(buf, 0, size);
            ret = new String(buf);
            fileReader.close();
        }
        catch (IOException e) {
            throw new AppException("Cannot read content from file : " + fileName, e);
        }
        return ret;
    }
    
    void load(String inputFile) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File(inputFile)));
            BufferedReader in = new BufferedReader(new InputStreamReader(bis, "utf-8"), 10 * 1024 * 1024);//10M缓存

            while (in.ready()) {
                String line = in.readLine();
                AppLog.info(line);
            }
            in.close();
        } catch (IOException e) {
            throw new AppException("Failed to load file : " + inputFile, e);
        }
    }
}
