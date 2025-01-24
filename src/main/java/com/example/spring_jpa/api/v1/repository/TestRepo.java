package com.example.spring_jpa.api.v1.repository;

import com.example.spring_jpa.object.Bit;
import com.example.spring_jpa.object.BitStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestRepo extends CrudRepository<Bit, Long> {

	List<Bit> findBitsByOrderByIdAsc();

}