package com.example.spring_jpa.data;

import com.example.spring_jpa.object.Member;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Mapper
public interface GuriSQL_USER {
    public Optional<Member> getUserInfo(String user_id);
    Member getUserInfo2(String user_id);
    List<Map<String, Object>> userMiddleCheck(Map map);

    int insertUserInfo (Map map);
    int updateUserInfo (Map map);
    int updatePassWordInfo (Map map);

    int insertUserLoginHistory (String user_id);
}

