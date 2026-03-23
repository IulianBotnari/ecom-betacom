package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.address.AddressCreateRequest;
import com.betacom.dto.request.address.AddressUpdateRequest;
import com.betacom.dto.response.address.AddressDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.model.Address;
import com.betacom.model.User;
import com.betacom.repository.AddressRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.interfaces.InterfaceAddressService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class AddressServiceImpl implements InterfaceAddressService {
	
	private final AddressRepository addressR;
	private final UserRepository userR;
	
	public AddressDTO getById(Long id) throws Exception {
		log.debug("getById {}", id);
		
		Address address = addressR.findById(id).orElseThrow(() -> new Exception("Indirizzo non presente in DB"));

		return DtoResponseMapper.addressDTO(address);
	}

	@Override
	public List<AddressDTO> list() throws Exception {
		log.debug("list");
		
		List<Address> addressList = addressR.findAll();
		
		return addressList.stream().map(address -> DtoResponseMapper.addressDTO(address)).collect(Collectors.toList());
	}

	@Override
	public void create(AddressCreateRequest request) throws Exception {
		log.debug("create {}", request);
		
		Address address = new Address();
		address.setCity(request.getCity());
		address.setStreet(request.getStreet());
		address.setCivic(request.getCivic());
		if(request.getStaircase() != null)
		address.setStaircase(request.getStaircase());
		address.setProvince(request.getProvince());
		address.setCap(request.getCap());
		address.setCountry(request.getCountry());
		address.setResidence(request.getResidence());
		address.setDomicile(request.getDomicile());
		if(request.getDefaultAddress() != null)
		address.setDefaulAddress(request.getDefaultAddress());
		
		User user = userR.findById(request.getUserId()).orElseThrow(() -> new Exception("Utente di riferimento non trovato"));
		address.setUser(user);
		
		addressR.save(address);
	}

	@Override
	public void update(AddressUpdateRequest request) throws Exception {
		log.debug("update {}", request);
		
		addressR.findById(request.getId()).orElseThrow(() -> new Exception("Indirizzo non presente in DB"));

		Address address = new Address();

		if (request.getCity() != null) {
		    address.setCity(request.getCity());
		}

		if (request.getStreet() != null) {
		    address.setStreet(request.getStreet());
		}

		if (request.getCivic() != null) {
		    address.setCivic(request.getCivic());
		}

		if (request.getStaircase() != null) {
		    address.setStaircase(request.getStaircase());
		}

		if (request.getProvince() != null) {
		    address.setProvince(request.getProvince());
		}

		if (request.getCap() != null) {
		    address.setCap(request.getCap());
		}

		if (request.getCountry() != null) {
		    address.setCountry(request.getCountry());
		}

		if (request.getResidence() != null) {
		    address.setResidence(request.getResidence());
		}

		if (request.getDomicile() != null) {
		    address.setDomicile(request.getDomicile());
		}

		if (request.getDefaultAddress() != null) {
		    address.setDefaulAddress(request.getDefaultAddress());
		}
		
		if(request.getUserId() != null) {
			User user = userR.findById(request.getUserId()).orElseThrow(() -> new Exception("Utente di riferimento non trovato"));
			address.setUser(user);
		}
		
		addressR.save(address);
	}

	@Override
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);
		
		Address address = addressR.findById(id).orElseThrow(() -> new Exception("Indirizzo non presente in DB"));
		
		addressR.delete(address);
	}

}
