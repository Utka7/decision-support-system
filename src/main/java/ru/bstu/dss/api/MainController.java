package ru.bstu.dss.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.bstu.dss.dto.DisplacedIdealDto;
import ru.bstu.dss.method.DisplacedIdealMethodSolver;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Log4j2
public class MainController {

    private final DisplacedIdealMethodSolver displacedIdealMethodSolver;

    @GetMapping("/displacedIdealMethod")
    public String displacedIdealMethod(@RequestBody DisplacedIdealDto data) {
        return displacedIdealMethodSolver.run(data);
    }

}
