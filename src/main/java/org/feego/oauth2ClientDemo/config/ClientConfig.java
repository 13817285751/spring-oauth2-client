package org.feego.oauth2ClientDemo.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.token.grant.client.ClientCredentialsResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

@Configuration
@EnableOAuth2Client
public class ClientConfig {
	
	@Bean("oauth2ClientContextFilter")
	public OAuth2ClientContextFilter getOAuth2ClientContextFilter(){
		return new OAuth2ClientContextFilter();
	}
	
	@Bean
	public ClientCredentialsResourceDetails getClientCredentialsResourceDetails() {
		ClientCredentialsResourceDetails res=new ClientCredentialsResourceDetails();
		List<String> scope=new ArrayList<>();
		scope.add("read");
		res.setAccessTokenUri("http://localhost:8080/oauth/token");
		res.setClientId("testClientId");
		res.setClientSecret("secret");
		res.setScope(scope);
		res.setGrantType("client_credentials");
		//res.setTokenName("demo");
		return res;
	}
	
	@Bean
	public ResourceOwnerPasswordResourceDetails getResourceOwnerPasswordResourceDetails() {
		ResourceOwnerPasswordResourceDetails res=new ResourceOwnerPasswordResourceDetails();
		res.setAccessTokenUri("http://localhost:8080/oauth/token");
		res.setClientId("testClientId");
		res.setClientSecret("secret");
		res.setGrantType("password");
		return res;
	}
	
	@Bean("passwordTemplate")
	public OAuth2RestTemplate getOAuth2RestTemplate() {
		OAuth2RestTemplate template=new OAuth2RestTemplate(getResourceOwnerPasswordResourceDetails());
		MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
		template.setMessageConverters(Arrays.asList(converter));
		return template;
	}
	
	@Bean("clientTemplate")
	public OAuth2RestTemplate getClientOAuth2RestTemplate() {
		OAuth2RestTemplate template=new OAuth2RestTemplate(getClientCredentialsResourceDetails());
		MappingJackson2HttpMessageConverter converter=new MappingJackson2HttpMessageConverter();
		template.setMessageConverters(Arrays.asList(converter));
		return template;
	}
}
