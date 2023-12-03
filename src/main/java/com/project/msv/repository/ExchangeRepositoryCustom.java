package com.project.msv.repository;

import com.project.msv.domain.Exchange;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface ExchangeRepositoryCustom {

    PageImpl<Exchange> findExchangeUser(Long id, String status, Pageable pageable);
}
