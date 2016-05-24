/**
 * 
 */
package pz.webclient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import pz.monitor.service.metric.MetricDto;
import pz.webclient.search.SearchByNameAttribute;
import pz.webclient.service.MetricService;

/**
 * @author niemar
 *
 */
@RestController
public class MetricsController {
	@Autowired
	private MetricService metricService;
	
	@RequestMapping(value={"/metrics"}, method = RequestMethod.GET)
	public ModelAndView getMetrics(ModelAndView modelAndView) {
		List<MetricDto> metricsList = metricService.getMetricByName(null);
		// TODO dodac obslude error page
		modelAndView.addObject("metrics", metricsList);
		modelAndView.setViewName("metrics-view");
		modelAndView.addObject("searchByNameAttribute", new SearchByNameAttribute());
		return modelAndView;
	}
	
	@RequestMapping(value={"/metrics"}, method = RequestMethod.POST)
	public ModelAndView searchResources(@ModelAttribute SearchByNameAttribute searchByNameAttribute, ModelAndView modelAndView) {
		if(searchByNameAttribute == null || StringUtils.isEmpty(searchByNameAttribute.getName()))
			return getMetrics(new ModelAndView());
		List<MetricDto> metricsList = metricService.getMetricByName(searchByNameAttribute.getName());
		// TODO dodac obslude error page
		modelAndView.addObject("metrics", metricsList);
		modelAndView.setViewName("metrics-view");
		return modelAndView;
	}
		
}
