package com.hetfdex.webservices.mobileapp.ui.controller.ui.model.response;

public enum ErrorMessages {
	INTERNAL_SERVER_ERROR("Internal server error"),
	AUTHENTICATION_FAIL("Authentication failure"),
	REQUIRED_FIELD_MISSING("Missing required field(s)"),
	RECORD_NOT_FOUND("Record not found"),
	RECORD_ALREADY_EXISTS("Record already exists"),
	UPDATE_RECORD_FAIL("Update record failure"),
	DELETE_RECORD_FAIL("Delete record failure"),
	EMAIL_NOT_VERIFIED("Email could not be verified");

	private String errorMessage;

	private ErrorMessages(String errorMessages) {
		this.errorMessage = errorMessages;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}