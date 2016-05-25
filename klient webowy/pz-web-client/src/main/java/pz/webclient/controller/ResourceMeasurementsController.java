/**
 * 
 */
package pz.webclient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import pz.monitor.service.measurement.MeasurementDto;
import pz.monitor.service.metric.MetricDto;
import pz.monitor.service.resource.ResourceDto;
import pz.webclient.service.MeasurementsService;
import pz.webclient.service.MetricService;
import pz.webclient.service.ResourceService;

/**
 * @author niemar
 *
 */
@RestController
public class ResourceMeasurementsController {
	private static final Long MESUREMENT_LIMIT = 30L;
	
	@Autowired
	private ResourceService resourcesService;
	@Autowired
	private MetricService metricService;
	@Autowired 
	private MeasurementsService measurementService;
	
	@RequestMapping(value={"/resources/{resourceId}/metrics/{metricId}/measurements"}, method = RequestMethod.GET)
	public ModelAndView getResource(@PathVariable Long resourceId, @PathVariable Long metricId, ModelAndView modelAndView) {
		System.out.println("ResourceMeasurementsController");
		ResourceDto resource = resourcesService.getResourceById(resourceId);
		MetricDto metric = metricService.getMetricById(metricId);
		// TODO dodac obsluge error page
		// TODO dodac sprawdzanie czy argumenty nie sa nullami bo wtedy zwroci wszystkie pomiary !
		List<MeasurementDto> measurements = measurementService.getMeasurements(resource.getName(), metric.getName(),
				null, null, MESUREMENT_LIMIT);
		modelAndView.addObject("resource", resource);
		modelAndView.addObject("metric", metric);
		modelAndView.addObject("measurements", measurements);
		modelAndView.setViewName("resource-measurements-view");
		return modelAndView;
	}
}
