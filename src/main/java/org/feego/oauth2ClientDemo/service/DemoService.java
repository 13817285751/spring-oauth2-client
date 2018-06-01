package org.feego.oauth2ClientDemo.service;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DemoService {
	@Autowired
	private OAuth2RestTemplate template;
	
	public Map<String,Object> sayHello(String name){
		Map<String,Object> resp=new HashMap<>();
		Map<String,String> param=new HashMap<>();
		param.put("name", "jack");
		resp=postForEntity("http://localhost:8081/api/say-hello",param);
		return resp;
	}
	
	private Map<String,Object> postForEntity(String url,Map<String,String> param){
		UriComponentsBuilder builder=UriComponentsBuilder.fromHttpUrl(url);
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		MultiValueMap<String,String> p=new LinkedMultiValueMap<>();
		for(String k:param.keySet()) {
			p.add(k, param.get(k));
		}
		builder.queryParams(p);
		UriComponents uriComponents=builder.build();
		ResponseEntity<Map> entity=template.postForEntity(uriComponents.toUri(), headers, Map.class);
		return entity.getBody();
	}
}
