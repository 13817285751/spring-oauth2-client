package org.feego.oauth2ClientDemo.controller;

import java.util.HashMap;
import java.util.Map;

import org.feego.oauth2ClientDemo.service.DemoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/demo",method=RequestMethod.POST,produces="application/json")
public class DemoController {
	@Autowired
	private DemoService svr;
	
	@RequestMapping(value="/say-hello")
	public Map<String,Object> getChk(@RequestParam String name) {
		Map<String,Object> result=svr.sayHello(name);
		result.put("name", name);
		return result;
	}
}
