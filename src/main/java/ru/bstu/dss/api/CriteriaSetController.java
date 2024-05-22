package ru.bstu.dss.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bstu.dss.model.CriteriaSet;
import ru.bstu.dss.repository.CriteriaSetRepository;

import java.util.List;

@RestController
@RequestMapping("/api/criteriaSet")
@RequiredArgsConstructor
public class CriteriaSetController {
    private final CriteriaSetRepository criteriaSetRepository;

    @PostMapping
    public ResponseEntity<CriteriaSet> createCriteriaSet(@RequestBody CriteriaSet criteriaSet) {
        return ResponseEntity.ok(criteriaSetRepository.save(criteriaSet));
    }

    @GetMapping
    public ResponseEntity<List<CriteriaSet>> getAllCriteriaSets() {
        return ResponseEntity.ok(criteriaSetRepository.findAll());
    }
}
