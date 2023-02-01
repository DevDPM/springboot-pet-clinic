package com.springframework.springbootpetclinic.repository;

import com.springframework.springbootpetclinic.model.Owner;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface OwnerRepository extends CrudRepository<Owner, Long> {

    Set<Owner> findByLastName(String lastName);


    List<Owner> findAllByLastNameLike(String lastName);
}
