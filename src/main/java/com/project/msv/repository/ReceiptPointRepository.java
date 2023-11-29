package com.project.msv.repository;

import com.project.msv.domain.ReceiptPoint;
import com.project.msv.dto.response.receipt.ReceiptPointListResIn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReceiptPointRepository extends JpaRepository<ReceiptPoint, Long> {

    @Query(value = "select re.id, re.vocaName, re.fromUser, re.toUser, re.point, re.buyCount, re.reg_dt as regDt, re.receipt_type as receiptType, re.status " +
            "from ((select rp.receipt_point_id as id, concat(v.voca_name, '(', v.country, ')') as vocaName, " +
            "concat(fu.name, '(', fu.login_id, ')') as fromUser, concat(tu.name, '(', tu.login_id, ')') as toUser, " +
            "rp.point, vb.buycount as buyCount, rp.reg_dt, rp.receipt_type, " +
            "case when fu.user_id = :userId then '구매' else '판매' end as status " +
            "from receipt_point rp join user fu on rp.from_user = fu.login_id " +
            "join user tu on rp.to_user = tu.login_id " +
            "join voca_board vb on rp.voca_board_id = vb.voca_board_id " +
            "join voca v on vb.voca_id = v.voca_id " +
            "where (fu.user_id = :userId or tu.user_id = :userId) and rp.receipt_type = 'DEALVOCA') " +
            "union all " +
            "(select rp.receipt_point_id as id, '' as vocaName, " +
            "concat(fu.name, '(', fu.login_id, ')') as fromUser, '' as toUser, " +
            "rp.point, 0 as buyCount, rp.reg_dt, rp.receipt_type, " +
            "case when fu.user_id = :userId then '구매' else '판매' end as status " +
            "from receipt_point rp left join user fu on rp.from_user = fu.login_id " +
            "where (fu.user_id = :userId) and rp.receipt_type = 'CHARGE')) as re " +
            "order by re.reg_dt desc " +
            "limit :limit offset :offset"

            , nativeQuery = true)
    List<ReceiptPointListResIn> findReceiptList(Long userId, int limit, Long offset);

    @Query(value = "select count(re.id) " +
            "from ((select rp.receipt_point_id as id " +
            "from receipt_point rp join user fu on rp.from_user = fu.login_id " +
            "join user tu on rp.to_user = tu.login_id " +
            "join voca_board vb on rp.voca_board_id = vb.voca_board_id " +
            "join voca v on vb.voca_id = v.voca_id " +
            "where (fu.user_id = :userId or tu.user_id = :userId) and rp.receipt_type = 'DEALVOCA') " +
            "union all " +
            "(select rp.receipt_point_id as id " +
            "from receipt_point rp left join user fu on rp.from_user = fu.login_id " +
            "where (fu.user_id = :userId) and rp.receipt_type = 'CHARGE')) as re", nativeQuery = true)
    long findReceiptListCount(Long userId);


}
