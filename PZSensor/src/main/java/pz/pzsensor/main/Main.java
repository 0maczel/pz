package pz.pzsensor.main;


import pz.pzsensor.config.Config;
import http.rest.RestClientException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import pz.pzsensor.metrics.MetricFactory;
import pz.pzsensor.metrics.MetricNotSupportedError;
import pz.pzsensor.model.Metric;
import pz.pzsensor.model.Resource;
import pz.pzsensor.model.Sensor;
import pz.pzsensor.network.RESTRequester;
import pz.pzsensor.main.Gatherer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Pawel
 */
public class Main {
    public static void main(String [] args){
        for(String arg : args){
            System.out.println(arg);
        }
        // read config from file
        String configFile = args[0];
        Config cfg = new Config();
        cfg.read(configFile);
        
//        System.out.println(cfg.getInterval());
//        System.out.println(cfg.getMonitorURL());
//        for(String metric: cfg.getMetrics()){
//            System.out.println(metric);
//        }
        RESTRequester restRequester = new RESTRequester(cfg.getMonitorURL());
        List<Metric> usedMetrics = new ArrayList<Metric>();
        for(String metric: cfg.getMetrics()){
            Metric m = restRequester.getMetric(metric);
            if(m == null){
                m = restRequester.createMetric(metric);
            }
            usedMetrics.add(m);
        }
        
        Resource resource = restRequester.getResource(cfg.getSystemName());
        if(resource == null){
            resource = restRequester.createResource(cfg.getSystemName());
        }
        
        List<Sensor> sensors = new ArrayList<Sensor>();
        for(Metric m: usedMetrics){
            Sensor s = restRequester.getSensor(resource.name, m.name);
            if(s == null){
                s = restRequester.createSensor(resource, m);
            }
            System.out.println(s.id);
            s.metricObj = m;
            sensors.add(s);
        }
        
        
        List<Gatherer> gatherers = new ArrayList<Gatherer>();
        MetricFactory mf = new MetricFactory();
        for(Sensor s: sensors){
            Gatherer g = new Gatherer();
            g.setSensor(s);
            try {
                g.setMetricIface(mf.createMetric(s.metricObj.name));
                g.setRest(restRequester);
            } catch (MetricNotSupportedError ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
            gatherers.add(g);
        }

        while(true){
            for(Gatherer g: gatherers){
                g.gather();
            }
            try {
                Thread.sleep(cfg.getInterval()*1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }
}
