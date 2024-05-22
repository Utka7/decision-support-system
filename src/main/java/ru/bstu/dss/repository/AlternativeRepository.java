package ru.bstu.dss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.bstu.dss.model.Alternative;

public interface AlternativeRepository extends JpaRepository<Alternative, Long> {
}
