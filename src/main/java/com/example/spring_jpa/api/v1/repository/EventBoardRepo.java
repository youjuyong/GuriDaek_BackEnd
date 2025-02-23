package com.example.spring_jpa.api.v1.repository;

import com.example.spring_jpa.object.EventBoard;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EventBoardRepo extends CrudRepository<EventBoard, Long> {

    EventBoard findById(String id);

}
