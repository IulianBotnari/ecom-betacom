package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.category.CategoryRequest;
import com.betacom.dto.request.category.CategoryRequestNoID;
import com.betacom.dto.response.category.CategoryDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.dto_mappers.map_model.ModelMappers;
import com.betacom.model.Category;
import com.betacom.repository.CategoryRepository;
import com.betacom.services.interfaces.InterfaceCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryServiceImpl implements InterfaceCategoryService{
	
	
	private final CategoryRepository categoryR;

	private final ModelMappers modelM;
	
	@Override
	public CategoryDTO getById(Long id) throws Exception {
		Category response = categoryR.findById(id).orElseThrow(()-> new Exception("Categoria non trovata in db"));
		return DtoResponseMapper.categoryDTO(response);
	}
	
	public Category getModelCategoryById(Long id) throws Exception {
		Category response = categoryR.findById(id).orElseThrow(()-> new Exception("Categoria non trovata in db"));
		return response;
	}
	
	@Override
	public Category getCateoryModelById(Long id) throws Exception {
		Category response = categoryR.findById(id).orElseThrow(()-> new Exception("Categoria non trovata in db"));
		return response;
	}

	@Override
	public List<CategoryDTO> list() throws Exception {
		List<Category> list = categoryR.findAll();
		
		return list.stream().map(el -> DtoResponseMapper.categoryDTO(el)).collect(Collectors.toList());
	}

	@Override
	public void create(CategoryRequestNoID request) throws Exception {
		if(request.getCategory() == null) throw new Exception("Campo nome non inserito!");
		
		request.getCategory().toUpperCase().trim();
		
		categoryR.save(modelM.category(request));
		
	}

	@Override
	public void update(CategoryRequest request) throws Exception {
		Category response = categoryR.findById(request.getId()).orElseThrow(()-> new Exception("Categoria non trovata in db"));
		if (request.getCategory() != null) {
			request.getCategory().toUpperCase().trim();
			response.setCategory(request.getCategory());
		}
		
		if(request.getIsView()!=null) {
			request.setIsView(request.getIsView());
		}
		categoryR.save(response);
	}

	@Override
	public void delete(Long id) throws Exception {
		Category response = categoryR.findById(id).orElseThrow(()-> new Exception("Categoria non trovata in db"));
		
		categoryR.delete(response);
		
		
	}


}
