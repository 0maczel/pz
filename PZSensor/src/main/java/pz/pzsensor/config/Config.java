/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pz.pzsensor.config;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Pawel
 * 
 * przykladowa konfiguracja
 * {
 *  "interval":600,
 *  "monitorURL": localhost:8080/
 *  "metrics": [
 *      {"name": "CPU"}, 
 *      {"name": "MEM"}
 *  ]
 * }
 */
public class Config {
    
    private String monitorURL;
    private long interval;
    private String [] metrics;
    private String systemName;
    private String hostName;
    
    final String INTERVAL_TAG = "interval";
    final String MONITOR_URL = "monitorURL";
    final String METRIC_TAG = "metrics";
    final String METRIC_TAG_NAME = "metricName";
    final String HOST_NAME = "hostName";
    
    public Config(){
        try {
            InetAddress addr;
            addr = InetAddress.getLocalHost();
            systemName = addr.getHostName();
        } catch (UnknownHostException ex) {
            Logger.getLogger(Config.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void read(String filename){
        Path path = Paths.get(filename);
        try{
            String content = new String(Files.readAllBytes(path));
            JSONObject jsonObj = new JSONObject(content);
            
            String intervalStr = jsonObj.getString(INTERVAL_TAG);
            interval = Long.parseLong(intervalStr);
            try{
                hostName = jsonObj.getString(HOST_NAME);
            }catch(Exception e){
                hostName = "";
            }
            monitorURL = jsonObj.getString(MONITOR_URL);
            JSONArray metricsArray = jsonObj.getJSONArray(METRIC_TAG);
            metrics = new String[metricsArray.length()];
            for(int i = 0; i< metricsArray.length(); ++i){
                metrics[i] = metricsArray.getJSONObject(i).getString(METRIC_TAG_NAME);
            }
            if(!hostName.isEmpty() || !hostName.equals(""))
                systemName = hostName;
        }
        catch(IOException e){
            
        }
    }
    
    /**
     * @return the monitorURL
     */
    public String getMonitorURL() {
        return monitorURL;
    }

    /**
     * @return the interval
     */
    public long getInterval() {
        return interval;
    }

    /**
     * @return the metrics
     */
    public String[] getMetrics() {
        return metrics;
    }

    /**
     * @return the systemName
     */
    public String getSystemName() {
        return systemName;
    }
    
    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }
}
