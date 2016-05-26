/**
 * 
 */
package pz.webclient.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import pz.monitor.service.measurement.MeasurementDto;
import pz.monitor.service.resource.ResourceDto;

/**
 * @author niemar
 *
 */
@Service
public class MeasurementResourceService {
	@Autowired
	private ResourceService resourceService;
	
	public ResourceDto getMeasurementResource(MeasurementDto measurement) {
		if (measurement == null)
			return null;
		return resourceService.getResourceFromUri(measurement.getResource());
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
