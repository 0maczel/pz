package pz.webclient.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import pz.webclient.service.MonitorsService;

// TODO usunac pozniej
@Controller
public class IndexController {

	@Autowired
	MonitorsService monitorsService;
	
	@RequestMapping("/index")
	public ModelAndView hello(ModelAndView modelAndView) {
		modelAndView.setViewName("index");
		List<String> prefixes = monitorsService.getMonitorsPrefixes();
		modelAndView.addObject("monitorsPrefixes", prefixes);
		return modelAndView;
	}
}
