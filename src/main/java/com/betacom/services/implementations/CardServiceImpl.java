package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.card.CardRequest;
import com.betacom.dto.response.card.CardDTO;
import com.betacom.services.interfaces.InterfaceCardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CardServiceImpl implements InterfaceCardService{@Override
	public CardDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CardDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(CardRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CardRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
