package com.betacom.services.interfaces;

import java.util.List;

import com.betacom.dto.request.wish_list.WishlistCreateRequest;
import com.betacom.dto.request.wish_list.WishlistUpdateRequest;
import com.betacom.dto.response.wish_list.WishListDTO;

public interface InterfaceWhishListService {
	WishListDTO getById(Long id) throws Exception;
	
	List<WishListDTO> list() throws Exception;
	
	void create(WishlistCreateRequest request) throws Exception;
	
	void update(WishlistUpdateRequest request) throws Exception;
	
	void delete(Long id) throws Exception;
}
