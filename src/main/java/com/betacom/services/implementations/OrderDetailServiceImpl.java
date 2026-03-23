package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.order_details.OrderDetailRequest;
import com.betacom.dto.response.order_details.OrderDetailsDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.model.Order;
import com.betacom.model.OrderDetail;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.repository.OrderDetailRepository;
import com.betacom.repository.OrderRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.repository.SizeRepository;
import com.betacom.services.interfaces.InterfaceOrderDetailService;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class OrderDetailServiceImpl implements InterfaceOrderDetailService{
	
	private final OrderRepository orderR;
	private final SizeRepository sizeR;
	private final ProductRepository productR;
	
	private final OrderDetailRepository orderDR;
	
	@Override
	public OrderDetailsDTO getById(Long id) throws Exception {
		log.debug("getById {}", id);
		
		OrderDetail detail = orderDR.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordine non trovato con id " + id));
		
		return DtoResponseMapper.orderDetailsDTO(detail);
	}

	@Override
	public List<OrderDetailsDTO> list() throws Exception {
		log.debug("list");
		
		List<OrderDetail> detail = orderDR.findAll();
		
		return detail.stream().map(order -> 
				DtoResponseMapper.orderDetailsDTO(order))
					.collect(Collectors.toList());
	}

	@Override
	public void create(OrderDetailRequest request) throws Exception {
		log.debug("create {}", request);
		
		OrderDetail detail = new OrderDetail();
		
		if (request.getOrderId() == null)
	        throw new Exception("OrderId è necessario");

	    if (request.getProductId() == null)
	        throw new Exception("ProductId è necessario");

	    if (request.getQuantity() == null || request.getQuantity() <= 0)
	        throw new Exception("Quantity deve essere maggiore di 0");
	    
	    detail.setQuantity(request.getQuantity());
	    detail.setTotalPrice(request.getTotalPrice());
	    
	}

	@Override
	public void update(OrderDetailRequest request) throws Exception {
		log.debug("create {}", request);
		
		OrderDetail orderD = orderDR.findById(request.getOrderId())
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
	    }
		
		if (request.getTotalPrice() != null) {
	        orderD.setTotalPrice(request.getTotalPrice());
	    }
	}

	@Override
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);
		
		OrderDetail orderD = orderDR.findById(id)
				.orElseThrow(() -> new EntityNotFoundException("Ordine non trovato con id " + id));
		
		orderDR.delete(orderD);
		
	}

}
