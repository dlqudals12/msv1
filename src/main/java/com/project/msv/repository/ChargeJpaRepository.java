package com.project.msv.repository;

import com.project.msv.domain.Charge;
import com.project.msv.domain.Member;

import com.project.msv.domain.Status;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.msv.domain.QCharge.*;

@Repository
@RequiredArgsConstructor
public class ChargeJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<Charge> findByMember(Member member) {
        return queryFactory
                .selectFrom(charge)
                .where(charge.member.eq(member))
                .fetch();
    }

    public List<Charge> findBySta(Status status) {
        return queryFactory
                .selectFrom(charge)
                .where(charge.status.eq(status))
                .fetch();
    }
}
