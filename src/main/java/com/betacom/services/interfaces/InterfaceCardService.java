package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.card.CardRequest;
import com.betacom.dto.response.card.CardDTO;


public interface InterfaceCardService {
	CardDTO getById(Long id) throws Exception;
	
	List<CardDTO> list() throws Exception;
	
	void create(CardRequest request) throws Exception;
	
	void update(CardRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
