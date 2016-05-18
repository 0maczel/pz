/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pz.pzsensor.metrics;

import static java.lang.Double.NaN;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author Pawel
 */
public class CPUMetric implements MetricInterface{

    @Override
    public double collect() {
        Sigar sigar = new Sigar();
        double result = NaN;
        try{
            CpuPerc cpu = sigar.getCpuPerc();
            result = cpu.getCombined();
        }
        catch(SigarException e){
            
        }
        return result;
    }
    
}
