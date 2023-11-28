package com.project.msv.repository;

import com.project.msv.domain.QTradeVoca;
import com.project.msv.domain.QVocaBoard;
import com.project.msv.domain.VocaBoard;
import com.project.msv.dto.request.vocaBoard.VocaBoardDetailDto;
import com.project.msv.dto.response.vocaBoard.VocaBoardListRes;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class VocaBoardRepositoryCustomImpl extends QuerydslRepositorySupport implements VocaBoardRepositoryCustom{

    private QVocaBoard vocaBoard = QVocaBoard.vocaBoard;
    private QTradeVoca tradeVoca = QTradeVoca.tradeVoca;
    private JPAQueryFactory jpaQueryFactory;

    public VocaBoardRepositoryCustomImpl(JPAQueryFactory jpaQueryFactory) {
        super(VocaBoard.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }


    @Override
    public PageImpl<VocaBoardListRes> findVocaBoardList(String title, Long userId,Pageable pageable) {
        List<VocaBoard> vocaBoardList = jpaQueryFactory
                .select(vocaBoard)
                .from(vocaBoard)
                .join(vocaBoard.voca)
                .fetchJoin()
                .where(vocaBoardFilter(title, userId))
                .orderBy(vocaBoard.regDt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();



        return new PageImpl<>(vocaBoardList.stream().map(VocaBoard::toDto).toList(), pageable, countTotal(title, userId));
    }

    @Override
    public VocaBoardDetailDto findVocaDetail(Long id, Long userId) {
        return jpaQueryFactory
                .select(Projections.bean(
                  VocaBoardDetailDto.class,
                        vocaBoard.id,
                        vocaBoard.title,
                        vocaBoard.board,
                        vocaBoard.count,
                        vocaBoard.point,
                        vocaBoard.buycount,
                        vocaBoard.voca,
                        vocaBoard.voca.user.id.eq(userId).as("own"),
                        haveVoca(vocaBoard.voca.id, userId)
                ))
                .from(vocaBoard)
                .join(vocaBoard.voca)
                .where(vocaBoard.id.eq(id))
                .fetchOne();
    }

    private long countTotal(String title, Long userId) {
        return jpaQueryFactory
                .select(vocaBoard)
                .from(vocaBoard)
                .join(vocaBoard.voca)
                .fetchJoin()
                .where(vocaBoardFilter(title, userId))
                .fetch().size();
    }

    private BooleanBuilder vocaBoardFilter(String title, Long userId) {
        BooleanBuilder builder = new BooleanBuilder();

        if(!title.isEmpty()) {
            builder.and(vocaBoard.title.contains(title));
        }

        if(userId != null) {
            builder.and(vocaBoard.voca.user.id.eq(userId));
        }

        return builder;
    }

    private BooleanExpression haveVoca(NumberPath<Long> id, Long userId) {
        return jpaQueryFactory.select(tradeVoca.countDistinct())
                .from(tradeVoca)
                .where(tradeVoca.voca.id.eq(id), tradeVoca.user.id.eq(userId))
                .gt(0L).as("haveVoca");
    }
}
