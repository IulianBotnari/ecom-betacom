package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.payment_method.PaymentMethodRequest;
import com.betacom.dto.response.payment_method.PaymentMethodDTO;
import com.betacom.services.interfaces.InterfacePaymentMethodService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class PaymentMethodServiceImpl implements InterfacePaymentMethodService{@Override
	public PaymentMethodDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PaymentMethodDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(PaymentMethodRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(PaymentMethodRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
