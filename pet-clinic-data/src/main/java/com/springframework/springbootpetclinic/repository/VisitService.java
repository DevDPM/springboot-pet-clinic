package com.springframework.springbootpetclinic.repository;

import com.springframework.springbootpetclinic.model.Visit;
import org.springframework.data.repository.CrudRepository;

public interface VisitService extends CrudRepository<Visit, Long> {
}
