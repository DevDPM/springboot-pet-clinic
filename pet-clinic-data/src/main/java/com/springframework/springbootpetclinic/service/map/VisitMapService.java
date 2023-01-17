package com.springframework.springbootpetclinic.service.map;

import com.springframework.springbootpetclinic.model.Visit;
import com.springframework.springbootpetclinic.repository.VisitService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class VisitMapService extends AbstractMapService<Visit, Long> implements com.springframework.springbootpetclinic.service.VisitService {

    VisitService visitService;

    public VisitMapService(VisitService visitService) {
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
