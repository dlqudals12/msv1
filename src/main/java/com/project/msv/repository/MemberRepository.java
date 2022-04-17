package com.project.msv.repository;

import com.project.msv.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findOpByUsername(String username);

    @Query(value = "select m.point from Member m where m.id = :id")
    Integer findPointById(@Param("id") Long id);




}
