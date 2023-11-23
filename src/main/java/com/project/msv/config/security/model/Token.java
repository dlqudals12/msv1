package com.project.msv.config.security.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class Token {
    
	private String id;
	private String access;
	private String refresh;
	
	@Data
    public static final class Request {
    	@NotEmpty
        private String id;
    	@NotEmpty
        private String password;
    }

    @Data
    @Builder
    public static final class Response {
        private String accToken;
        private String refToken;
    }
    
    @Data
    public static final class RefreshRequest{
    	@NotEmpty
    	private String refToken;
    }
    
}