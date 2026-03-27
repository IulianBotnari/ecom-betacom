package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.request.product.ProudctUpdate;
import com.betacom.dto.response.product.ProductsDTO;
import com.betacom.dto.response.size.SizeDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.dto_mappers.map_model.ModelMappers;
import com.betacom.enums.Genders;
import com.betacom.enums.Sizes;
import com.betacom.model.Category;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.repository.CategoryRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.repository.SizeRepository;
import com.betacom.services.interfaces.InterfaceProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductServiceImpl implements InterfaceProductService{
	
	private final ProductRepository productR;
	private final CategoryRepository categoryR;
	private final SizeRepository sizeR;
	private final ModelMappers modelM;
	
	@Override
	public ProductsDTO getById(Long id) throws Exception {
		
		Product product = productR.findById(id).orElseThrow(()-> new Exception("Prodotto non trovato"));
		
		return DtoResponseMapper.productsDTO(product);
	}

	@Override
	public List<ProductsDTO> list() throws Exception {
		List<Product> lista = productR.findAll();
		return lista.stream().map(el -> DtoResponseMapper.productsDTO(el)).collect(Collectors.toList());
	}
	
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void create(ProductRequest request) throws Exception {
		if (request.getCategoryId() == null) throw new Exception("Campo categoria id non puo essere vuoto");
		if (request.getPrice() == null) throw new Exception("Campo prezzo non puo essere vuoto");
		// request.setDiscount(request.getDiscount());
		Category category = categoryR.findById(request.getCategoryId()).orElseThrow(()-> new Exception("Categoria non trovata"));
		if(request.getDiscount()!= null) {
			request.setPrice(request.getPrice() - request.getDiscount());
		}
		Product product = modelM.product(request, category);
		
		product = productR.save(product);
		
		Size size = new Size();
		
		size.setProduct(product);
		size.setQuantity(request.getQuantity());
		try {
	        size.setSize(Sizes.valueOf(request.getSize().toUpperCase()));
	    } catch (IllegalArgumentException e) {
	        throw new Exception("Taglia non valida: " + request.getSize());
	    }
		
		
		
		sizeR.save(size);

		
	}

	@Override
	public void update(ProudctUpdate request) throws Exception {
	
		Product product = productR.findById(request.getId()).orElseThrow(()-> new Exception("Prodotto non trovato in db"));
		
		if (request.getCategoryId()!= null) {
		Category category = categoryR.findById(request.getCategoryId()).orElseThrow(()->new Exception("Categoria non trovata in db"));
		product.setCategory(category);
		}
		
		if(request.getDescription()!= null) {
			product.setDescription(request.getDescription());
		}
		
		if(request.getGender() != null) {
			product.setGender(Genders.valueOf(request.getGender()));
		}
		
		if (request.getImage() != null) {
			product.setImage(request.getImage());
		}
		
		if(request.getMaterial()!= null) {
			product.setMaterial(request.getMaterial());
		}
		
		if(request.getPrice() != null) {
			product.setPrice(request.getPrice());
		}
		
		if(request.getDiscount() != null) {
			product.setDiscount(request.getDiscount());
		}
		
		if(request.getName() != null) {
			product.setName(request.getName());
		}
		
		
		
		productR.save(product);
		
	}

	@Override
	public void delete(Long id) throws Exception {
		Product product = productR.findById(id).orElseThrow(()-> new Exception("Prodotto non trovato"));
		productR.delete(product);
		
	}
	
	@Override
	public Product getProductModelById(Long id) throws Exception {
		
		Product product = productR.findById(id).orElseThrow(()-> new Exception("Prodotto non trovato"));		
		return product;
	}
	
	@Override
	public List<? extends ProductsDTO> multiFilter(Long id,                
	        String name,
	        Long categoryId,
	        Genders gender,
	        String material,
	        Double price) throws Exception {
		List<Product> prodotti = productR.findProductsByFilters(
		        id,
		        name != null ? name + "%" : null,
		        categoryId,
		        gender,
		        material != null ? material + "%" : null,
		        price
		);
	    return prodotti.stream().map(p -> 
	    		DtoResponseMapper.productsDTO(p))
	    		.collect(Collectors.toList()) ;
		}

}
