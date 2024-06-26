package ru.bstu.dss.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.bstu.dss.model.Alternative;
import ru.bstu.dss.model.CriteriaSet;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class inputDataDto {
    private CriteriaSet criteriaSet;
    private List<Alternative> alternatives;

}
