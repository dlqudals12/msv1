package com.project.msv.repository;

import com.project.msv.domain.VocaBoard;
import com.project.msv.domain.voca.Voca;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocaBoardRepository extends JpaRepository<VocaBoard, Long> {
    @Query(value = "select v.point from VocaBoard v where v.id = :id")
    Integer findPointById(@Param("id") Long id);

    @Query(value = "select b from VocaBoard v join fetch Voca b join fetch VocaBoard c where v.id = :id")
    List<Voca> findByVocaId(@Param("id") Long id);


    @Query(value = "select v from VocaBoard v where v.title like '%:title%'")
    Page<VocaBoard> findBytitle(@Param("String title") String title, PageRequest pageRequest);
}
