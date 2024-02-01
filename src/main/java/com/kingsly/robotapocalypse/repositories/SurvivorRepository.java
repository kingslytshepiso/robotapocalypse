package com.kingsly.robotapocalypse.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.kingsly.robotapocalypse.models.Survivor;

@Repository
public interface SurvivorRepository extends JpaRepository<Survivor, Long> {
}
