package com.project.msv.repository;

import com.project.msv.domain.Exchange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ExchangeRepository extends JpaRepository<Exchange, Long>, ExchangeRepositoryCustom {

    @Query(value = "select ex from Exchange ex join fetch ex.user u order by ex.regDt desc")
    List<Exchange> findAllExChangeOrderByDesc();

}
