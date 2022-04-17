package com.project.msv.repository;

import com.project.msv.domain.voca.VocaWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VocaWordRepositoy extends JpaRepository<VocaWord, Long> {

    @Query(value = "select v from VocaWord v where v.id = :id")
    VocaWord findByVocaid(@Param("id") Long id);

    @Query(value = "select v from VocaWord v  where v.id = :id")
    List<VocaWord> findByVocawordId(@Param("id") Long id);

}
