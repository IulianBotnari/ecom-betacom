package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.address.AddressRequest;
import com.betacom.dto.response.address.AddressDTO;
import com.betacom.services.interfaces.InterfaceAddressService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddressServiceImpl implements InterfaceAddressService{@Override
	public AddressDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AddressDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(AddressRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(AddressRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
