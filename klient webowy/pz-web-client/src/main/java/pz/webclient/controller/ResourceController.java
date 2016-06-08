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
import pz.webclient.service.ResourceMetricService;
import pz.webclient.service.ResourceService;

/**
 * @author niemar
 *
 */
@RestController
public class ResourceController {
	@Autowired
	private ResourceService resourcesService;
	@Autowired
	private ResourceMetricService resourceMetricService;

	@RequestMapping(value = { Paths.RESOURCES }, method = RequestMethod.GET)
	public ModelAndView getResources(ModelAndView modelAndView) {
		try {
			List<ResourceDto> resourcesList = resourcesService.getResourceByName(null);
			modelAndView.addObject("resources", resourcesList);
			modelAndView.setViewName("resources-view");
			modelAndView.addObject("searchByNameAttribute", new SearchByNameAttribute());
		} catch (Exception e) {
			modelAndView.setViewName("error-view");
		}
		return modelAndView;
	}

	@RequestMapping(value = { Paths.RESOURCE }, method = RequestMethod.GET)
	public ModelAndView getResource(@PathVariable Long resourceId, ModelAndView modelAndView) {
		try {
			ResourceDto resource = resourcesService.getResourceById(resourceId);
			List<MetricDto> metrics = resourceMetricService.getResourceMetrics(resource.getName());
			modelAndView.addObject("resource", resource);
			modelAndView.addObject("metrics", metrics);
			modelAndView.setViewName("resource-details-view");
		} catch (Exception e) {
			modelAndView.setViewName("error-view");
		}
		return modelAndView;
	}
}
