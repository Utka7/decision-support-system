package ru.bstu.dss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bstu.dss.model.Alternative;
import ru.bstu.dss.service.AlternativeService;

import java.util.List;

@RestController
@RequestMapping("/api/alternatives")
public class AlternativeController {

    @Autowired
    private AlternativeService alternativeService;

    @GetMapping
    public List<Alternative> getAllAlternatives() {
        return alternativeService.getAllAlternatives();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alternative> getAlternativeById(@PathVariable Long id) {
        return alternativeService.getAlternativeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Alternative createAlternative(@RequestBody Alternative alternative) {
        return alternativeService.saveAlternative(alternative);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alternative> updateAlternative(@PathVariable Long id, @RequestBody Alternative alternative) {
        return alternativeService.updateAlternative(id, alternative)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlternative(@PathVariable Long id) {
        if (alternativeService.deleteAlternative(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
