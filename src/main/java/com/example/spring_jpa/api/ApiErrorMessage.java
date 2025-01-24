package com.example.spring_jpa.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "HTTP 에러 Status, Message, ErrorCode 래퍼 객체")
@Data
public class ApiErrorMessage {
	
	private int status;
	private String message;
	private String errorCode;
	private String suggestion;
	
}
