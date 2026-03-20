package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.size.SizeRequest;
import com.betacom.dto.response.size.SizeDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.dto_mappers.map_model.ModelMappers;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.repository.ProductRepository;
import com.betacom.repository.SizeRepository;
import com.betacom.services.interfaces.InterfaceSizeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class SizeServiceImpl implements InterfaceSizeService{
	public final ModelMappers modelM;
	
	private final SizeRepository sizeR;
	private final ProductRepository productR;
	
	@Override
	public SizeDTO getById(Long id) throws Exception {
		Size response = sizeR.findById(id).orElseThrow(()-> new Exception("Size non trovata"));
		return DtoResponseMapper.sizeDTO(response);
	}


	@Override
	public List<SizeDTO> list() throws Exception {
		List<Size> list = sizeR.findAll();
		return list.stream().map(el -> DtoResponseMapper.sizeDTO(el)).collect(Collectors.toList());
	}

	@Override
	public void create(SizeRequest request) throws Exception {
		Product product = productR.findById(request.getProductId()).orElseThrow(()-> new Exception("Prodotto non trovato"));
		Size size = modelM.size(request, product);
		
		sizeR.save(size);

		
	}

	@Override
	public void update(SizeRequest request) throws Exception {
		
		Size response = sizeR.findById(request.getId()).orElseThrow(()-> new Exception("Size non trovata in db"));
		
		sizeR.save(response);

		
	}

	@Override
	public void delete(Long id) throws Exception {
		Size response = sizeR.findById(id).orElseThrow(()-> new Exception("Size non trovata in db"));
		
		sizeR.delete(response);
		
	}

}
