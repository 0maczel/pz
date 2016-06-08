/**
 * 
 */
package pz.webclient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pz.monitor.service.metric.MetricDto;
import pz.monitor.service.resource.ResourceDto;
import pz.webclient.path.Paths;
import pz.webclient.search.SearchByNameAttribute;
import pz.webclient.service.MetricService;
import pz.webclient.service.ResourceMetricService;

/**
 * @author niemar
 *
 */
@RestController
public class MetricController {
	@Autowired
	private MetricService metricService;
	@Autowired
	private ResourceMetricService resourceMetricService;

	@RequestMapping(value = { Paths.METRICS }, method = RequestMethod.GET)
	public ModelAndView getMetrics(ModelAndView modelAndView) {
		try {
			List<MetricDto> metricsList = metricService.getMetricByName(null);
			modelAndView.addObject("metrics", metricsList);
			modelAndView.setViewName("metrics-view");
			modelAndView.addObject("searchByNameAttribute", new SearchByNameAttribute());
		} catch (Exception e) {
			modelAndView.setViewName("error-view");
		}
		return modelAndView;
	}

	@RequestMapping(value = { Paths.METRIC }, method = RequestMethod.GET)
	public ModelAndView searchResources(@PathVariable Long metricId, ModelAndView modelAndView) {
		try {
			MetricDto metric = metricService.getMetricById(metricId);
			List<ResourceDto> resources = resourceMetricService.getMetricResources(metric.getName());
			modelAndView.addObject("metric", metric);
			modelAndView.addObject("resources", resources);
			modelAndView.setViewName("metric-details-view");
		} catch (Exception e) {
			modelAndView.setViewName("error-view");
		}
		return modelAndView;
	}
}
