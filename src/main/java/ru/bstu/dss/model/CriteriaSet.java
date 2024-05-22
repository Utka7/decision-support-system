package ru.bstu.dss.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.List;

@Data
@Entity
public class CriteriaSet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "criteriaSet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alternative> alternatives;

    @ElementCollection
    private List<String> names;

    @ElementCollection
    private List<Boolean> usefulness;

    @ElementCollection
    private List<Double> importance;
}
