package com.example.spring_jpa.api;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "HTTP 성공 Status, Message 래퍼 객체")
@Data
public class ApiSuccessMessage {
	
	private int status;
	private String message;

}
