package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_STAT {
    //양이 전쟁 통계
    public List<Map<String, Object>> yangiRankList ();


}

