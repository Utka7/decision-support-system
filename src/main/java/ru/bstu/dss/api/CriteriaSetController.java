package ru.bstu.dss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bstu.dss.model.CriteriaSet;
import ru.bstu.dss.service.CriteriaSetService;

import java.util.List;

@RestController
@RequestMapping("/api/criteria-sets")
public class CriteriaSetController {

    @Autowired
    private CriteriaSetService criteriaSetService;

    @GetMapping
    public List<CriteriaSet> getAllCriteriaSets() {
        return criteriaSetService.getAllCriteriaSets();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriteriaSet> getCriteriaSetById(@PathVariable Long id) {
        return criteriaSetService.getCriteriaSetById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public CriteriaSet createCriteriaSet(@RequestBody CriteriaSet criteriaSet) {
        return criteriaSetService.saveCriteriaSet(criteriaSet);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriteriaSet> updateCriteriaSet(@PathVariable Long id, @RequestBody CriteriaSet criteriaSet) {
        return criteriaSetService.updateCriteriaSet(id, criteriaSet)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriteriaSet(@PathVariable Long id) {
        if (criteriaSetService.deleteCriteriaSet(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
