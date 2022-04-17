package com.project.msv.repository;

import com.project.msv.domain.QMember;
import com.project.msv.domain.voca.QVoca;
import com.project.msv.domain.voca.Voca;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.msv.domain.QMember.*;
import static com.project.msv.domain.voca.QVoca.*;

@Repository
@RequiredArgsConstructor
public class VocaJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<Voca> findVocaByMember(Long id) {
        return queryFactory
                .select(voca)
                .from(voca)
                .leftJoin(voca.member, member)
                .fetchJoin()
                .where(member.id.eq(id))
                .fetch();

    }
}
