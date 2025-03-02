package com.example.spring_jpa.data;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface GuriSQL_STAT {
    //양이 전쟁 통계
    public List<Map<String, Object>> yangiRankList ();
    // 마을 ID 조회
    public int yangVillageList(String name);
    // 양이 전공 마을별 업로드
    public int insertYangKillsVillage(Map map);
     // 마을별 주민수 업로드
    public void insertVillageHumanUpload(Map map);
    // 마을별 주민수 리스트
    public List<Map<String, Object>> selectVillageHumanCountList();
    // 장인 업로드
    public void insertCraftsManList(Map map);
    // 마을 주민수 통계
    public List<Map<String, Object>> staticsVillageHumanCnt (Map map);
    // 검색 조건 달 주민수 통계
    public List<Map<String, Object>> staticsCurrentHumanCnt (Map map);

}

