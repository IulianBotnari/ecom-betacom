package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.order.OrderRequest;
import com.betacom.dto.response.order.OrderDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.enums.OrderStatus;
import com.betacom.model.Address;
import com.betacom.model.Order;
import com.betacom.model.OrderedItemsDetails;
import com.betacom.model.User;
import com.betacom.repository.AddressRepository;
import com.betacom.repository.OrderRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.interfaces.InterfaceOrderService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderServiceImpl implements InterfaceOrderService{

    private final AddressRepository addressR;
	private final UserRepository userR;
    
	private final OrderRepository orderR;
	
	@Override
	public OrderDTO getById(Long id) throws Exception {
		log.debug("getById {}", id);
		
		Order order = orderR.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordine non trovato con id " + id));
		
		return DtoResponseMapper.orderDTO(order);
	}

	@Override 
	public List<OrderDTO> list() throws Exception {
		log.debug("list");
		
		List<Order> ord = orderR.findAll();
		
		return ord.stream().map(order -> 
				DtoResponseMapper.orderDTO(order))
					.collect(Collectors.toList());
	}

	@Override
	public void create(OrderRequest request) throws Exception {
		log.debug("create {}", request);
		
		User user = userR.findById(request.getUserId())
				.orElseThrow(()-> new Exception ("user non trovato"));
		
		Order order = new Order();
		order.setStatus(OrderStatus.valueOf(request.getStatus()));
	    order.setOrderPrice(request.getOrderPrice());
	    order.setUser(user);	    
	    
	    orderR.save(order);
	}
	@Override
	public void update(OrderRequest request) throws Exception {
		log.debug("create {}", request);
		
		Order order = orderR.findById(request.getId())
		        .orElseThrow(() -> new Exception("Ordine non presente in DB"));
		
		if (request.getUserId() != null) {
		    User user = userR.findById(request.getUserId())
		            .orElseThrow(() -> new Exception("Utente non trovato"));
		    order.setUser(user);
		}
		
		if (request.getShippingAddress() != null) {
		    Address address = addressR.findById(request.getShippingAddress())
		            .orElseThrow(() -> new Exception("Indirizzo non trovato"));
		    order.setShippingAddress(address);
		}
		
		if (request.getOrderPrice() != null) {
	        order.setOrderPrice(request.getOrderPrice());
	    }
		
		if (request.getStatus() != null) {
		    order.setStatus(OrderStatus.valueOf(request.getStatus()));
		}
		
		orderR.save(order);
	}

	@Override
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);
		
		Order order = orderR.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordine non trovato con id " + id));
		
		orderR.delete(order);
	}

}
