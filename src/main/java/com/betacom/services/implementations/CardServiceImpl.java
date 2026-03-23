package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.card.CardRequest;
import com.betacom.dto.response.card.CardDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.model.Card;
import com.betacom.repository.CardRepository;
import com.betacom.repository.PaymentMethodRepository;
import com.betacom.services.interfaces.InterfaceCardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CardServiceImpl implements InterfaceCardService{
	
	private final CardRepository cardR;
	private final PaymentMethodRepository pmR;
	
	@Override
	public CardDTO getById(Long id) throws Exception {
	log.debug("getById {}", id);
		
	Card card = cardR.findById(id) 
			.orElseThrow(() -> new Exception("carta non presente in DB"));;
	
	return DtoResponseMapper.cardDTO(card);
	}

	@Override
	public List<CardDTO> list() throws Exception {
		log.debug("list");
		
		List<Card> cards = cardR.findAll();
		
		return cards.stream().map(card -> 
			DtoResponseMapper.cardDTO(card))
				.collect(Collectors.toList());
	}

	@Override
	public Card create(CardRequest request) throws Exception {
		log.debug("create {}", request);
		
		Card card = new Card();
		
		card.setCardNumber(request.getCardNumber());
		card.setExpiryDate(request.getExpiryDate());
		card.setCvv(request.getCvv());
		card.setCardHolder(request.getCardHolder());
		
			
		
		return cardR.save(card);
	
	}

	@Override
	public void update(CardRequest request) throws Exception {
		log.debug("create {}", request);
		
		Card card = cardR.findById(request.getId())
		        .orElseThrow(() -> new Exception("card non presente in DB"));
	

	
		if(request.getCardNumber()!=null) {
			card.setCardNumber(request.getCardNumber());
		}
		
		if(request.getExpiryDate()!=null) {
			card.setExpiryDate(request.getExpiryDate());
		}
		
		if(request.getCvv()!=null) {
			card.setCvv(request.getCvv());
		}
		
		if(request.getCardHolder()!=null) {
			card.setCardHolder(request.getCardHolder());
		}
		
		cardR.save(card);
		
	}

	@Override
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);

		Card card = cardR.findById(id)
		        .orElseThrow(() -> new Exception("card non presente in DB"));
		
		cardR.delete(card);
		
	}

	

}
