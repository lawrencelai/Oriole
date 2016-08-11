package org.oriole.document.exception;

public class ErrorDetail {
	
	private int status;
	private String message;
	private String url;
		
	public ErrorDetail(int status, String message, String url) {
		super();
		this.status = status;
		this.message = message;
		this.url = url;
	}

	public int getStatus() {
		return status;
	}
	
	public String getMessage() {
		return message;
	}

	public String getUrl() {
		return url;
	}

} 