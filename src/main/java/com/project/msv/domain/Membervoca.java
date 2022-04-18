package com.project.msv.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Membervoca {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "membervoca_id")
    private Long id;

    private Long vocaboardid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Builder
    public Membervoca(Long id, Long vocaboardid, Member member) {
        this.id = id;
        this.vocaboardid = vocaboardid;
        this.member = member;
    }
}
