package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.user.UserRequest;
import com.betacom.dto.request.wish_list.WishlistRequest;
import com.betacom.dto.response.user.UserDTO;
import com.betacom.dto.response.wish_list.WishListDTO;

public interface InterfaceWhishListService {
	WishListDTO getById(Long id) throws Exception;
	
	List<WishListDTO> list() throws Exception;
	
	void create(WishlistRequest request) throws Exception;
	
	void update(WishlistRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
