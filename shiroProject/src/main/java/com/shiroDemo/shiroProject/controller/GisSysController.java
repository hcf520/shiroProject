package com.shiroDemo.shiroProject.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/gis")
public class GisSysController {

	private static Logger logger = LogManager.getLogger(GisSysController.class);

	@RequestMapping(value = "/openlayers", method = RequestMethod.GET)
	public String openlayers() {
		System.err.println("openlayers");
		System.err.println("openlayers");
		System.err.println("openlayers");
		System.err.println("openlayers");
		System.err.println("openlayers");
		System.err.println("openlayers");
		System.err.println("openlayers");
		System.err.println("openlayers");
		System.err.println("openlayers");
		System.err.println("openlayers");
		System.err.println("openlayers");
		return "/gis/openlayers";
	}
}
