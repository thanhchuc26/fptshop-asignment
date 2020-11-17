package com.softech.fpt.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softech.fpt.models.Depart;

@Repository
public interface DepartRepository extends CrudRepository<Depart, Integer> {
	public List<Depart> findByNameLikeOrderByName(String name);
}
