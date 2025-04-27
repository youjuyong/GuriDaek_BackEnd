package com.example.spring_jpa.api.v1.repository;

import com.example.spring_jpa.object.TbCommCd;
import com.example.spring_jpa.object.TbUser;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TbUserRepo extends CrudRepository<TbUser, Long> {
    TbUser findById(String id);
}
