package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.wish_list.WishlistRequest;
import com.betacom.dto.response.wish_list.WishListDTO;
import com.betacom.services.interfaces.InterfaceWhishListService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class WishListServiceImpl implements InterfaceWhishListService{@Override
	public WishListDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<WishListDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(WishlistRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(WishlistRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
