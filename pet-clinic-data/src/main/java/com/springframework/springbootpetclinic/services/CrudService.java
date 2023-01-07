package com.springframework.springbootpetclinic.services;

import java.util.Set;

public interface CrudService<T, ID> {

    Set<T> fineAll();

    T findById(ID id);

    T save(T object);

    void delete(T object);

    void deleteById(T object);

}
