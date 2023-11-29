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
@Table(name = "receipt_point")
@ToString
public class ReceiptPoint extends CUpdate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "receipt_point_id")
    private Long id;

    private Long vocaBoardId;

    @Column(nullable = false)
    private int point;

    @Column(nullable = false, name = "from_user")
    private String fromUser;

    @Column(nullable = false, name = "to_user")
    private String toUser;

    @Enumerated(value = EnumType.STRING)
    private ReceiptType receiptType;


}
