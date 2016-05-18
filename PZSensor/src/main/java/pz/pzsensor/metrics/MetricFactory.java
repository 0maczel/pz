/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pz.pzsensor.metrics;

/**
 *
 * @author Pawel
 */
public class MetricFactory {
    public static final String CPU_METRIC_TAG = "CPU";
    public static final String MEM_METRIC_TAG = "MEM";
    public static final String [] TAGS = {CPU_METRIC_TAG,
                                          MEM_METRIC_TAG
                                         };
    
    public MetricInterface createMetric(String id) throws MetricNotSupportedError{
        MetricInterface metric = null;
        switch(id){
            case CPU_METRIC_TAG: metric = new CPUMetric();
                        break;
            case MEM_METRIC_TAG: metric = new MemoryMetric();
                        break;
            default:
                System.out.println("metric "+ id);
                throw new MetricNotSupportedError();
        }
        return metric;
    }
}
