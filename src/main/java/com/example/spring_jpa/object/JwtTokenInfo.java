package com.example.spring_jpa.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class JwtTokenInfo {
        private String grantType;
        private String accessToken;
        private String refreshToken;
        private String key;
}
