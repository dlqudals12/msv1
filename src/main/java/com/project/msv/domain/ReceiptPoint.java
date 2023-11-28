package com.project.msv.domain;

import com.project.msv.domain.baseEntity.CUpdate;
import com.project.msv.domain.enums.ReceiptType;
import lombok.*;

import javax.persistence.*;

@Getter
@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ReceiptPoint extends CUpdate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_point_id")
    private Long id;

    private Long vocaBoardId;

    @Column(nullable = false)
    private int point;

    @Column(nullable = false)
    private String fromUser;

    @Column(nullable = false)
    private String toUser;

    @Enumerated(value = EnumType.STRING)
    @Column(nullable = false)
    private ReceiptType receiptType;


}
