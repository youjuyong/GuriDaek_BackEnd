package com.example.spring_jpa.api;

import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Hidden
@RestController
@RequestMapping("/api/swagger-ui")
public class SwaggerController {
	
	@GetMapping(path="/swagger-ui.css", produces = "text/css")
	public String getCss() {
		return toText(getClass().getResourceAsStream("/swagger-ui.css"));
	}
	
	private String toText(InputStream in) {
		try (BufferedReader br = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
			return br.lines()
			         .collect(Collectors.joining("\n"));
		} catch (Throwable ex) {
			return null;
		}
	}
	
}
