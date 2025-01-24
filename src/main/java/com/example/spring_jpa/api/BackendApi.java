package com.example.spring_jpa.api;

public interface BackendApi {
	
	static ApiErrorMessage getErrorMessage(int status, String message, String errorCode, String suggestion) {
		ApiErrorMessage apiErrorMessage = new ApiErrorMessage();
		apiErrorMessage.setStatus(status);
		apiErrorMessage.setMessage(message);
		apiErrorMessage.setErrorCode(errorCode);
		apiErrorMessage.setSuggestion(suggestion);
		return apiErrorMessage;
	}
	
	static ApiSuccessMessage getSuccessMessage(int status, String message) {
		ApiSuccessMessage apiSuccessMessage = new ApiSuccessMessage();
		apiSuccessMessage.setStatus(status);
		apiSuccessMessage.setMessage(message);
		return apiSuccessMessage;
	}
	
	void assertConnection();
	
	void loadData();
	
}
