package pz.webclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

// TODO usunac pozniej
@Controller
public class IndexController {

	@RequestMapping("/index")
	public ModelAndView hello(ModelAndView modelAndView) {
		modelAndView.setViewName("index");
		List<String> prefixes = new ArrayList<>();
		prefixes.add("blabla");
		modelAndView.addObject("monitorsPrefixes", prefixes);
		return modelAndView;
	}
}
