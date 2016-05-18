/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pz.pzsensor.network;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Maps;
import http.rest.AbstractRestClient;
import http.rest.RestClient;
import http.rest.RestClientException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import pz.pzsensor.model.Measurement;
import pz.pzsensor.model.Metric;
import pz.pzsensor.model.Resource;
import pz.pzsensor.model.Sensor;
import org.apache.http.Header;
import org.apache.http.HttpResponse;


/**
 *
 * @author Pawel
 */
public class RESTRequester {
    
    private String serverAddress;
    
    private static final String SENSORS_ENDPOINT = "/sensors";
    private static final String METRICS_ENDPOINT = "/metrics";
    private static final String MEASUREMENTS_ENDPOINT = "/measurements";
    private static final String RESOURCES_ENDPOINT = "/resources";
    
    public RESTRequester(String serverAddress){
        this.serverAddress = serverAddress;
    }
    public Metric getMetric(String metricName){
        Metric result = null;
        try {
            String metricsUrl = this.serverAddress + METRICS_ENDPOINT;
            RestClient client = RestClient.builder().build();
            Map<String, String> params = Maps.newHashMap();
            params.put("name-like", metricName);
            
            List<Metric> metrics = client.get(metricsUrl, params, new TypeReference<List<Metric>>() {});
            //Zakladam ze monitor zawsze zwraca albo jednoelementowa liste
            //albo pusta liste
            if(!metrics.isEmpty()){
                result = metrics.get(0);
            }
        } catch (RestClientException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    public List<Metric> getMetrics(){
        RestClient client = RestClient.builder().build();
        String metricsUrl = this.serverAddress + METRICS_ENDPOINT;
        List<Metric> metrics = null;
        try {
            metrics = client.get(metricsUrl, null, new TypeReference<List<Metric>>() {}); 
            
            if(metrics != null){
                for(Metric m: metrics){
                    System.out.println(m.name);
                }
            }
        } 
        catch (RestClientException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return metrics;
    }
    
    public Metric createMetric(String metricName) {
        Metric metric = new Metric();
        metric.name =  metricName;
        
        String metricsUrl = this.serverAddress + METRICS_ENDPOINT;
        
        RestClient client = RestClient.builder().build();
        
        Header header = null;
        try {
            header = client.create(metricsUrl, metric, 200);
            metric = getMetric(metricName);
        } catch (IOException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
            metric = null;
        } catch (RestClientException ex) {
            System.out.println(ex.getMessage());
            metric = null;
        }
        if(header != null){
            System.out.println("Location header is: "+ header.getValue());
        }
        
        System.out.println("Created metric id = " + metric.id);
        
        return metric;
    }
    
    public Resource getResource(String resourceName){
        Resource result = null;
        try {
            String metricsUrl = this.serverAddress + RESOURCES_ENDPOINT;
            RestClient client = RestClient.builder().build();
            Map<String, String> params = Maps.newHashMap();
            params.put("name-like", resourceName);
            
            List<Resource> resources = client.get(metricsUrl, params, new TypeReference<List<Resource>>() {});
            
            //Zakladam ze monitor zawsze zwraca albo jednoelementowa liste
            //albo pusta liste
            if(!resources.isEmpty()){
                result = resources.get(0);
            }
        } catch (RestClientException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    
    
    public Resource createResource(String resourceName) {
        Resource resource = new Resource();
        resource.name =  resourceName;
        
        String metricsUrl = this.serverAddress + RESOURCES_ENDPOINT;
        
        RestClient client = RestClient.builder().build();
        
        Header header = null;
        try {
            header = client.create(metricsUrl, resource, 200);
            resource = getResource(resourceName);
        } catch (IOException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
            resource = null;
        } catch (RestClientException ex) {
            System.out.println(ex.getMessage());
            resource = null;
        }
        if(header != null){
            System.out.println("Location header is: "+ header.getValue());
        }
        
        System.out.println("Created resource id = " + resource.id);
        
        return resource;
    }
    
    
    public Sensor createSensor(Resource resource, Metric metric){
        Sensor sensor = new Sensor();
        
        sensor.metricId = "" + metric.id;
        sensor.resourceId = "" + resource.id;
        
        String sensorUrl = this.serverAddress + SENSORS_ENDPOINT;
        RestClient client = RestClient.builder().build();
        
        Header header = null;
        try {
            header = client.create(sensorUrl, sensor, 200);
            sensor = getSensor(resource.name, metric.name);
        } catch (RestClientException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        }
        sensor.metric = metric.name;
        sensor.resource = resource.name;
        return sensor;
    }

    public Sensor getSensor(String resourceName, String metricName) {
        Sensor result = null;
        try {
            
            String metricsUrl = this.serverAddress + SENSORS_ENDPOINT;
            RestClient client = RestClient.builder().build();
            Map<String, String> params = Maps.newHashMap();
            params.put("resource-name", resourceName);
            params.put("metric-name", metricName);
            
            List<Sensor> resources = client.get(metricsUrl, params, new TypeReference<List<Sensor>>() {});
            
            //Zakladam ze monitor zawsze zwraca albo jednoelementowa liste
            //albo pusta liste
            if(!resources.isEmpty()){
                result = resources.get(0);
            }
        } catch (RestClientException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
    
    public void createMeasurement(String value, Sensor s){
        try {
            String measurementsUrl = this.serverAddress + SENSORS_ENDPOINT + "/" + s.id +  MEASUREMENTS_ENDPOINT;
            RestClient client = RestClient.builder().build();
            Measurement meas = new Measurement();
            meas.value = value;
            
            client.create(measurementsUrl, meas, 200);
        } catch (RestClientException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RESTRequester.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
