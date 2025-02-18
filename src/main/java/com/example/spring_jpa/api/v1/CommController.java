package com.example.spring_jpa.api.v1;

import com.example.spring_jpa.api.BackendApi;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/comm")
public class CommController implements BackendApi {

    @Override
    @PostConstruct
    public void assertConnection() {
    }

    @Override
    @PostConstruct
    public void loadData() {
    }



}
