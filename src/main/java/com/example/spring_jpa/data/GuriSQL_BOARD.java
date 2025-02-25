package com.example.spring_jpa.data;

import com.example.spring_jpa.object.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface GuriSQL_BOARD {

    List<Map<String, Object>> getEvnetBoardList();
    List<Map<String, Object>> getEventBoardDateTime(Map map);

    int putEventDayTime (Map map);
    int putEventBoardReviewInfo (Map map);
    int deleteEventBoardRevieInfo (Map map);

    List<Map<String, Object>>  getEventBoardUserCheck(Map map);
    List<Map<String, Object>> getEventBoardDateTimeChart (Map map);
    List<Map<String, Object>> getEventBoardReviewList (Map map);
    List<Map<String, Object>> getEventBoardContentList (Map map);

    List<Map<String, Object>> getEventBoardContentImgList (Map map);
    Map<String, Object> fileDownload (Map map);

}

