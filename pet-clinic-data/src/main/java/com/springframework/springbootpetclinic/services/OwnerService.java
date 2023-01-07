package com.springframework.springbootpetclinic.services;

import com.springframework.springbootpetclinic.model.Owner;

public interface OwnerService extends CrudService<Owner, Long> {
    Owner findByLastName(String lastName);

}
