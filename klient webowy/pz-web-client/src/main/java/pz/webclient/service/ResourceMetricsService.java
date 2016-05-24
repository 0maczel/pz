/**
 * 
 */
package pz.webclient.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import pz.monitor.service.metric.MetricDto;
import pz.monitor.service.sensor.SensorDto;

/**
 * @author niemar
 *
 */
@Service
public class ResourceMetricsService {
	@Autowired
	private SensorService sensorService;
	@Autowired
	private MetricService metricService;
	
	public List<MetricDto> getResourceMetrics(String resourceName) {
		if(StringUtils.isEmpty(resourceName))
			return Collections.emptyList();
		List<SensorDto> sensors = sensorService.getSensor(resourceName, null);
		if(sensors == null || sensors.isEmpty())
			return Collections.emptyList();
		List<MetricDto> metricsDtos = getMetricsFromSensors(sensors);	
		return metricsDtos;
	}

	private List<MetricDto> getMetricsFromSensors(List<SensorDto> sensors) {
		List<MetricDto> metricsDtos = new ArrayList<MetricDto>();
		MetricDto metric;
		for (SensorDto sensorDto : sensors) {
			metric = metricService.getMetricById(sensorDto.getId());
			if(metric != null)
				metricsDtos.add(metric);
		}
		return metricsDtos;
	}
}
