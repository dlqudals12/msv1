package com.project.msv.repository;

import com.project.msv.domain.QReceiptPoint;
import com.project.msv.domain.QUser;
import com.project.msv.domain.QVocaBoard;
import com.project.msv.domain.ReceiptPoint;
import com.project.msv.domain.enums.ReceiptType;
import com.project.msv.dto.response.receipt.ReceiptPointListRes;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ReceiptPointRepositoryImpl extends QuerydslRepositorySupport implements ReceiptPointRepositoryCustom{

    private QReceiptPoint receiptPoint = QReceiptPoint.receiptPoint;
    private QUser user = QUser.user;
    private QVocaBoard vocaBoard = QVocaBoard.vocaBoard;
    private JPAQueryFactory jpaQueryFactory;

    public ReceiptPointRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(ReceiptPoint.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PageImpl<ReceiptPointListRes> findReceiptList(Long userId, Pageable pageable) {
        QUser fromUser = new QUser("fromUser");
        QUser toUser = new QUser("toUser");

        List<ReceiptPointListRes> result = jpaQueryFactory
                .select(Projections.bean(
                        ReceiptPointListRes.class,
                        receiptPoint.id,
                        vocaBoard.voca.vocaName.concat(" (").concat(vocaBoard.voca.country).concat( ")").as("vocaName"),
                        fromUser.name.concat(" (").concat(fromUser.loginId).concat(")").as("fromUser"),
                        toUser.name.concat(" (").concat(toUser.loginId).concat(")").as("toUser"),
                        receiptPoint.point,
                        vocaBoard.buycount.as("buyCount"),
                        receiptPoint.regDt,
                        statusCase(fromUser, receiptPoint)
                ))
                .from(receiptPoint)
                .join(fromUser).on(receiptPoint.fromUser.eq(fromUser.loginId))
                .join(toUser).on(receiptPoint.toUser.eq(toUser.loginId))
                .join(vocaBoard).on(receiptPoint.vocaBoardId.eq(vocaBoard.id))
                .join(vocaBoard.voca)
                .where(fromUser.id.eq(userId).or(toUser.id.eq(userId)), receiptPoint.receiptType.eq(ReceiptType.DEALVOCA))
                .orderBy(receiptPoint.regDt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        long count = jpaQueryFactory
                .select(receiptPoint)
                .from(receiptPoint)
                .join(fromUser).on(receiptPoint.fromUser.eq(fromUser.loginId))
                .join(toUser).on(receiptPoint.toUser.eq(toUser.loginId))
                .join(vocaBoard).on(receiptPoint.vocaBoardId.eq(vocaBoard.id))
                .join(vocaBoard.voca)
                .where(fromUser.id.eq(userId).or(toUser.id.eq(userId)), receiptPoint.receiptType.eq(ReceiptType.DEALVOCA))
                .fetchCount();

        return new PageImpl<>(result, pageable,count);
    }

    private StringExpression statusCase(QUser fromUser, QReceiptPoint receiptPoint) {
        return new CaseBuilder()
                .when(fromUser.loginId.eq(receiptPoint.fromUser))
                .then("구매")
                .otherwise("판매")
                .as("status");

    }
}
