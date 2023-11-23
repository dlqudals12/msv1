package com.project.msv.repository;

import com.project.msv.domain.VocaBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VocaBoardRepository extends JpaRepository<VocaBoard, Long>, VocaBoardRepositoryCustom {

    long countVocaBoardByVocaIdOrTitle(Long vocaId, String title);
}
