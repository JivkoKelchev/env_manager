package com.jivko.env_manager.Services;

import java.io.*;
import java.util.HashMap;

public class XammpControllerService {

    //todo clear cmd processes after execution
    HashMap<String, Process> runningProcesses;
    public XammpControllerService(){
        this.runningProcesses = new HashMap<String, Process>();
    }

    public void startApache(String dir) {

        try {
            System.out.println("start apache on " + dir);
            //hide.vbs is using to be able to hide cmd window for appache
            File hideScript = new File(getClass().getClassLoader().getResource("hide.vbs").getFile());
            String hideScriptPath = hideScript.getAbsolutePath();
            Process p = Runtime.getRuntime().exec("wscript.exe " + hideScriptPath + " " + dir + "/apache_start.bat");



        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopApache(String dir) {
        try {
            System.out.println("stop apache on " + dir );
            Process p = Runtime.getRuntime().exec("cmd /c start /B " + dir + "/apache_stop.bat");
            p.waitFor();

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void startMySql(String dir) {
        try{
            System.out.println("start MySql on " + dir);
            //Process p = Runtime.getRuntime().exec("cmd /c start /B " + dir + "/mysql_start.bat");

            File hideScript = new File(getClass().getClassLoader().getResource("hide.vbs").getFile());
            String hideScriptPath = hideScript.getAbsolutePath();
            Process p = Runtime.getRuntime().exec("wscript.exe " + hideScriptPath + " " + dir + "/mysql_start.bat");


        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stopMySql(String dir) {
        try {
            System.out.println("stop MySql on " + dir);
            Process p = Runtime.getRuntime().exec("cmd /c start /B " + dir + "/mysql_stop.bat");
            p.waitFor();
        }catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
