package org.feego.oauth2ClientDemo.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2AccessDeniedException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class DemoService {
	@Autowired
	@Qualifier("passwordTemplate")
	private OAuth2RestTemplate passwordTemplate;
	
	@Autowired
	@Qualifier("clientTemplate")
	private OAuth2RestTemplate clientTemplate;
	
	public Map<String,Object> sayHello(Long id){
		Map<String,Object> resp=new HashMap<>();
		Map<String,String> param=new HashMap<>();
		param.put("id", id.toString());
		resp=postForEntity("http://localhost:8081/user/item",param);
		return resp;
	}
	
	public Map<String,Object> sayBye(Long id){
		Map<String,Object> resp=new HashMap<>();
		Map<String,String> param=new HashMap<>();
		param.put("id", id.toString());
		resp=otherPostForEntity("http://localhost:8081/client/item",param);
		return resp;
	} 
	
	public Map<String,Object> getToken(String username,String password){
		Map<String,Object> resp=new HashMap<>();
		Map<String,Object> param=new HashMap<>();
		param.put("username", username);
		param.put("password", password);
		try {
			OAuth2AccessToken token=getOAuth2AccessToken(username,password);
			resp.put("token", token.getValue());
		} catch (OAuth2AccessDeniedException e) {
			resp.put("error", "get token failed");
			e.printStackTrace();
		}
		return resp;
	}
	
	/**
	 * 获取调用token
	 * */
	private OAuth2AccessToken getOAuth2AccessToken(String username,String password) throws InvalidGrantException{
		passwordTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("username", username);
		passwordTemplate.getOAuth2ClientContext().getAccessTokenRequest().set("password", password);
		OAuth2AccessToken token=null;
		token=passwordTemplate.getAccessToken();
		return token;
	}
	
	/**
	 * 使用ClientCredentials方式的远程调用方法
	 * */
	private Map<String,Object> otherPostForEntity(String url,Map<String,String> param){
		UriComponentsBuilder builder=UriComponentsBuilder.fromHttpUrl(url);
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		MultiValueMap<String,String> p=new LinkedMultiValueMap<>();
		for(String k:param.keySet()) {
			p.add(k, param.get(k));
		}
		builder.queryParams(p);
		UriComponents uriComponents=builder.build();
		try {
			ResponseEntity<Map> entity=clientTemplate.postForEntity(uriComponents.toUri(), headers, Map.class);
			return entity.getBody();
		}catch(OAuth2AccessDeniedException e) {
			Map<String,Object> resp=new HashMap<>();
			resp.put("access", "denied");
			return resp;
		}
	}
	
	/**
	 * 使用password方式的远程调用方法
	 * */
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
		try {
			ResponseEntity<Map> entity=passwordTemplate.postForEntity(uriComponents.toUri(), headers, Map.class);
			return entity.getBody();
		}catch(OAuth2AccessDeniedException e) {
			Map<String,Object> resp=new HashMap<>();
			resp.put("access", "denied");
			return resp;
		}
	}
}
