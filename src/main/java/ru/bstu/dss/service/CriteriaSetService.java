package ru.bstu.dss.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.bstu.dss.model.CriteriaSet;
import ru.bstu.dss.repository.CriteriaSetRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CriteriaSetService {

    @Autowired
    private CriteriaSetRepository criteriaSetRepository;

    public List<CriteriaSet> getAllCriteriaSets() {
        return criteriaSetRepository.findAll();
    }

    public Optional<CriteriaSet> getCriteriaSetById(Long id) {
        return criteriaSetRepository.findById(id);
    }

    public CriteriaSet saveCriteriaSet(CriteriaSet criteriaSet) {
        return criteriaSetRepository.save(criteriaSet);
    }

    public Optional<CriteriaSet> updateCriteriaSet(Long id, CriteriaSet criteriaSet) {
        if (criteriaSetRepository.existsById(id)) {
            criteriaSet.setId(id);
            return Optional.of(criteriaSetRepository.save(criteriaSet));
        }
        return Optional.empty();
    }

    public boolean deleteCriteriaSet(Long id) {
        if (criteriaSetRepository.existsById(id)) {
            criteriaSetRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
