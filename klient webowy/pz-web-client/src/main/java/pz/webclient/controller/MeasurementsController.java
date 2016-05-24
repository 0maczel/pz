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
import pz.webclient.service.MeasurementsService;

/**
 * @author niemar
 *
 */
@RestController
public class MeasurementsController {
	private static final Long MEASUREMENTS_LIMIT = 30L;
	
	@Autowired 
	private MeasurementsService measurementService;
	
	@RequestMapping(value={"/measurements"}, method = RequestMethod.GET)
	public ModelAndView getMeasurements(ModelAndView modelAndView) {
		List<MeasurementDto> measurements = measurementService.getMeasurements(null, null, null, null, MEASUREMENTS_LIMIT);
		// TODO dodac obsluge error page
		modelAndView.addObject("measurements", measurements);
		modelAndView.setViewName("measurements-view");
		return modelAndView;
	}
}
