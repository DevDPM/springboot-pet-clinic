package com.springframework.springbootpetclinic.service.SpringDataJPA;

import com.springframework.springbootpetclinic.model.Visit;
import com.springframework.springbootpetclinic.repository.VisitService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("springdatajpa")
public class VisitJpaService implements com.springframework.springbootpetclinic.service.VisitService {

    VisitService visitService;

    public VisitJpaService(VisitService visitService) {
        this.visitService = visitService;
    }

    @Override
    public Set<Visit> findAll() {
        Set<Visit> visits = new HashSet<>();
        visitService.findAll().forEach(visit -> {
            visits.add(visit);
        });
        return visits;
    }

    @Override
    public Visit findById(Long id) {
        return visitService.findById(id).isPresent() ? visitService.findById(id).get() : null;
    }

    @Override
    public Visit save(Visit visit) {
        return visitService.save(visit);
    }

    @Override
    public void delete(Visit visit) {
        visitService.delete(visit);
    }

    @Override
    public void deleteById(Long id) {
        visitService.deleteById(id);
    }
}
