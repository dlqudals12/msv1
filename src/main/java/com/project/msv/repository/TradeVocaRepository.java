package com.project.msv.repository;

import com.project.msv.domain.TradeVoca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeVocaRepository extends JpaRepository<TradeVoca, Long> {

    long countByUserIdAndVocaId(Long userId, Long vocaId);
}
