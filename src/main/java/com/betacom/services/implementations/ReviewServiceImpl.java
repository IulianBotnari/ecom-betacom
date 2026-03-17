package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.review.ReviewRequest;
import com.betacom.dto.response.review.ReviewDTO;
import com.betacom.services.interfaces.InterfaceReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewServiceImpl implements InterfaceReviewService{@Override
	public ReviewDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ReviewDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(ReviewRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(ReviewRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
