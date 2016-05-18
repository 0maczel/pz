/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pz.pzsensor.main;

import pz.pzsensor.metrics.MetricInterface;
import pz.pzsensor.model.Sensor;
import pz.pzsensor.network.RESTRequester;

/**
 *
 * @author Pawel
 */
public class Gatherer {
    private Sensor sensor;
    private MetricInterface metricIface;
    private RESTRequester rest;
    
    public void gather(){
        double value = getMetricIface().collect();
        getRest().createMeasurement(""+value, getSensor());
    }

    /**
     * @return the sensor
     */
    public Sensor getSensor() {
        return sensor;
    }

    /**
     * @param sensor the sensor to set
     */
    public void setSensor(Sensor sensor) {
        this.sensor = sensor;
    }

    /**
     * @return the metricIface
     */
    public MetricInterface getMetricIface() {
        return metricIface;
    }

    /**
     * @param metricIface the metricIface to set
     */
    public void setMetricIface(MetricInterface metricIface) {
        this.metricIface = metricIface;
    }

    /**
     * @return the rest
     */
    public RESTRequester getRest() {
        return rest;
    }

    /**
     * @param rest the rest to set
     */
    public void setRest(RESTRequester rest) {
        this.rest = rest;
    }
}
