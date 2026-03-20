package com.betacom.services.implementations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.cart.CartRequest;
import com.betacom.dto.response.cart.CartDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.model.Cart;
import com.betacom.model.User;
import com.betacom.repository.CartRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.interfaces.InterfaceCartService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CartServiceImpl implements InterfaceCartService{
	
	private final CartRepository cartR;
	private final UserRepository userR;
	
	@Override
	public CartDTO getById(Long id) throws Exception {
		log.debug("getById{}",id);
		Cart cart = cartR.findById(id)
				.orElseThrow(()->new Exception("cart non prsente nel DB"));
		
		
		return DtoResponseMapper.cartDTO(cart);
	}
	
	
	

	@Override
	public List<CartDTO> list() throws Exception {
		log.debug("list");
		
		List<Cart> carts = cartR.findAll();
		
		
		return carts.stream()
				.map(cart -> DtoResponseMapper.cartDTO(cart))
				.collect(Collectors.toList());
	}

	@Override
	public void create(CartRequest request) throws Exception {
		log.debug("create{}",request );
		
		User user = userR.findById(request.getUserId()) // controllo se ci sia un utente 
				.orElseThrow(()-> new Exception ("user non trovato"));
		
		Cart cart = new Cart();
		
		cart.setUser(user);
		cart.setCartItems(new ArrayList<>()); 
		
		cartR.save(cart);
		
	}

	@Override
	public void update(CartRequest request) throws Exception {
		log.debug("upDate{}", request);
		
		Cart cart = cartR.findById(request.getUserId())
				.orElseThrow(()-> new Exception("cart non prsente nel DB"));
		
		if(request.getUserId()!= null) {
			User user = userR.findById(request.getUserId())
					.orElseThrow(()-> new Exception("user non trovato per update"));
					
				cart.setUser(user);
			}

		
	}

	@Override
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);
		
		Cart cart = cartR.findById(id)
				.orElseThrow(()-> new Exception("cart non presente nel DB"));
		
		cartR.delete(cart);
		
	}

}
