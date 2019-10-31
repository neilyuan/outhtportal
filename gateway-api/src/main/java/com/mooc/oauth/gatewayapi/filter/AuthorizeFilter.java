package com.mooc.oauth.gatewayapi.filter;

import com.mooc.oauth.gatewayapi.dto.TokenInfo;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Component
public class AuthorizeFilter extends ZuulFilter {

	public String filterType() {
		return "pre";
	}

	public int filterOrder() {
		return 3;
	}

	public boolean shouldFilter() {
		return true;
	}

	public Object run() throws ZuulException {
		RequestContext requestContext = RequestContext.getCurrentContext();
		HttpServletRequest request = requestContext.getRequest();

		if (isNeedAuth(request)) {
			TokenInfo tokenInfo = (TokenInfo) request.getAttribute("tokenInfo");
			if (tokenInfo != null && tokenInfo.isActive()) {
				if (!hasPermission(tokenInfo, request)) {
					handleError(403, "No permission", requestContext);
				}
			} else {
				if (!StringUtils.startsWith(request.getRequestURI(), "/token")) {
					handleError(401, "Invalid token" ,requestContext);
				}
			}

		}
		return null;
	}

	private boolean hasPermission(TokenInfo tokenInfo, HttpServletRequest request) {
		return RandomUtils.nextInt() % 2 == 0;
	}

	private boolean isNeedAuth(HttpServletRequest request) {
		return true;
	}

	private void handleError(int status, String message ,RequestContext requestContext) {
		requestContext.getResponse().setContentType("application/json");
		requestContext.setResponseStatusCode(status);
		requestContext.setResponseBody("{\"message\": " + message + " }" );
		requestContext.setSendZuulResponse(false);
	}
}
