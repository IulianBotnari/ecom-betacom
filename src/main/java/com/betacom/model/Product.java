package com.betacom.model;

import java.util.List;

import com.betacom.enums.Genders;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String image;
	
	@Column(columnDefinition = "TEXT")
	private String description;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@Enumerated(EnumType.STRING)
	private Genders gender;
	
	private String material;
	
	private Double price;
	
	@Column(nullable = true)
	private Double discount;
	
	@Column(nullable = true)
	private Double discountPercentage;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Size> sizes;
	
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
	private List<Review> reviews;
	
	@PrePersist
	@PreUpdate
	public void calculateDiscountPercentage() {
	    if (price != null && discount != null && price > 0) {

	        Double priceDiff = price - discount;
	        Double percentage = priceDiff / (price /100);
	        percentage = (double) Math.round(percentage);
	        this.discountPercentage = percentage;
	    } else {
	        this.discountPercentage = 0.0;
	    }
	}
}

