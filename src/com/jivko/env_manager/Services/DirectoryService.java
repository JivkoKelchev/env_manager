package com.jivko.env_manager.Services;

import java.io.File;

public class DirectoryService implements Service{

    public Boolean isMySqlInsalled(String dir) {
        String binPath = dir+"\\mysql\\bin\\mysql.exe";
        if(this.fileExist(binPath)){
            return true;
        }
        return false;
    }

    private Boolean fileExist(String filePathString) {
        File f = new File(filePathString);
        if(f.exists() && !f.isDirectory()) {
            return true;
        }
        return false;
    }

    private Boolean dirExist(String dirPathString) {
        File dir = new File(dirPathString);
        if(dir.exists() && dir.isDirectory()) {
            return true;
        }else {
            return false;
        }
    }


}
