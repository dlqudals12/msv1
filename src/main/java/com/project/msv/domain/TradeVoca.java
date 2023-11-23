package com.project.msv.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.msv.domain.baseEntity.CUpdate;
import com.project.msv.dto.response.voca.VocaListRes;
import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "trade_voca")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class TradeVoca extends CUpdate {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trade_voca_id")
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voca_id")
    @JsonIgnore
    private Voca voca;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public VocaListRes toDto() {
        return VocaListRes.builder()
                .vocaName(voca.getVocaName())
                .country(voca.getCountry())
                .column1(voca.getColumn1())
                .column2(voca.getColumn2())
                .column3(voca.getColumn3())
                .column4(voca.getColumn4())
                .vocaId(voca.getId())
                .own(user.getId().equals(voca.getUser().getId()))
                .build();
    }


}
