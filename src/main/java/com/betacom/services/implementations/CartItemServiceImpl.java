package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.cart_item.CartItemRequest;
import com.betacom.dto.response.cart_item.CartItemDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.model.Cart;
import com.betacom.model.CartItem;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.repository.CartItemRepository;
import com.betacom.repository.CartRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.repository.SizeRepository;
import com.betacom.services.interfaces.InterfaceCartItemService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service

public class CartItemServiceImpl implements InterfaceCartItemService{
	
	
	private final CartItemRepository cartItemR;
	private final ProductRepository prodR;
	private final CartRepository cartR;
   
	@Override
	public CartItemDTO getById(Long id) throws Exception {
		log.debug("getById {}", id);
		
			
		CartItem item = cartItemR.findById(id)
				.orElseThrow(() -> new Exception("elemento carrello non presente in DB"));

			return DtoResponseMapper.cartItemDTO(item);
	
	}
	
	
	public CartItem getByCartItem(Long id) throws Exception {
		log.debug("getById {}", id);
		
			
		CartItem item = cartItemR.findById(id)
				.orElseThrow(() -> new Exception("elemento carrello non presente in DB"));

			return item;
	
	}

	@Override
	public List<CartItemDTO> list() throws Exception {
			
		log.debug("list");
		
		List<CartItem> items = cartItemR.findAll();
		
		return items.stream()
				.map(item -> DtoResponseMapper.cartItemDTO(item))
				.collect(Collectors.toList());
		
	}

	@Override
	public void create(CartItemRequest request) throws Exception {
		
		log.debug("create {}", request);
		
		if (request.getQuantity() == null || request.getQuantity() <= 0) {
	        throw new Exception("La quantità deve essere presente e maggiore di zero");
	
		}
			
		if (request.getCartId() == null) {
	        
			throw new Exception("ID Carrello mancante");
	    
		}
			
		if (request.getProductId() == null) {
	       
			throw new Exception("ID Prodotto mancante");
	    
		}
			
		if (request.getSizeId() == null) {
	       
			throw new Exception("ID Taglia mancante");
	    
		}
		
			
			CartItem cartItem = new CartItem();
			
			
			cartItem.setQuantity(request.getQuantity());
			Product prodotto = prodR.findById(request.getProductId())
					.orElseThrow(()-> new Exception("Prodotto non trovato"));
			cartItem.setProduct(prodotto);
			Cart cart = cartR.findById(request.getCartId())
					.orElseThrow(()-> new Exception("Carrello non trovato"));
			cartItem.setQuantity(request.getQuantity());
			
			cartItemR.save(cartItem);
	
	}

	@Override
	public void update(CartItemRequest request) throws Exception {
		log.debug("update {}", request);
		
		CartItem item = cartItemR.findById(request.getCartItemId())//recupero item del carrello
				.orElseThrow(() -> new Exception("elemento carrello non presente in DB"));
		
		
		if (request.getQuantity() != null || request.getQuantity() <= 0) {
	        item.setQuantity(request.getQuantity());
	
		}
			
		if (request.getCartId() != null) {
			Cart cart = cartR.findById(request.getCartId())
					.orElseThrow(()-> new Exception("Carrello non trovato"));
			item.setCart(cart);
	    
		}
			
		if (request.getProductId() != null) {
			Product prodotto = prodR.findById(request.getProductId())
					.orElseThrow(()-> new Exception("Prodotto non trovato"));
			item.setProduct(prodotto);
	       
		}
			
		cartItemR.save(item);
		
		
		
	}

	@Override
	public void delete(Long id) throws Exception {
		CartItem item = cartItemR.findById(id)//recupero item del carrello
				.orElseThrow(() -> new Exception("elemento carrello non presente in DB"));
		cartItemR.delete(item);
		
	}

}
