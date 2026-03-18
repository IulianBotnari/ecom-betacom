package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.review.ReviewCreateRequest;
import com.betacom.dto.request.review.ReviewUpdateRequest;
import com.betacom.dto.response.review.ReviewDTO;

public interface InterfaceReviewService {
	ReviewDTO getById(Long id) throws Exception;
	
	List<ReviewDTO> list() throws Exception;
	
	void create(ReviewCreateRequest request) throws Exception;
	
	void update(ReviewUpdateRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
