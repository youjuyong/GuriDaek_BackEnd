package com.example.spring_jpa.api.v1.repository;

import com.example.spring_jpa.object.EventBoard;
import com.example.spring_jpa.object.TbMainBord;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MainBoardRepo extends CrudRepository<TbMainBord, Long> {

    List<TbMainBord> findAllByUseYnOrderByRegDtDesc(String useYn);
    TbMainBord findById(String id);

}
