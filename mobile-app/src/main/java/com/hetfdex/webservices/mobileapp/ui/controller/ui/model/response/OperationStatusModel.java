package com.hetfdex.webservices.mobileapp.ui.controller.ui.model.response;

public class OperationStatusModel {
	private String operationName;
	private String operationUserID;

	private Boolean operationSuccess;

	public String getOperationName() {
		return operationName;
	}

	public void setOperationName(String operationName) {
		this.operationName = operationName;
	}

	public String getOperationUserID() {
		return operationUserID;
	}

	public void setOperationUserID(String operationUserID) {
		this.operationUserID = operationUserID;
	}

	public Boolean getOperationSuccess() {
		return operationSuccess;
	}

	public void setOperationSuccess(Boolean operationSuccess) {
		this.operationSuccess = operationSuccess;
	}
}