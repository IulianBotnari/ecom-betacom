package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.size.SizeRequest;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.dto.request.user.UserUpdateRequest;
import com.betacom.dto.response.size.SizeDTO;
import com.betacom.dto.response.user.UserDTO;

public interface InterfaceUserService {
	UserDTO getById(Long id) throws Exception;
	
	List<UserDTO> list() throws Exception;
	
	void create(UserCreateRequest request) throws Exception;
	
	void update(UserUpdateRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
