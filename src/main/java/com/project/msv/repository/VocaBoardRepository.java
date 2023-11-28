package com.project.msv.repository;

import com.project.msv.domain.VocaBoard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VocaBoardRepository extends JpaRepository<VocaBoard, Long>, VocaBoardRepositoryCustom {

    long countVocaBoardByVocaIdOrTitle(Long vocaId, String title);

    @Override
    @Query(value = "select vb from VocaBoard vb join fetch vb.voca v where vb.id = :id")
    Optional<VocaBoard> findById(Long id);
}
