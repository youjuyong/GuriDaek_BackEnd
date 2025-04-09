package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_EQUIP {
    // 장비 대여 목록
    public List<Map<String, Object>> equipList (Map<String, Object> map);
     // 장비 대여 상세 목록
    public List<Map<String, Object>> equipDetlList (Map<String, Object> map);

}

