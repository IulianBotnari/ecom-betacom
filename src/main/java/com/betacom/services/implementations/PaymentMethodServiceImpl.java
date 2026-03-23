package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.payment_method.PaymentMethodRequest;
import com.betacom.dto.response.payment_method.PaymentMethodDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.model.Card;
import com.betacom.model.PaymentMethod;
import com.betacom.model.User;
import com.betacom.repository.CardRepository;
import com.betacom.repository.PaymentMethodRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.interfaces.InterfaceCardService;
import com.betacom.services.interfaces.InterfacePaymentMethodService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentMethodServiceImpl implements InterfacePaymentMethodService{
	
	private final PaymentMethodRepository pmR;
	
	private final UserRepository userR;
	
	
	private final InterfaceCardService cardService;
	
	@Override
	public PaymentMethodDTO getById(Long id) throws Exception {
	log.debug("getById {}", id);
	
	PaymentMethod paymentMethod = pmR.findById(id) 
			.orElseThrow(() -> new Exception("carta non presente in DB"));;
	
	return DtoResponseMapper.paymentMethodDTO(paymentMethod);
	}

	@Override
	public List<PaymentMethodDTO> list() throws Exception {
		log.debug("list");
		
		List<PaymentMethod> paymentMethod = pmR.findAll();
		
		return paymentMethod.stream()
				.map(paymentM -> DtoResponseMapper.paymentMethodDTO(paymentM))
			.collect(Collectors.toList());
		
		
	}

	@Override
	public void create(PaymentMethodRequest request) throws Exception {
		log.debug("create {}", request);
		
		User user = userR.findById(request.getUserId())
				.orElseThrow(() -> new Exception("User non trovato in DB:" + request.getUserId()));
		
		//Card card = cardR.findById(request.getCardId())
	  //.orElseThrow(() -> new Exception("Carta non trovata in DB:" + request.getCardId()));
			
		Card card = cardService.create(request.getCard());
		
		PaymentMethod pm = new PaymentMethod();
	    pm.setDescription(request.getDescription());
	    pm.setUser(user); 
	    pm.setCard(card);
	    
	    pmR.save(pm);
		
	}

	@Override
	public void update(PaymentMethodRequest request) throws Exception {
		log.debug("create {}", request);
		
		PaymentMethod pm = pmR.findById(request.getId())
		        .orElseThrow(() -> new Exception("Metodo di pagamento non trovato"));
		
		
		if(request.getDescription()!=null) {
			pm.setDescription(request.getDescription());
		}
			
        if (request.getUserId() != null) {
            User user = userR.findById(request.getUserId())
                    .orElseThrow(() -> new Exception("User non trovato"));	
            pm.setUser(user);
        }

      
        if (request.getCard() != null) {	
        	Long idCartaDaAggiornare = pm.getCard().getId();
        	
        	request.getCard().setId(idCartaDaAggiornare);
        	
            cardService.update(request.getCard());
		
		pmR.save(pm);
        }
	}

	@Override
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);
		
		PaymentMethod pm = pmR.findById(id)
				.orElseThrow(() -> new Exception("Metodo pagamento non presente in DB"));
		
		pmR.delete(pm);
	}

}