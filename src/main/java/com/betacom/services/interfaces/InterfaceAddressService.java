package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.address.AddressRequest;
import com.betacom.dto.response.address.AddressDTO;

public interface InterfaceAddressService {
	
	
	AddressDTO getById(Long id) throws Exception;
	
	List<AddressDTO> list() throws Exception;
	
	void create(AddressRequest request) throws Exception;
	
	void update(AddressRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
	
	
}
