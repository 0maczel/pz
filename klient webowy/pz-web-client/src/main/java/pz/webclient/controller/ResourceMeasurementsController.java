/**
 * 
 */
package pz.webclient.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pz.monitor.service.measurement.MeasurementDto;
import pz.monitor.service.metric.MetricDto;
import pz.monitor.service.resource.ResourceDto;
import pz.webclient.constant.Constants;
import pz.webclient.path.Paths;
import pz.webclient.service.MeasurementsService;
import pz.webclient.service.MetricService;
import pz.webclient.service.ResourceService;

/**
 * @author niemar
 *
 */
@RestController
public class ResourceMeasurementsController {
	@Autowired
	private ResourceService resourcesService;
	@Autowired
	private MetricService metricService;
	@Autowired
	private MeasurementsService measurementService;

	@RequestMapping(value = { Paths.RESOURCE + "/" + Paths.METRIC + "/measurements" }, method = RequestMethod.GET)
	public ModelAndView getResource(@PathVariable Long resourceId, @PathVariable Long metricId,
			ModelAndView modelAndView) {
		try {
			ResourceDto resource = resourcesService.getResourceById(resourceId);
			MetricDto metric = metricService.getMetricById(metricId);
			// TODO dodac sprawdzanie czy argumenty nie sa nullami bo wtedy
			// zwroci wszystkie pomiary !
			List<MeasurementDto> measurements = measurementService.getMeasurements(resource.getName(), metric.getName(),
					null, null, Constants.MESUREMENTS_LIMIT);
			modelAndView.addObject("resource", resource);
			modelAndView.addObject("metric", metric);
			modelAndView.addObject("measurements", measurements);
			modelAndView.addObject("datasToChart", buildDatasToChart(measurements));
			modelAndView.setViewName("resource-measurements-view");
		} catch (Exception e) {
			modelAndView.setViewName("error-view");
		}
		return modelAndView;
	}

	private String buildDatasToChart(List<MeasurementDto> measurements) {
		StringBuilder sb = new StringBuilder("[");
		Iterator<MeasurementDto> measurementDtoIter = measurements.iterator();
		while (measurementDtoIter.hasNext()) {
			MeasurementDto measurementDto = measurementDtoIter.next();
			sb.append("{\"label\": \"").append(measurementDto.getCreationTimestamp());
			BigDecimal total = new BigDecimal(measurementDto.getValue(), MathContext.DECIMAL64);
			total = total.setScale(4, BigDecimal.ROUND_DOWN);
			sb.append("\",\"value\": \"").append(total).append("\"}");
			if (measurementDtoIter.hasNext()) {
				sb.append(",");
			}
		}
		sb.append("]");
		return sb.toString();
	}

}
