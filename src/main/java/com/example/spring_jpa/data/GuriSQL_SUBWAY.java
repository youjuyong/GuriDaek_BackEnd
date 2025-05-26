package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_SUBWAY {
    public int insertStationInfo ( Map map );

    List<Map<String, Object>> getSubWayInfo (Map map);

    Map<String, Object> getSubWayDirInfo(String fcode);
}

