package com.softech.fpt.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.softech.fpt.models.Staff;

@Repository
public interface StaffRepository extends CrudRepository<Staff, Long>{
	
}
