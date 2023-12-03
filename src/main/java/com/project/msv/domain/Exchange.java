package com.project.msv.domain;

import com.project.msv.domain.baseEntity.Create;
import com.project.msv.domain.enums.Status;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Exchange extends Create {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exchange_id")
    private Long id;

    @Column(nullable = false)
    private int money;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String bankNum;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


    public void setStatus(Status status) {
        this.status = status;
    }
}