package com.project.msv.repository;

import com.project.msv.domain.voca.Voca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.ModelAttribute;

@Repository
public interface VocaRepository extends JpaRepository<Voca, Long> {

    @Query("select v from Voca v where v.id = :id")
    Voca findById2(@Param("id") Long id);
}
