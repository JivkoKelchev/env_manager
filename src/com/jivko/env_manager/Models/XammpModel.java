package com.jivko.env_manager.Models;

public class XammpModel {
    private String dir;
    private Boolean apacheStatus;
    private Boolean sqlStatus;

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Boolean getApacheStatus() {
        return apacheStatus;
    }

    public void setApacheStatus(Boolean apacheStatus) {
        this.apacheStatus = apacheStatus;
    }

    public Boolean getSqlStatus() {
        return sqlStatus;
    }

    public void setSqlStatus(Boolean sqlStatus) {
        this.sqlStatus = sqlStatus;
    }
}
