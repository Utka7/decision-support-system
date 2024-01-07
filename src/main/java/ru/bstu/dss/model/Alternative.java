package ru.bstu.dss.model;

import lombok.Data;

import java.util.List;

@Data
public class Alternative {
    String name;
    List<Double> criteriaValuesList;
}
