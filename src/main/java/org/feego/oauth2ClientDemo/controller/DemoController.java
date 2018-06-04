package org.feego.oauth2ClientDemo.controller;

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
	public Map<String,Object> sayHello(@RequestParam Long id) {
		Map<String,Object> result=svr.sayHello(id);
		result.put("id", id);
		return result;
	}
	
	@RequestMapping(value="/say-bye")
	public Map<String,Object> sayBye(@RequestParam Long id) {
		Map<String,Object> result=svr.sayBye(id);
		result.put("id", id);
		return result;
	}
	
	@RequestMapping(value="/get-token")
	public Map<String,Object> getToken(@RequestParam String username,@RequestParam String password){
		Map<String,Object> resp=svr.getToken(username, password);
		return resp;
	}
}
