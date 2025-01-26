package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_HORSE {
    // 탈것 리스트
    public List<Map<String, Object>> horseList (Map map);


}

