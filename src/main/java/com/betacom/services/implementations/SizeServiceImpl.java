package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.size.SizeRequest;
import com.betacom.dto.response.size.SizeDTO;
import com.betacom.services.interfaces.InterfaceSizeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class SizeServiceImpl implements InterfaceSizeService{@Override
	public SizeDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SizeDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(SizeRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(SizeRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
