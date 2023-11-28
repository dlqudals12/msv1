package com.project.msv.repository;

import com.project.msv.domain.QTradeVoca;
import com.project.msv.domain.QVoca;
import com.project.msv.domain.QVocaBoard;
import com.project.msv.dto.response.voca.VocaListRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class VocaRepositoryCustomImpl implements VocaRepositoryCustom {

    private QVoca voca = QVoca.voca;
    private QTradeVoca tradeVoca = QTradeVoca.tradeVoca;

    private QVocaBoard vocaBoard = QVocaBoard.vocaBoard;
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
                        voca.user.id.eq(tradeVoca.user.id).as("own"),
                        isSelling()
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

    private BooleanExpression isSelling() {
        return new CaseBuilder().when(
                jpaQueryFactory.select(vocaBoard.count().as("boardCount")).from(vocaBoard).where(vocaBoard.voca.id.eq(voca.id)).gt(0L)
        ).then(true).otherwise(false).as("sell");
    }
}
