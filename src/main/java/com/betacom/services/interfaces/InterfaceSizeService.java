package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.review.ReviewCreateRequest;
import com.betacom.dto.request.review.ReviewUpdateRequest;
import com.betacom.dto.request.size.SizeRequest;
import com.betacom.dto.response.review.ReviewDTO;
import com.betacom.dto.response.size.SizeDTO;
import com.betacom.model.Size;

public interface InterfaceSizeService {
	SizeDTO getById(Long id) throws Exception;
	
	List<SizeDTO> list() throws Exception;
	
	void create(SizeRequest request) throws Exception;
	
	void update(SizeRequest request) throws Exception;
	
	void delete(Long id) throws Exception;

}
