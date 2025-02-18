package com.example.spring_jpa.jwt;


import com.example.spring_jpa.object.Authority;
import com.example.spring_jpa.object.Member;
import lombok.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    private String userId;
    private String userPwd;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .userId(userId)
                .userPwd(passwordEncoder.encode(userPwd))
                .authority(Authority.ROLE_ADMIN)
                .build();
    }

    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(userId, userPwd);
    }

    @Override
    public String toString() {
        return "MemberRequestDto{" +
                "userId='" + userId + '\'' +
                ", userPwd='" + userPwd + '\'' +
                '}';
    }
}
