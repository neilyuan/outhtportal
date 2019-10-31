package com.mooc.oauth.gatewayapi.filter;

import com.marcosbarbero.cloud.autoconfigure.zuul.ratelimit.config.repository.DefaultRateLimiterErrorHandler;
import org.springframework.stereotype.Component;

@Component
public class RateLimitErrorHandler extends DefaultRateLimiterErrorHandler {

	@Override
	public void handleError(String message, Exception e) {
		super.handleError(message, e);
	}
}
