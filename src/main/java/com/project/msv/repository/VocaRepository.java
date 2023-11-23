package com.project.msv.repository;

import com.project.msv.domain.Voca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VocaRepository extends JpaRepository<Voca, Long>, VocaRepositoryCustom {

    int countVocaByVocaName(String name);

    @Override
    @Query(value = "select v from Voca v join fetch v.user u where v.id = :aLong")
    Optional<Voca> findById(Long aLong);
}
