package ru.bstu.dss.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bstu.dss.model.Alternative;
import ru.bstu.dss.repository.AlternativeRepository;

import java.util.List;

@RestController
@RequestMapping("/api/alternative")
@RequiredArgsConstructor
public class AlternativeController {
    private final AlternativeRepository alternativeRepository;

    @PostMapping
    public ResponseEntity<Alternative> createAlternative(@RequestBody Alternative alternative) {
        return ResponseEntity.ok(alternativeRepository.save(alternative));
    }

    @GetMapping
    public ResponseEntity<List<Alternative>> getAllAlternatives() {
        return ResponseEntity.ok(alternativeRepository.findAll());
    }
}
