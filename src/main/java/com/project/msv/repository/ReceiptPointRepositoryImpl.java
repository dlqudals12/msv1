package com.project.msv.repository;

import com.project.msv.domain.*;
import com.project.msv.domain.enums.ReceiptType;
import com.project.msv.dto.response.receipt.ReceiptPointListRes;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.sql.JPASQLQuery;
import com.querydsl.sql.SQLTemplates;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

public class ReceiptPointRepositoryImpl extends QuerydslRepositorySupport implements ReceiptPointRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;
    private final SQLTemplates sqlTemplates;

    private final QReceiptPoint receiptPoint = QReceiptPoint.receiptPoint;
    private final QUser user = QUser.user;
    private final QVocaBoard vocaBoard = QVocaBoard.vocaBoard;
    private final QVoca voca = QVoca.voca;
    private final JPAQueryFactory jpaQueryFactory;

    public ReceiptPointRepositoryImpl(JPAQueryFactory jpaQueryFactory, SQLTemplates sqlTemplates) {
        super(ReceiptPoint.class);
        this.jpaQueryFactory = jpaQueryFactory;
        this.sqlTemplates = sqlTemplates;
    }

    @Override
    public PageImpl<ReceiptPointListRes> findReceiptList(Long userId, Pageable pageable) {
        JPASQLQuery<?> sqlQuery = new JPASQLQuery<>(entityManager, sqlTemplates);
        QUser fromUser = new QUser("fromUser");
        QUser toUser = new QUser("toUser");
        StringPath receiptPointAs = Expressions.stringPath("receiptPoint");
        
        List<ReceiptPointListRes> result = sqlQuery.clone()
                .select(Projections.bean(
                        ReceiptPointListRes.class,
                        Expressions.numberPath(Long.class, "id"),
                        Expressions.stringPath(receiptPointAs, "vocaName"),
                        Expressions.stringPath(receiptPointAs, "fromUser"),
                        Expressions.stringPath(receiptPointAs, "toUser"),
                        Expressions.numberPath(Integer.class, "point"),
                        Expressions.numberPath(Integer.class, "buyCount"),
                        Expressions.datePath(LocalDateTime.class, "regDt"),
                        Expressions.enumPath(ReceiptType.class, "receiptType"),
                        Expressions.stringPath(receiptPointAs, "status")
                ))
                .from(
                        sqlQuery.clone().unionAll(
                                JPAExpressions
                                        .select(
                                                receiptPoint.id.as("id"),
                                                Expressions.stringPath(voca, "voca_name").concat(" (").concat(voca.country).concat(")").as("vocaName"),
                                                fromUser.name.concat(" (").concat(Expressions.stringPath(fromUser, "login_id")).concat(")").as("fromUser"),
                                                toUser.name.concat(" (").concat(Expressions.stringPath(toUser, "login_id")).concat(")").as("toUser"),
                                                receiptPoint.point,
                                                Expressions.numberPath(Integer.class, vocaBoard, "buyCount").as("buyCount"),
                                                Expressions.datePath(LocalDateTime.class, receiptPoint, "reg_dt").as("regDt"),
                                                Expressions.enumPath(ReceiptType.class, receiptPoint, "receipt_type").as("receiptType"),
                                                statusCase(fromUser, receiptPoint)
                                        )
                                        .from(receiptPoint)
                                        .join(fromUser).on(receiptPoint.fromUser.eq(fromUser.loginId))
                                        .join(toUser).on(receiptPoint.toUser.eq(toUser.loginId))
                                        .join(vocaBoard).on(Expressions.numberPath(Long.class, receiptPoint, "voca_board_id").eq(Expressions.numberPath(Long.class, vocaBoard, "voca_board_id")))
                                        .join(voca).on(Expressions.numberPath(Long.class, vocaBoard, "voca_id").eq(voca.id))
                                        .where(fromUser.id.eq(userId).or(toUser.id.eq(userId)), Expressions.enumPath(ReceiptType.class, receiptPoint, "receipt_type").eq(ReceiptType.DEALVOCA))


                                , JPAExpressions
                                        .select(receiptPoint.id,
                                                Expressions.as(Expressions.nullExpression(), "vocaName"),
                                                fromUser.name.concat(" (").concat(Expressions.stringPath(fromUser, "login_id")).concat(")").as("fromUser"),
                                                toUser.name.concat(" (").concat(Expressions.stringPath(toUser, "login_id")).concat(")").as("toUser"),
                                                receiptPoint.point,
                                                Expressions.as(Expressions.nullExpression(), "buyCount"),
                                                Expressions.datePath(LocalDateTime.class, receiptPoint, "reg_dt").as("regDt"),
                                                Expressions.enumPath(ReceiptType.class, receiptPoint, "receipt_type").as("receiptType"),
                                                statusCase(fromUser, receiptPoint))
                                        .from(receiptPoint)
                                        .leftJoin(fromUser).on(receiptPoint.fromUser.eq(fromUser.loginId))
                                        .leftJoin(toUser).on(receiptPoint.toUser.eq(toUser.loginId))
                                        .where(fromUser.id.eq(userId).or(toUser.id.eq(userId)), Expressions.enumPath(ReceiptType.class, receiptPoint, "receipt_type").eq(ReceiptType.CHARGE))
                        ), receiptPointAs)
                .orderBy(receiptPoint.regDt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        /*List<ReceiptPointListRes> result = sqlQuery.clone()
                .select(Projections.bean(
                        ReceiptPointListRes.class,
                        receiptPoint.id,
                        vocaBoard.voca.vocaName.concat(" (").concat(vocaBoard.voca.country).concat(")").as("vocaName"),
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
                .fetch();*/

        long count = jpaQueryFactory
                .select(receiptPoint)
                .from(receiptPoint)
                .join(fromUser).on(receiptPoint.fromUser.eq(fromUser.loginId))
                .join(toUser).on(receiptPoint.toUser.eq(toUser.loginId))
                .join(vocaBoard).on(receiptPoint.vocaBoardId.eq(vocaBoard.id))
                .join(vocaBoard.voca)
                .where(fromUser.id.eq(userId).or(toUser.id.eq(userId)))
                .fetchCount();

        return new PageImpl<>(result, pageable, count);
    }


    private StringExpression statusCase(QUser fromUser, QReceiptPoint receiptPoint) {
        return new CaseBuilder()
                .when(Expressions.stringPath(fromUser, "login_id")
                        .eq(Expressions.stringPath(receiptPoint, "from_user")))
                .then("구매")
                .otherwise("판매")
                .as("status");

    }
}
