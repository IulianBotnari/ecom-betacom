package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.request.review.ReviewRequest;
import com.betacom.dto.response.product.ProductsDTO;
import com.betacom.dto.response.review.ReviewDTO;

public interface InterfaceReviewService {
	ReviewDTO getById(Long id) throws Exception;
	
	List<ReviewDTO> list() throws Exception;
	
	void create(ReviewRequest request) throws Exception;
	
	void update(ReviewRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
