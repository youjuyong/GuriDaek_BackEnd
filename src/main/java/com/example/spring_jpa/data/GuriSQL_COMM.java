package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_COMM {
    // 마굿간 헤더 탈것 조건
    public List<Map<String, Object>> horseRideLimitType ();
    // 마굿간 헤더 탈것 버프 타입
    public List<Map<String, Object>> horseBurpType ();
    // 마굿간 탈것 수명 타입
    public List<Map<String, Object>> horseLifeType ();
    // 마굿간 버프타입 타입
    public List<Map<String, Object>> horseHouseBurpType ();
    // 마굿간 탈것 버프 비율
    public List<Map<String, Object>> horseHouseBurpPercent ();


}

