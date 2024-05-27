package ru.bstu.dss.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bstu.dss.dto.inputDataDto;
import ru.bstu.dss.method.DisplacedIdealMethodSolver;
import ru.bstu.dss.method.PermutationMethodSolver;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class MainController {

    private final DisplacedIdealMethodSolver displacedIdealMethodSolver;
    private final PermutationMethodSolver permutationMethodSolver;

    @GetMapping("/displacedIdealMethod")
    public String displacedIdealMethod(@RequestBody inputDataDto data) {
        return displacedIdealMethodSolver.run(data);
    }

    @GetMapping("/permutationMethod")
    public String permutationMethod(@RequestBody inputDataDto data) {
        return permutationMethodSolver.run(data);
    }

}
