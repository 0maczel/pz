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
import pz.monitor.service.resource.ResourceDto;
import pz.monitor.service.sensor.SensorDto;

/**
 * @author niemar
 *
 */
@Service
public class ResourceMetricService {
	@Autowired
	private SensorService sensorService;
	@Autowired
	private MetricService metricService;
	@Autowired
	private ResourceService resourceService;
	
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
			// TODO check
			metric = metricService.getMetricFromUri(sensorDto.getMetric());
			if(metric != null)
				metricsDtos.add(metric);
		}
		return metricsDtos;
	}
	
	public List<ResourceDto> getMetricResources(String metricName) {
		if(StringUtils.isEmpty(metricName))
			return Collections.emptyList();
		List<SensorDto> sensors = sensorService.getSensor(null, metricName);
		if(sensors == null || sensors.isEmpty())
			return Collections.emptyList();
		List<ResourceDto> resourceDtos = getResourcesFromSensors(sensors);	
		return resourceDtos;
	}
	private List<ResourceDto> getResourcesFromSensors(List<SensorDto> sensors) {
		List<ResourceDto> resources = new ArrayList<>();
		ResourceDto resource;
		for (SensorDto sensorDto : sensors) {
			resource = resourceService.getResourceFromUri(sensorDto.getResource());
			if(resource != null)
				resources.add(resource);
		}
		return resources;
	}
	
}
