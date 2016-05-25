package pz.webclient.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(path = "/")
	public ModelAndView hello(ModelAndView modelAndView) {
		return new ModelAndView("redirect:/resources");
	}
}
