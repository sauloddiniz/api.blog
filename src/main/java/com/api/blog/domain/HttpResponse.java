package com.api.blog.domain;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class HttpResponse {

	@JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy hh:mm:ss", timezone = "America/Sao_Paulo")
	private Date timeStemp;
	private int httpStatusCode;
	private HttpStatus httpStatus;
	private String reason;
	private String message;

	public HttpResponse() {
	}

	public HttpResponse(int httpStatusCode, HttpStatus httpStatus, String reason, String message) {
		this.httpStatusCode = httpStatusCode;
		this.httpStatus = httpStatus;
		this.reason = reason;
		this.message = message;
		this.timeStemp = new Date();
	}

	public int getHttpStatusCode() {
		return httpStatusCode;
	}

	public void setHttpStatusCode(int httpStatusCode) {
		this.httpStatusCode = httpStatusCode;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Date getTimeStemp() {
		return timeStemp;
	}

	public void setTimeStemp(Date timeStemp) {
		this.timeStemp = timeStemp;
	}
}
