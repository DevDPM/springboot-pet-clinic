package com.springframework.springbootpetclinic.service;

import com.springframework.springbootpetclinic.model.Owner;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface OwnerService extends CrudService<Owner, Long> {

    Set<Owner> findByLastName(String lastName);

    List<Owner> findAllByLastNameLike(String lastName);
}
