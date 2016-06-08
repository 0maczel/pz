package pz.webclient.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import pz.monitor.service.measurement.MeasurementDto;
import pz.monitor.service.metric.MetricDto;
import pz.monitor.service.resource.ResourceDto;
import pz.webclient.constant.Constants;
import pz.webclient.service.MeasurementMetricService;
import pz.webclient.service.MeasurementResourceService;
import pz.webclient.service.MeasurementsService;

import java.util.List;

/**
 * Created by mnawrot on 07/06/16.
 */
@RestController
public class ComplexMeasurementsController {

    @Autowired
    private MeasurementsService measurementService;
    @Autowired
    private MeasurementResourceService resourceMeasurementsService;
    @Autowired
    private MeasurementMetricService measurementMetricService;

    @RequestMapping(value={"/complex-measurements"}, method = RequestMethod.GET)
    public ModelAndView getMeasurements(ModelAndView modelAndView) {

        //TODO: CHANGE BEHAVIOUR TO GET COMPLEX MEASUREMENTS INSTEAD OF SIMPLE ONES

        List<MeasurementDto> measurements = measurementService.getMeasurements(null, null, null, null, Constants.MESUREMENTS_LIMIT);
        List<ResourceDto> resources = resourceMeasurementsService.getMeasurementsResources(measurements);
        List<MetricDto> metrics = measurementMetricService.getMeasurementsMetrics(measurements);
        modelAndView.addObject("measurements", measurements);
        modelAndView.addObject("resources", resources);
        modelAndView.addObject("metrics", metrics);
        modelAndView.setViewName("complex-measurements-view");
        return modelAndView;
    }
}
