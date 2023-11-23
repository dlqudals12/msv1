package com.project.msv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.msv.domain.baseEntity.CUpdate;
import lombok.*;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@AllArgsConstructor
@Builder
public class VocaWord extends CUpdate {

    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "vocaword_id")
    private Long id;

    @Column(nullable = false)
    private String word1;

    @Column(nullable = false)
    private String word2;

    private String word3;

    private String word4;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voca_id", nullable = false)
    private Voca voca;


}
