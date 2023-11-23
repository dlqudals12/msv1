package com.project.msv.repository;

import com.project.msv.domain.QTradeVoca;
import com.project.msv.domain.QVoca;
import com.project.msv.dto.response.voca.VocaListRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class VocaRepositoryCustomImpl implements VocaRepositoryCustom {

    private QVoca voca = QVoca.voca;
    private QTradeVoca tradeVoca = QTradeVoca.tradeVoca;
    private final JPAQueryFactory jpaQueryFactory;

    public VocaRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public PageImpl<VocaListRes> findVocaList(String vocaName, Long userId, Pageable pageable) {
        List<VocaListRes> vocaListRes = jpaQueryFactory
                .select(Projections.bean(VocaListRes.class,
                        voca.id.as("vocaId"),
                        voca.vocaName,
                        voca.country,
                        voca.column1,
                        voca.column2,
                        voca.column3,
                        voca.column4,
                        voca.user.id.eq(tradeVoca.user.id).as("own")
                ))
                .from(tradeVoca)
                .leftJoin(tradeVoca.voca, voca)
                .where(tradeVoca.user.id.eq(userId), vocaNameContains(vocaName))
                .orderBy(tradeVoca.regDt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        return new PageImpl<>(vocaListRes, pageable, countVocaList(vocaName, userId));
    }

    private Long countVocaList(String vocaName, Long userId) {
        return jpaQueryFactory
                .select(tradeVoca)
                .from(tradeVoca)
                .join(tradeVoca.voca)
                .fetchJoin()
                .where(tradeVoca.user.id.eq(userId), vocaNameContains(vocaName))
                .stream().count();
    }

    private BooleanBuilder vocaNameContains(String vocaName) {
        BooleanBuilder builder = new BooleanBuilder();

        if (vocaName != null && !vocaName.isEmpty()) builder.and(tradeVoca.voca.vocaName.contains(vocaName));

        return builder;
    }
}
