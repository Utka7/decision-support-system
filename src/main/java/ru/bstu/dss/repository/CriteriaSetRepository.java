package ru.bstu.dss.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bstu.dss.model.CriteriaSet;

@Repository
public interface CriteriaSetRepository extends JpaRepository<CriteriaSet, Long> {
}
