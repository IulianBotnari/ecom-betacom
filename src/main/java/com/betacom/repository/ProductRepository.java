package com.betacom.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.betacom.enums.Genders;
import com.betacom.model.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	@Query("""
			SELECT DISTINCT p
			FROM Product p
			LEFT JOIN p.category c
			LEFT JOIN p.sizes s
			LEFT JOIN p.reviews r
			WHERE (:id IS NULL OR p.id = :id)
			AND (:name IS NULL OR p.name LIKE :name)
			AND (:categoryId IS NULL OR c.id = :categoryId)
			AND (:gender IS NULL OR p.gender = :gender)
			AND (:material IS NULL OR p.material LIKE :material)
			AND (:price IS NULL OR p.price <= :price)
			""")
	List<Product> findProductsByFilters(
		    @Param("id") Long id,
		    @Param("name") String name,
		    @Param("categoryId") Long categoryId,
		    @Param("gender") Genders gender,
		    @Param("material") String material,
		    @Param("price") Double price
		);
}
