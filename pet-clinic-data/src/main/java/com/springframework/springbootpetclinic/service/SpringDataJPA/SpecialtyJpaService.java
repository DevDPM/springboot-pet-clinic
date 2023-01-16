package com.springframework.springbootpetclinic.service.SpringDataJPA;

import com.springframework.springbootpetclinic.model.Specialty;
import com.springframework.springbootpetclinic.repository.SpecialtyRepository;
import com.springframework.springbootpetclinic.service.SpecialtyService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class SpecialtyJpaService implements SpecialtyService {

    SpecialtyRepository specialtyRepository;

    public SpecialtyJpaService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    @Override
    public Set<Specialty> findAll() {
        Set<Specialty> specialties = new HashSet<>();
        specialtyRepository.findAll().forEach(specialty -> {
            specialties.add(specialty);
        });
        return specialties;
    }

    @Override
    public Specialty findById(Long id) {
        return specialtyRepository.findById(id).isPresent() ? specialtyRepository.findById(id).get() : null;
    }

    @Override
    public Specialty save(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public void delete(Specialty specialty) {
        specialtyRepository.delete(specialty);
    }

    @Override
    public void deleteById(Long id) {
        specialtyRepository.deleteById(id);
    }
}
