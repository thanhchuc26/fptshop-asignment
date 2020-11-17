package com.softech.fpt.services;

import java.util.List;
import java.util.Optional;

import com.softech.fpt.models.Depart;

public interface DepartService {

	void deleteAll();

	void deleteAll(List<Depart> entities);

	void delete(Depart entity);

	void deleteById(Integer id);

	long count();

	List<Depart> findAllById(List<Integer> ids);

	Iterable<Depart> findAll();

	List<Depart> findByNameLikeOrderByName(String name);

	boolean existsById(Integer id);

	Optional<Depart> findById(Integer id);

	List<Depart> saveAll(List<Depart> entities);

	Depart save(Depart entity);


}
