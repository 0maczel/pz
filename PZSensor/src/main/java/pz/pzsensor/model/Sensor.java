/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pz.pzsensor.model;

import pz.pzsensor.network.RESTRequester;
import org.hyperic.sigar.Cpu;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author Pawel
 */
public class Sensor {
    public int id;
    public String resource;
    public String metric;
    public String resourceId;
    public String metricId;
    
    public Metric metricObj;
    public Resource resourceObj;
    
}
