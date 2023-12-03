package com.project.msv.repository;

import com.project.msv.domain.Exchange;
import com.project.msv.domain.QExchange;
import com.project.msv.domain.enums.Status;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ExchangeRepositoryImpl extends QuerydslRepositorySupport implements ExchangeRepositoryCustom {


    private final QExchange exchange = QExchange.exchange;
    private final JPAQueryFactory jpaQueryFactory;

    public ExchangeRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(Exchange.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public PageImpl<Exchange> findExchangeUser(Long id, String status, Pageable pageable) {
        List<Exchange> fetchList = jpaQueryFactory
                .selectFrom(exchange)
                .join(exchange.user)
                .fetchJoin()
                .where(filter(id, status))
                .orderBy(exchange.regDt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetch();

        long count = jpaQueryFactory
                .selectFrom(exchange)
                .join(exchange.user)
                .fetchJoin()
                .where(filter(id, status))
                .orderBy(exchange.regDt.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset())
                .fetchCount();

        return new PageImpl<>(fetchList, pageable, count);
    }

    private BooleanBuilder filter(Long id, String status) {
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(exchange.user.id.eq(id));

        if (!status.isEmpty()) {
            builder.and(exchange.status.eq(Status.valueOf(status)));
        }

        return builder;
    }
}
