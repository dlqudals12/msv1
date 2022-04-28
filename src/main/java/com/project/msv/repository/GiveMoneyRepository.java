package com.project.msv.repository;

import com.project.msv.domain.GiveMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GiveMoneyRepository extends JpaRepository<GiveMoney, Long> {
}
