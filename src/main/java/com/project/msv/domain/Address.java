package com.project.msv.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Address {


    private String street;
    private Integer zipcode;

    public Address(String street, Integer zipcode) {
        this.street = street;
        this.zipcode = zipcode;
    }
}
