package com.project.msv.repository;

import com.project.msv.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findOpByUsername(String username);

    Member findByUsername(String username);

    @Query(value = "select m.point from Member m where m.id = :id")
    Integer findPointById(@Param("id") Long id);

    @Query(value = "select m from Member m join m.voca v join v.vocaBoard c where c.id = :id")
    Optional<Member> findByVocaid(@Param("id") Long id);

    @Query(value = "select m from Member m where m.email = :email")
    Optional<Member> findByEmail(@Param("email") String email);
}
