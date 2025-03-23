package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_HERO {
    // 소환영웅 리스트
    public List<Map<String, Object>> heroList (Map map);
    // 소환영웅 리뷰 리스트
    List<Map<String, Object>> getHeroReviewList (Map map);
    // 소환영우 리뷰 등록
    int putHeroRevieInfo (Map map);
    int deleteHeroRevieInfo (Map map);
}

