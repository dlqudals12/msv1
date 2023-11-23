package com.project.msv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.msv.domain.baseEntity.CUpdate;
import com.project.msv.dto.response.voca.VocaListRes;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Voca extends CUpdate {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "voca_id")
    private Long id;

    @Column(nullable = false)
    private String vocaName;

    @Column(nullable = false)
    private String country;;

    @Column(nullable = false)
    private String column1;

    @Column(nullable = false)
    private String column2;

    private String column3;

    private String column4;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Builder.Default
    @OneToMany(mappedBy = "voca", cascade = CascadeType.ALL)
    private List<VocaWord> vocaWords = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "voca")
    private VocaBoard vocaBoard;

    @Builder.Default
    @JsonIgnore
    @OneToMany(mappedBy = "voca", cascade = CascadeType.ALL)
    private List<TradeVoca> tradeVocas = new ArrayList<>();


}