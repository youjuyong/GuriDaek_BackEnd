package com.example.spring_jpa.jwt;

import com.example.spring_jpa.object.Member;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {
    private String userId;
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;

    private Member member;

    @Override
    public String toString() {
        return "TokenDto{" +
                "userId='" + userId + '\'' +
                ", grantType='" + grantType + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", tokenExpiresIn=" + tokenExpiresIn +
                '}';
    }
}
