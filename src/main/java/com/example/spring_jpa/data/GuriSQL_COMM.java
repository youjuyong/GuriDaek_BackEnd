package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_COMM {
    public List<Map<String, Object>> horseRideLimitType ();
}

