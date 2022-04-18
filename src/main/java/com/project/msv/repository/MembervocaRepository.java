package com.project.msv.repository;


import com.project.msv.domain.Membervoca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembervocaRepository extends JpaRepository<Membervoca, Long> {

    @Query(value = "select v from Membervoca v join fetch v.member m where m.id = :id")
    List<Membervoca> findByMemberid(@Param("id") Long id);

}
