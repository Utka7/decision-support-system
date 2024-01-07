package ru.bstu.dss.model;

import lombok.Data;

import java.util.List;

@Data
public class CriteriaSet {
    List<String> names;
    List<Boolean> usefulness;
    List<Double> importance;
}
