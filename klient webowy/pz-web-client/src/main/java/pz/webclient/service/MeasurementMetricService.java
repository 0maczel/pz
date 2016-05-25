/**
 * 
 */
package pz.webclient.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import pz.monitor.service.measurement.MeasurementDto;
import pz.monitor.service.metric.MetricDto;

/**
 * @author niemar
 *
 */
@Service
public class MeasurementMetricService {

	public MetricDto getMeasurementMetric(MeasurementDto measurement) {
		if (measurement == null)
			return null;
		return getMeasurementMetric(measurement.getMetric());
	}

	private MetricDto getMeasurementMetric(String uri) {
		if (StringUtils.isEmpty(uri))
			return null;
		RestTemplate template = new RestTemplate();
		URI targetUri = UriComponentsBuilder.fromUriString(uri).build().toUri();
		return template.getForObject(targetUri, MetricDto.class);
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
