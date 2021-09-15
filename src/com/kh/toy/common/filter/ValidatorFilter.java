package com.kh.toy.common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.toy.common.code.ErrorCode;
import com.kh.toy.common.exception.HandlableException;
import com.kh.toy.common.exception.PageNotFoundException;
import com.kh.toy.member.validator.JoinForm;



public class ValidatorFilter implements Filter {

    public ValidatorFilter() {
        // TODO Auto-generated constructor stub
    }

	
	public void destroy() {
		// TODO Auto-generated method stub
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		String[] uriArr = httpRequest.getRequestURI().split("/");
		
		//  /member/join   =>  [ , member,  join]
		String redirectUrl = null;
		
		if(uriArr.length != 0) {
			
			switch (uriArr[1]) {
			case "member":
				redirectUrl = memberValidation(httpRequest,uriArr);
				break;
			}
			
			if(redirectUrl != null) {
				httpResponse.sendRedirect(redirectUrl);
				return;
			}
	
		chain.doFilter(request, response);
		}
	}
	
	private String memberValidation(HttpServletRequest httpRequest, String[] uriArr) {
		
		String redirectUrl = null;
		
		switch (uriArr[2]) {
		case "join":
			JoinForm joinForm = new JoinForm(httpRequest);
			if(!joinForm.test()) {
				redirectUrl = "/member/join-form";
			}
			break;
		}
		return redirectUrl;
	}
	
	
	
	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
