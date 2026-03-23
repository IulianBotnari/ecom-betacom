package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.cart.CartRequest;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.dto.request.user.UserUpdateRequest;
import com.betacom.dto.response.user.UserDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.enums.Roles;
import com.betacom.model.User;
import com.betacom.repository.UserRepository;
import com.betacom.services.interfaces.InterfaceUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class UserServiceImpl implements InterfaceUserService{
	
	private final UserRepository userR;
	private final CartServiceImpl cartService;
	
	@Override
	public UserDTO getById(Long id) throws Exception {
		log.debug("getById {}", id);
		
		User user = userR.findById(id)
		        .orElseThrow(() -> new Exception("utente non presente in DB"));

		return DtoResponseMapper.userDTO(user);
	}

	@Override
	public List<UserDTO> list() throws Exception {
		log.debug("list");
		
		List<User> users = userR.findAll();
		
		return users.stream().map(user -> 
			DtoResponseMapper.userDTO(user))
				.collect(Collectors.toList());
	}

	@Override
	public void create(UserCreateRequest request) throws Exception {
		log.debug("create {}", request);
		
		User user = new User();
		user.setName(request.getName());
		user.setLastName(request.getLastName());
		user.setBirthday(request.getBirthday());
		if(request.getCodiceFiscale() != null)
			user.setCodiceFiscale(request.getCodiceFiscale());
		user.setEmail(request.getEmail());
		user.setPassword(request.getPassword());
		user.setPhone(request.getPhone());
		user.setRole(request.getRole());
		
		User userSaved = userR.save(user);
		cartService.create(new CartRequest(userSaved.getId()));
	}

	@Override
	public void update(UserUpdateRequest request) throws Exception {
		log.debug("create {}", request);

		User user = userR.findById(request.getId())
		        .orElseThrow(() -> new Exception("utente non presente in DB"));

	    if (request.getName() != null) {
	        user.setName(request.getName());
	    }
	    
	    

	    if (request.getLastName() != null) {
	        user.setLastName(request.getLastName());
	    }

	    if (request.getBirthday() != null) {
	        user.setBirthday(request.getBirthday());
	    }

	    if (request.getCodiceFiscale() != null) {
	        user.setCodiceFiscale(request.getCodiceFiscale());
	    }

	    if (request.getEmail() != null) {
	        user.setEmail(request.getEmail());
	    }

	    if (request.getPassword() != null) {
	        user.setPassword(request.getPassword());
	    }

	    if (request.getPhone() != null) {
	        user.setPhone(request.getPhone());
	    }

	    if (request.getRole() != null) {
	        user.setRole(Roles.valueOf(request.getRole()));
	    }

	    userR.save(user);
	}

	@Override
	public void delete(Long id) throws Exception {
		log.debug("delete {}", id);

		User user = userR.findById(id)
		        .orElseThrow(() -> new Exception("utente non presente in DB"));
		
		userR.delete(user);
	}

}
