package com.springframework.springbootpetclinic.service.SpringDataJPA;

import com.springframework.springbootpetclinic.model.Vet;
import com.springframework.springbootpetclinic.repository.VetRepository;
import com.springframework.springbootpetclinic.service.VetService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VetJpaService implements VetService {

    private final VetRepository vetRepository;

    public VetJpaService(VetRepository vetRepository) {
        this.vetRepository = vetRepository;
    }

    @Override
    public Set<Vet> findAll() {

        Set<Vet> vets = new HashSet<>();
        vetRepository.findAll().forEach(vet -> {
            vets.add(vet);
        });

        return vets;
    }

    @Override
    public Vet findById(Long id) {
        return vetRepository.findById(id).isPresent() ? vetRepository.findById(id).get() : null;
    }

    @Override
    public Vet save(Vet vet) {
        return vetRepository.save(vet);
    }

    @Override
    public void delete(Vet vet) {
        vetRepository.delete(vet);
    }

    @Override
    public void deleteById(Long id) {
        vetRepository.deleteById(id);
    }
}
