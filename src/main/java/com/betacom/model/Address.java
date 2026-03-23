package com.betacom.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Table(name = "addresses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
    private String city;
	
	@Column(nullable = false)
    private String street;
	
	@Column(nullable = false)
    private String civic;
    
	@Column(nullable = true)
    private String staircase;
    
	@Column(nullable = false)
    private String province;
	
	@Column(nullable = false)
    private String cap;
	
	@Column(nullable = false)
    private String country;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true) 
    private User user;

    @Column(nullable = false)
    private boolean residence;
    
    @Column(nullable = false)
    private boolean domicile;
    
    @Column(name ="default_address")
    private boolean defaulAddress;
}
