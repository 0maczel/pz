/**
 * 
 */
package pz.webclient.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pz.monitor.service.measurement.MeasurementDto;
import pz.monitor.service.metric.MetricDto;

/**
 * @author niemar
 *
 */
@Service
public class MeasurementMetricService {
	@Autowired
	private MetricService metricService;
	
	public MetricDto getMeasurementMetric(MeasurementDto measurement) {
		if (measurement == null)
			return null;
		return metricService.getMetricFromUri(measurement.getMetric());
	}
	
	public List<MetricDto> getMeasurementsMetrics(List<MeasurementDto> measurements) {
		if (measurements == null || measurements.isEmpty())
			return null;
		List<MetricDto> metrics = new ArrayList<>();
		MetricDto metric;
		for (MeasurementDto measurement : measurements) {
			metric = getMeasurementMetric(measurement);
			if(metric != null)
				metrics.add(metric);
		}
		return metrics;
	}
}