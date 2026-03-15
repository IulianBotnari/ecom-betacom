package com.betacom.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Table(name = "cards")
@AllArgsConstructor 
@NoArgsConstructor 
@Getter 
@Setter
public class Card {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "card")
    private PaymentMethod paymentMethod;

    @Column(nullable = false, name="card_number")
    private String cardNumber;

    @Column(nullable = false,name="expiry_date")
    private String expiryDate;

    @Column(nullable = false)
    private String cvv;

    @Column(nullable = false, name="card_holder")
    private String cardHolder;
}
