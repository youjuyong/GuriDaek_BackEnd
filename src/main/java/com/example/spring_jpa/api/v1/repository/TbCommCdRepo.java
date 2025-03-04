package com.example.spring_jpa.api.v1.repository;

import com.example.spring_jpa.object.EventBoard;
import com.example.spring_jpa.object.TbCommCd;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TbCommCdRepo extends CrudRepository<TbCommCd, Long> {
    List<TbCommCd> findById(String id);
}
