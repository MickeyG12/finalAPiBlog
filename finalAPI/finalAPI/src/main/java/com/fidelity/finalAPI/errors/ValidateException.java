/**
 * 
 */
package com.fidelity.finalAPI.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Validate exception")
public class ValidateException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private String error_msg;
	
	/**
	 * 
	 */
	public ValidateException() {
		// TODO Auto-generated constructor stub
	}
	
	public ValidateException(String extraMsg) {
		super();
		this.error_msg = extraMsg;
	}

	public String getError_msg() {
		return error_msg;
	}

	public void setError_msg(String error_msg) {
		this.error_msg = error_msg;
	}
	
	

}
