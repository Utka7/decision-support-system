package ru.bstu.dss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bstu.dss.model.Alternative;
import ru.bstu.dss.repository.AlternativeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class AlternativeService {

    @Autowired
    private AlternativeRepository alternativeRepository;

    public List<Alternative> getAllAlternatives() {
        return alternativeRepository.findAll();
    }

    public Optional<Alternative> getAlternativeById(Long id) {
        return alternativeRepository.findById(id);
    }

    public Alternative saveAlternative(Alternative alternative) {
        return alternativeRepository.save(alternative);
    }

    public Optional<Alternative> updateAlternative(Long id, Alternative alternative) {
        if (alternativeRepository.existsById(id)) {
            alternative.setId(id);
            return Optional.of(alternativeRepository.save(alternative));
        }
        return Optional.empty();
    }

    public boolean deleteAlternative(Long id) {
        if (alternativeRepository.existsById(id)) {
            alternativeRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
