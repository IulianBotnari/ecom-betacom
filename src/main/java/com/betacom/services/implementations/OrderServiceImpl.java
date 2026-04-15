package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.dto.request.order.OrderRequest;
import com.betacom.dto.response.order.OrderDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.enums.OrderStatus;
import com.betacom.model.Address;
import com.betacom.model.Cart;
import com.betacom.model.Order;
import com.betacom.model.OrderedItemsDetails;
import com.betacom.model.PaymentMethod;
import com.betacom.model.User;
import com.betacom.repository.AddressRepository;
import com.betacom.repository.CartRepository;
import com.betacom.repository.OrderRepository;
import com.betacom.repository.PaymentMethodRepository;
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
    private final CartRepository cartR;
	private final OrderRepository orderR;
	private final PaymentMethodRepository payR;
	
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
	@Transactional 
	public OrderDTO create(OrderRequest request) throws Exception {
	    log.debug("create {}", request);
	    
	    if(request.getUserId() == null) {
	        throw new Exception("Devi associare un id utente all'ordine"); 
	    }
	    
	    User user = userR.findById(request.getUserId())
	            .orElseThrow(() -> new Exception("User non trovato"));
	    
	    Order order = new Order();
	    
	    Address defaultAddress = user.getAddresses().stream()
	            .filter(Address::isDefaulAddress) 
	            .findFirst()
	            .orElseThrow(() -> new Exception("Non hai un indirizzo predefinito"));
	    

	    PaymentMethod paymentMethod = payR.findById(request.getPaymentMethodId())
	            .orElseThrow(() -> new Exception("Metodo pagamento non trovato"));
	    
	    order.setShippingAddress(defaultAddress);
	    order.setStatus(OrderStatus.ORDINATO); 
	    order.setPaymentMethod(paymentMethod);
	    order.setOrderPrice(0.0);
	    order.setUser(user);	    

	    Order response = orderR.save(order);
	    
	    log.info("Ordine creato con ID: {}", response.getId());
	    
	    return DtoResponseMapper.orderDTO(response);
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
		
		if (request.getPaymentMethodId() != null) {
			PaymentMethod paymentMethod = payR.findById(request.getPaymentMethodId()).orElseThrow(()->new Exception("metodo pagamento non trovato"));
		    order.setPaymentMethod(paymentMethod);
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

	@Override
	public OrderDTO getByUserId(Long id) throws Exception {
	    log.debug("Richiesta ordine per userId: {}", id);
	    
	
	    User user = userR.findById(id)
	            .orElseThrow(() -> new Exception("Utente non trovato con id: " + id));
	    

	    Order order = orderR.findByUser(user)
	            .orElseThrow(() -> new Exception("Nessun ordine trovato associato all'utente con id: " + id));
	    
	 
	    return DtoResponseMapper.orderDTO(order);
	}
	
	
	
	
	
	
}
