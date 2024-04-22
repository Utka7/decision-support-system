package ru.bstu.dss.model;

import lombok.Data;

import java.util.List;

@Data
public class Alternative {
    private String name;
    private List<Double> criteriaValuesList;
}
