/**
 * 
 */
package pz.webclient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pz.monitor.service.measurement.MeasurementDto;
import pz.monitor.service.metric.MetricDto;
import pz.monitor.service.resource.ResourceDto;
import pz.webclient.service.MeasurementMetricService;
import pz.webclient.service.MeasurementsService;
import pz.webclient.service.MeasurementResourceService;

/**
 * @author niemar
 *
 */
@RestController
public class MeasurementController {
	private static final Long MEASUREMENTS_LIMIT = 20L;
	
	@Autowired 
	private MeasurementsService measurementService;
	@Autowired
	private MeasurementResourceService resourceMeasurementsService;
	@Autowired
	private MeasurementMetricService measurementMetricService;
	
	@RequestMapping(value={"/measurements"}, method = RequestMethod.GET)
	public ModelAndView getMeasurements(ModelAndView modelAndView) {
		List<MeasurementDto> measurements = measurementService.getMeasurements(null, null, null, null, MEASUREMENTS_LIMIT);
		// TODO dodac obsluge error page
		List<ResourceDto> resources = resourceMeasurementsService.getMeasurementsResources(measurements);
		List<MetricDto> metrics = measurementMetricService.getMeasurementsMetrics(measurements);
		modelAndView.addObject("measurements", measurements);
		modelAndView.addObject("resources", resources);
		modelAndView.addObject("metrics", metrics);
		modelAndView.setViewName("measurements-view");
		return modelAndView;
	}

}
