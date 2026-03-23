package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.address.AddressCreateRequest;
import com.betacom.dto.request.address.AddressUpdateRequest;
import com.betacom.dto.response.address.AddressDTO;

public interface InterfaceAddressService {
	
	
	AddressDTO getById(Long id) throws Exception;
	
	List<AddressDTO> list() throws Exception;
	
	void create(AddressCreateRequest request) throws Exception;
	
	void update(AddressUpdateRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
	
	
}
