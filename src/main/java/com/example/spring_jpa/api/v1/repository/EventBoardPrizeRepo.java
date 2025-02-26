package com.example.spring_jpa.api.v1.repository;

import com.example.spring_jpa.object.EventBoardPrize;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventBoardPrizeRepo extends CrudRepository<EventBoardPrize, Long> {

    List<EventBoardPrize> findById(String id);
    List<EventBoardPrize> findByUserIdIsNullAndId(String id);
    List<EventBoardPrize> findByIdAndUserId(String id, String userId);
}
