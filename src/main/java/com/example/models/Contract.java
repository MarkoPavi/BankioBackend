package com.example.models;

import lombok.*;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@Table(name = "contract")
@NoArgsConstructor
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "title")
    private String title;

    @Column(name = "gender")
    private String gender;

    @Column(name = "address")
    private String address;

    @Column(name = "postalCode")
    private String postalCode;

    @Column(name = "birthCountry")
    private String birthCountry;

    @Column(name = "stayingCountry")
    private String stayingCountry;

    @Column(name = "citizenship")
    private String citizenship;

    @Column(name = "phoneNumber")
    private String phoneNumber;

    @Column(name = "loanAmmount")
    private BigDecimal loanAmmount;

    @Column(name = "loanDuraton")
    private int loanDuration;

    @Column(name = "interestRate")
    private double interestRate;

    @Column(name = "monthlyPayment")
    private double monthlyPayment;

    @Column(name = "income")
    private Double income;

    @Column(name = "taxes")
    private Double taxes;

    @Column(name = "iban")
    private String iban;

    @Column(name = "published")
    private Boolean published;

    /*@Column(name = "relationship_status")
    private String relationshipStatus;*/

    @Column(name = "description")
    private String description;


}
