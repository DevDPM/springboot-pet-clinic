package com.springframework.springbootpetclinic.service;

import com.springframework.springbootpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);

}
