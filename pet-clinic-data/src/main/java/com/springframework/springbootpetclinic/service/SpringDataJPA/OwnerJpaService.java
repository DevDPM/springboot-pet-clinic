package com.springframework.springbootpetclinic.service.SpringDataJPA;

import com.springframework.springbootpetclinic.model.Owner;
import com.springframework.springbootpetclinic.repository.OwnerRepository;
import com.springframework.springbootpetclinic.service.OwnerService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class OwnerJpaService implements OwnerService {

    private final OwnerRepository ownerRepository;

    public OwnerJpaService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    @Override
    public Set<Owner> findAll() {
        Set<Owner> owners = new HashSet<>();
        ownerRepository.findAll().forEach(owner -> {
            owners.add(owner);
        });
        return owners.size() > 0 ? owners : null;
    }

    @Override
    public Owner findById(Long id) {

        return ownerRepository.findById(id).isPresent() ? ownerRepository.findById(id).get() : null;
    }

    @Override
    public Owner save(Owner owner) {
        return ownerRepository.save(owner);
    }

    @Override
    public void delete(Owner owner) {
        ownerRepository.delete(owner);
    }

    @Override
    public void deleteById(Long id) {
        ownerRepository.deleteById(id);
    }

    @Override
    public Set<Owner> findByLastName(String lastName) {
        Set<Owner> ownerListByLastName = new HashSet<>();
        ownerRepository.findAll().forEach(owner -> {
            if (owner.getLastName().equals(lastName))
                ownerListByLastName.add(owner);
        });
        return ownerListByLastName.size() > 0 ? ownerListByLastName : null;
    }
}
