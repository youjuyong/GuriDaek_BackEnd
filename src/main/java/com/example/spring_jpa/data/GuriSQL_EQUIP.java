package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_EQUIP {
    // 장비 대여 목록
    public List<Map<String, Object>> equipList ();

}

