package com.mooc.oauth.gatewayapi.filter;

import com.mooc.oauth.gatewayapi.dto.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class OauthFilter extends ZuulFilter {

	private RestTemplate restTemplate = new RestTemplate();

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 2;
	}

	public boolean shouldFilter() {
		return true;
	}

	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		// Skip for token request
		if (StringUtils.containsIgnoreCase(request.getRequestURI(), "/token")) {
			return null;
		}

		// Skip if authorization header is blank
		String authHeader = request.getHeader("authorization");
		if (StringUtils.isBlank(authHeader)) {
			return null;
		}

		try {
			TokenInfo tokenInfo = getTokenInfo(authHeader);
			request.setAttribute("tokenInfo", tokenInfo);
		} catch (Exception e) {
			log.error("Validate token error", e);
			return null;
		}

		return null;
	}


	private TokenInfo getTokenInfo(String authHeader) {

		String authToken = StringUtils.substringAfter(authHeader, "bearer ");
		String oauthServiceUrl = "http://localhost:9002/oauth/check_token";

		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		httpHeaders.setBasicAuth("client-gateway-api", "123456");

		MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
		params.add("token", authToken);

		HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity(params, httpHeaders);

		ResponseEntity<TokenInfo> responseEntity = restTemplate.exchange(oauthServiceUrl, HttpMethod.POST, httpEntity, TokenInfo.class);
		log.info("token info : " + responseEntity.toString());

		return responseEntity.getBody();
	}
}
