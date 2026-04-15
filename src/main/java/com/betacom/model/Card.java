	package com.betacom.model;
	
	import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
	
@Builder
@Entity
@Table(name = "cards")
@AllArgsConstructor
@NoArgsConstructor
@Data
	public class Card {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "card", cascade = CascadeType.REMOVE, orphanRemoval = true)
    @ToString.Exclude
    private PaymentMethod paymentMethod;

    @Column(nullable = false, name="card_number")
    private String cardNumber;

    @Column(nullable = false, name="expiry_date")
    private String expiryDate;

    @Column(nullable = false)
    private String cvv;

    @Column(nullable = false, name="card_holder")
    private String cardHolder;
	}
