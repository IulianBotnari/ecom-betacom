package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.dto.request.ordered_items_details.OrderedItemsDetailsRequest;
import com.betacom.dto.response.ordered_items_details.OrderedItemsDetailsDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.model.Order;
import com.betacom.model.OrderedItemsDetails;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.repository.OrderedItemsDetailsRepository;
import com.betacom.repository.OrderRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.repository.SizeRepository;
import com.betacom.services.interfaces.InterfaceOrderedItemsDetailsService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderedItemsDetailsServiceImpl implements InterfaceOrderedItemsDetailsService{
	
	private final OrderRepository orderR;
	private final SizeRepository sizeR;
	private final ProductRepository productR;
	
	private final OrderedItemsDetailsRepository orderDR;
	
	@Override
	public OrderedItemsDetailsDTO getById(Long id) throws Exception {
		log.debug("getById {}", id);
		
		OrderedItemsDetails detail = orderDR.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordine non trovato con id " + id));
		
		return DtoResponseMapper.orderItemDetailsDTO(detail);
	}

	@Override
	public List<OrderedItemsDetailsDTO> list() throws Exception {
		log.debug("list");
		
		List<OrderedItemsDetails> detail = orderDR.findAll();
		
		return detail.stream().map(order -> 
				DtoResponseMapper.orderItemDetailsDTO(order))
					.collect(Collectors.toList());
	}
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void create(OrderedItemsDetailsRequest request) throws Exception {
		log.debug("create {}", request);
		
		OrderedItemsDetails detail = new OrderedItemsDetails();
		
		if (request.getOrderId() == null)
	        throw new Exception("OrderId è necessario");

	    if (request.getProductId() == null)
	        throw new Exception("ProductId è necessario");

	    if (request.getQuantity() == null || request.getQuantity() <= 0)
	        throw new Exception("Quantity deve essere maggiore di 0");
	    
	    Order order = orderR.findById(request.getOrderId()).orElseThrow(() -> new EntityNotFoundException("Ordine non trovato"));
	    Product product = productR.findById(request.getProductId()).orElseThrow(() -> new EntityNotFoundException("Prodotto non trovato"));

	    detail.setOrder(order);
	    detail.setProduct(product);
	    detail.setQuantity(request.getQuantity());
	    detail.setTotalPrice(product.getPrice() * request.getQuantity());
	    
	    orderDR.save(detail);
	    
	    Order orderF = orderR.findById(request.getOrderId()).orElseThrow(()-> new Exception("Ordine non trovato"));
	    
	    orderF.setOrderPrice(orderF.getOrderPrice() + detail.getTotalPrice());
	    
	    orderR.save(orderF);
	    
	    
	    
	}

	@Override
	public void update(OrderedItemsDetailsRequest request) throws Exception {
		log.debug("create {}", request);
		
		OrderedItemsDetails orderD = orderDR.findById(request.getOrderId())
		        .orElseThrow(() -> new Exception("Ordine non presente in DB"));
		
		if (request.getProductId() != null) {
		    Order order = orderR.findById(request.getProductId())
		            .orElseThrow(() -> new Exception("Ordine non trovato"));
		    orderD.setOrder(order);
		}
		
		if (request.getProductId() != null) {
		    Product product = productR.findById(request.getProductId())
		            .orElseThrow(() -> new Exception("Prodotto non trovato"));
		    orderD.setProduct(product);
		}
		
		if (request.getProductId() != null) {
		    Size size = sizeR.findById(request.getSizeId())
		            .orElseThrow(() -> new Exception("Size non trovato"));
		    orderD.setSize(size);
		}
		
		if (request.getQuantity() != null) {
	        orderD.setQuantity(request.getQuantity());
	        orderD.setTotalPrice(orderD.getProduct().getPrice() * orderD.getQuantity());
	    }
		
		if (request.getTotalPrice() != null) {
	        orderD.setTotalPrice(request.getTotalPrice());
	    }
		
		orderDR.save(orderD);
	}
	
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);
		
		OrderedItemsDetails orderD = orderDR.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordine non trovato con id " + id));
		Order order = orderR.findById(orderD.getOrder().getId()).orElseThrow(()-> new Exception("Ordine non trovato"));
		
		order.setOrderPrice(order.getOrderPrice() - orderD.getTotalPrice());
		orderR.save(order);
		orderDR.delete(orderD);
		
	}

}
