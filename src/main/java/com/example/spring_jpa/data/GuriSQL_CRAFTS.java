package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_CRAFTS {
    // 장인 제조 타입
    public List<Map<String, Object>> craftsManMakeType ();
    // 장인 마을 조회
    public List<Map<String, Object>> villageName ();
    // 장인 목록 조회
    public List<Map<String, Object>> getCraftsManList (Map map);

}

