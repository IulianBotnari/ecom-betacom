package com.betacom.model;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;

import com.betacom.enums.Roles;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Builder
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false, name = "last_name")
	private String lastName;
	
	@Column(nullable = false)
	private String email;
	
	@Column(nullable = false)
	private LocalDate birthday;
	
	@Column(nullable = true, name = "codice_fiscale")
	private String codiceFiscale;
	
	@Column(nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Address> addresses;
	
	@Column(nullable = false)
	private String phone;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Roles role;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<PaymentMethod> paymentMethods;
	

	@OneToMany(mappedBy = "user")
	private List<Order> orders;
	
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
	private Cart cart;

	@OneToMany(mappedBy = "user")
	private List<Review> reviews;
	
	@CreationTimestamp
	@Column(name = "create_date")
	private LocalDate createDate;
	
	@Column(name = "delete_date")
	private LocalDate deleteDate;
	
	
	
}
