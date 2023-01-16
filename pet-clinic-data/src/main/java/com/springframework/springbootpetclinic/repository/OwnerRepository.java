package com.springframework.springbootpetclinic.repository;

import com.springframework.springbootpetclinic.model.Owner;
import org.springframework.data.repository.CrudRepository;

public interface OwnerRepository extends CrudRepository<Owner, Long> {
}
