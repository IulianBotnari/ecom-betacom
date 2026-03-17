package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.size.SizeRequest;
import com.betacom.dto.request.user.UserRequest;
import com.betacom.dto.response.size.SizeDTO;
import com.betacom.dto.response.user.UserDTO;

public interface InterfaceUserService {
	UserDTO getById(Long id) throws Exception;
	
	List<UserDTO> list() throws Exception;
	
	void create(UserRequest request) throws Exception;
	
	void update(UserRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
