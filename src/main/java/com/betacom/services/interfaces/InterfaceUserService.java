package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.login.LoginRequest;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.dto.request.user.UserUpdateRequest;
import com.betacom.dto.response.login.LoginDTO;
import com.betacom.dto.response.user.UserDTO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface InterfaceUserService {
	void create(UserCreateRequest request) throws Exception;
	void update(UserUpdateRequest request) throws Exception;
	void delete(Long id) throws Exception;

	List<UserDTO> list() throws Exception;
	UserDTO getById(Long id) throws Exception;
	LoginDTO login(LoginRequest request, HttpServletRequest httpRequest, 
            HttpServletResponse httpResponse) throws Exception;
}
