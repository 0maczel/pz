/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pz.pzsensor.metrics;

import static java.lang.Double.NaN;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

/**
 *
 * @author Pawel
 */
public class MemoryMetric implements pz.pzsensor.metrics.MetricInterface{

    @Override
    public double collect() {
        Sigar sigar = new Sigar();
        double result = NaN;
        try {
            Mem mem = sigar.getMem();
            result = mem.getActualUsed();
        } 
        catch (SigarException ex) {
            Logger.getLogger(MemoryMetric.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }
    
}
