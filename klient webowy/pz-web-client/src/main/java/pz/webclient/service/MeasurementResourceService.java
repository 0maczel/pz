/**
 * 
 */
package pz.webclient.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import pz.monitor.service.measurement.MeasurementDto;
import pz.monitor.service.resource.ResourceDto;

/**
 * @author niemar
 *
 */
@Service
public class MeasurementResourceService {
	// TODO zmienic nazwe klasy
	public ResourceDto getMeasurementResource(MeasurementDto measurement) {
		if (measurement == null)
			return null;
		return getMeasurementResource(measurement.getResource());
	}
	
	private ResourceDto getMeasurementResource(String uri) {
		if (StringUtils.isEmpty(uri))
			return null;
		RestTemplate template = new RestTemplate();
		URI targetUri = UriComponentsBuilder.fromUriString(uri).build().toUri();
		return template.getForObject(targetUri, ResourceDto.class);
	}
	
	public List<ResourceDto> getMeasurementsResources(List<MeasurementDto> measurements) {
		if (measurements == null || measurements.isEmpty())
			return Collections.emptyList();
		List<ResourceDto> resources = new ArrayList<>();
		ResourceDto resource;
		for (MeasurementDto measurement : measurements) {
			resource = getMeasurementResource(measurement);
			if(resource != null)
				resources.add(resource);
		}
		return resources;
	}
}
