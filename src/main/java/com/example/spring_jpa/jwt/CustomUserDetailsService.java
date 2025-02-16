package com.example.spring_jpa.jwt;

import com.example.spring_jpa.data.GuriSQL_USER;
import com.example.spring_jpa.object.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final GuriSQL_USER guriSQLUser;

    @Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> userInfo = guriSQLUser.getUserInfo(username);
        return userInfo.map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(username + " -> 등록되지 않은 사용자입니다."));
    }

    private UserDetails createUserDetails(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getAuthority().toString());

        return new User(
                String.valueOf(member.getUserId()),
                member.getUserPwd(),
                Collections.singleton(grantedAuthority)
        );
    }
}
