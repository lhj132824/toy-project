package com.kh.toy.common.exception;

public class PageNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 521587827126031031L;
	
	public PageNotFoundException() {
		// stackTrace를 비워준다. 배열의 길이가 0인 친구를 넘겨준다.
		this.setStackTrace(new StackTraceElement[0]);
	}
}
