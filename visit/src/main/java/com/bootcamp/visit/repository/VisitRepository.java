package com.bootcamp.visit.repository;

import com.bootcamp.visit.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, String>{
}
