package com.kh.toy.common.exception;

import com.kh.toy.common.code.ErrorCode;

public class HandlableException extends RuntimeException{

	private static final long serialVersionUID = 521587827126031031L;
	public ErrorCode error;
	
	public HandlableException(ErrorCode error) {
		this.error = error;
		this.setStackTrace(new StackTraceElement[0]);
	}
	
	public HandlableException(ErrorCode error, Exception e) {
		this.error = error;
		e.printStackTrace();
		this.setStackTrace(new StackTraceElement[0]);
	}
	
	
	//1. 콘솔에 로그
	//2. result.jsp를 사용해 사용자에게 알림메시지 띄워주기, 경로 재지정
	//	  발생한 예외별 에러 메세지와, 재지정할 경로
}
