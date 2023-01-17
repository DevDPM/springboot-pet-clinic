package com.springframework.springbootpetclinic.service.SpringDataJPA;

import com.springframework.springbootpetclinic.model.Visit;
import com.springframework.springbootpetclinic.repository.VisitRepository;
import com.springframework.springbootpetclinic.service.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VisitJpaService implements VisitService {

    VisitRepository visitRepository;

    public VisitJpaService(VisitRepository visitRepository) {
        this.visitRepository = visitRepository;
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits = new HashSet<>();
        visitRepository.findAll().forEach(visit -> {
            visits.add(visit);
        });
        return visits;
    }

    @Override
    public Visit findById(Long id) {
        return visitRepository.findById(id).isPresent() ? visitRepository.findById(id).get() : null;
    }

    @Override
    public Visit save(Visit visit) {
        return visitRepository.save(visit);
    }

    @Override
    public void delete(Visit visit) {
        visitRepository.delete(visit);
    }

    @Override
    public void deleteById(Long id) {
        visitRepository.deleteById(id);
    }
}
