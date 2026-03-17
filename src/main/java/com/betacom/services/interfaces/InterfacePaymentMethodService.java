package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.order.OrderRequest;
import com.betacom.dto.request.payment_method.PaymentMethodRequest;
import com.betacom.dto.response.order.OrderDTO;
import com.betacom.dto.response.payment_method.PaymentMethodDTO;

public interface InterfacePaymentMethodService {
	PaymentMethodDTO getById(Long id) throws Exception;
	
	List<PaymentMethodDTO> list() throws Exception;
	
	void create(PaymentMethodRequest request) throws Exception;
	
	void update(PaymentMethodRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
