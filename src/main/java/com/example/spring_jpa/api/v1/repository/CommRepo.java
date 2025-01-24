package com.example.spring_jpa.api.v1.repository;

import com.example.spring_jpa.object.Bit;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommRepo extends CrudRepository<Bit, Long> {


}