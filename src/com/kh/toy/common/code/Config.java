package com.kh.toy.common.code;

public enum Config {
	//DOMAIN("http://pclass.ga"),
	DOMAIN("http://localhost:9090"),
	COMPANY_EMAIL("lhj132824@gmail.com"),
	SMTP_AUTHENTICATION_ID("lhj132824@gmail.com"),
	SMTP_AUTHENTICATION_PASSWORD("wjscoals102$"),
	UPLOAD_PATH("C:\\CODE\\upload\\");
	
	public final String DESC;
	
	Config(String desc) {
		this.DESC = desc;
	}
}
