package com.keepcount.model.errorsuccess;

public class ErrorSuccess {

	private String message;

	public ErrorSuccess() {

	}

	public ErrorSuccess(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "ErrorSuccess [message=" + message + "]";
	}

}
