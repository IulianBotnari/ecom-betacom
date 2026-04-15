package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.card.CardRequest;
import com.betacom.dto.response.card.CardDTO;
import com.betacom.model.Card;


public interface InterfaceCardService {
	CardDTO getById(Long id) throws Exception;
	
	List<CardDTO> list() throws Exception;
	
	Card create(CardRequest request) throws Exception;
	
	//void update(CardRequest request) throws Exception;
	
	void delete(Long id) throws Exception;

	void update(CardRequest request) throws Exception;

	

	

	
}
