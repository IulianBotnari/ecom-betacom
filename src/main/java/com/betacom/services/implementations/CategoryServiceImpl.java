package com.betacom.services.implementations;

import java.util.List;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.category.CategoryRequest;
import com.betacom.dto.response.category.CategoryDTO;
import com.betacom.services.interfaces.InterfaceCategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryServiceImpl implements InterfaceCategoryService{@Override
	public CategoryDTO getById(Long id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<CategoryDTO> list() throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void create(CategoryRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(CategoryRequest request) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Long id) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
