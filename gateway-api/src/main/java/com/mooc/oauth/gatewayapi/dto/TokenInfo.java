package com.mooc.oauth.gatewayapi.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TokenInfo {

	private boolean active;
	private String client_id;
	private String[] scope;
	private String user_name;
	//Resource Ids
	private String[] aud;
	private Date exp;
	private String[] authorities;
}
