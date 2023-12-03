package com.project.msv.service;

import com.project.msv.domain.Exchange;
import com.project.msv.domain.ReceiptPoint;
import com.project.msv.domain.User;
import com.project.msv.domain.enums.PayType;
import com.project.msv.domain.enums.ReceiptType;
import com.project.msv.domain.enums.Status;
import com.project.msv.dto.request.exchange.SaveExchangeReq;
import com.project.msv.dto.request.exchange.UpdateExchangeReq;
import com.project.msv.exception.DeleteException;
import com.project.msv.exception.NoneException;
import com.project.msv.exception.NotEnoughException;
import com.project.msv.repository.ExchangeRepository;
import com.project.msv.repository.ReceiptPointRepository;
import com.project.msv.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ExchangeService {

    private final ReceiptPointRepository receiptPointRepository;
    private final UserRepository userRepository;
    private final ExchangeRepository exchangeRepository;

    public void saveExchange(SaveExchangeReq saveExchangeReq, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new NoneException("유저가"));

        if (user.getPoint() < saveExchangeReq.getMoney()) {
            throw new NotEnoughException("포인트가");
        }

        exchangeRepository.save(saveExchangeReq.toEntity(user));

        user.setPoint(user.getPoint() - saveExchangeReq.getMoney());
    }

    public void updateState(UpdateExchangeReq updateExchangeReq) {
        Exchange exchange = exchangeRepository.findById(updateExchangeReq.getExchangeId()).orElseThrow(() -> new NoneException("환전 내역이"));

        exchange.setStatus(updateExchangeReq.getStatus());

        if (updateExchangeReq.getStatus().equals(Status.COMPLETED)) {
            User user = userRepository.findUserByLoginId(updateExchangeReq.getLoginId()).orElseThrow(() -> new NoneException("유저가"));

            receiptPointRepository.save(ReceiptPoint.builder()
                    .payType(PayType.POINT)
                    .point(exchange.getMoney())
                    .receiptType(ReceiptType.EXCHANGE)
                    .fromUser("ROOT@@")
                    .toUser(user.getLoginId())
                    .orderId(UUID.randomUUID().toString().replaceAll("-", ""))
                    .build());
        }
    }

    public void deleteById(Long exchangeId, Long userId) {
        Exchange exchange = exchangeRepository.findById(exchangeId).orElseThrow(() -> new NoneException("환전 내역이"));

        if (!exchange.getStatus().equals(Status.PROCESSING)) {
            throw new DeleteException("처리중인 내역만");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new NoneException("유저가"));

        user.setPoint(user.getPoint() + exchange.getMoney());

        exchange.setStatus(Status.REVOKE);
    }

    @Transactional(readOnly = true)
    public List<Exchange> findAllExchange() {
        return exchangeRepository.findAllExChangeOrderByDesc();
    }

    @Transactional(readOnly = true)
    public PageImpl<Exchange> findExchangeUser(Pageable pageable, Long userId, String status) {
        return exchangeRepository.findExchangeUser(userId, status, pageable);
    }


}
