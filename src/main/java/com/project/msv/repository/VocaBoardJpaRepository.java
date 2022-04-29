package com.project.msv.repository;


import com.project.msv.domain.VocaBoard;

import com.project.msv.domain.voca.QVoca;
import com.project.msv.domain.voca.QVocaWord;
import com.project.msv.dto.*;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import java.util.List;

import static com.project.msv.domain.QMember.*;
import static com.project.msv.domain.QVocaBoard.*;
import static com.project.msv.domain.voca.QVoca.*;
import static com.project.msv.domain.voca.QVocaWord.*;
import static org.springframework.util.StringUtils.*;

@Repository
@RequiredArgsConstructor
public class VocaBoardJpaRepository {

    private final EntityManager em;
    private final JPAQueryFactory queryFactory;

    public List<VocaWordDto> copyWord(Long id) {
        return queryFactory
                .select(new QVocaWordDto(vocaWord.word1, vocaWord.word2, vocaWord.word3, vocaWord.word4))
                .from(vocaWord)
                .join(vocaWord.voca, voca)
                .join(voca.vocaBoard, vocaBoard)
                .where(vocaBoard.id.eq(id))
                .fetch();
    }

    public VocaDto copyVocaname(Long id) {
        return queryFactory
                .select(new QVocaDto(voca.vocaname, voca.country, voca.word1name, voca.word2name, voca.word3name, voca.word4name))
                .from(voca)
                .join(voca.vocaBoard, vocaBoard)
                .where(vocaBoard.id.eq(id))
                .fetchOne();
    }


    public List<VocaBoard> findByMemberid(Long id) {
        return queryFactory
                .select(vocaBoard)
                .from(vocaBoard)
                .join(vocaBoard.voca, voca)
                .join(voca.member, member)
                .where(member.id.eq(id))
                .fetch();
    }

    public Page<VocaBoard> findBytitle(VocaBoardSearchDto vocaBoardSearchDto, Pageable pageable) {
        QueryResults<VocaBoard> vocaBoardQueryResults = queryFactory
                .selectFrom(vocaBoard)
                .where(titleEq(vocaBoardSearchDto.getTitle()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<VocaBoard> results = vocaBoardQueryResults.getResults();
        long total = vocaBoardQueryResults.getTotal();

        return new PageImpl<>(results, pageable, total);
    }

    public BooleanExpression titleEq(String title) {
        return hasText(title) ? vocaBoard.title.contains(title) : null;
    }
}
