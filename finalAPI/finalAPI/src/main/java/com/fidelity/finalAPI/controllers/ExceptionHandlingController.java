package com.fidelity.finalAPI.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fidelitytechnologies.training.blogapp.errors.ValidateException;

@ControllerAdvice
@RestController
public class ExceptionHandlingController {
	
	private class JsonErrorResponse {
		private int errorCode;
		private String errorMsg;
		
		public JsonErrorResponse() {
			
		}
		
		public JsonErrorResponse(int code, String msg) {
			super();
			this.errorCode = code;
			this.errorMsg = msg;
		}
		
		public String getErrorMsg() {
			return errorMsg;
		}
		
		public int getErrorCode() {
			return errorCode;
		}
		
		public void setErrorMsg(String msg) {
			this.errorMsg = msg;
		}
		
		public void setErrorCode(int errorCode) {
			this.errorCode = errorCode;
		}
		
	}

	public ExceptionHandlingController() {
		// TODO Auto-generated constructor stub
	}
	
	@ExceptionHandler(ValidateException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<JsonErrorResponse> handleNoSuchEelemntFoundException(ValidateException exception) {
		return new ResponseEntity<ExceptionHandlingController.JsonErrorResponse> (
				new JsonErrorResponse(HttpStatus.NOT_FOUND.value(), exception.getError_msg())
				, HttpStatus.NOT_FOUND);
	}
	
	

}
