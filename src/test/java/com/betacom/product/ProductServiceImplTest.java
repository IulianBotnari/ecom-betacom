package com.betacom.product;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.request.product.ProudctUpdate;
import com.betacom.dto.response.product.ProductsDTO;
import com.betacom.dto_mappers.map_model.ModelMappers;
import com.betacom.model.Category;
import com.betacom.model.Product;
import com.betacom.repository.CategoryRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.services.implementations.ProductServiceImpl;

class ProductServiceImplTest {

    @Mock
    private ProductRepository productR;
    @Mock
    private CategoryRepository categoryR;
    @Mock
    private ModelMappers modelM;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- 1. TEST GET BY ID ---
    @Test
    void testGetById_Success() throws Exception {
        Product p = new Product();
        p.setId(1L);
        when(productR.findById(1L)).thenReturn(Optional.of(p));

        ProductsDTO result = productService.getById(1L);
        assertNotNull(result);
    }

    @Test
    void testGetById_NotFound() {
        when(productR.findById(1L)).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> productService.getById(1L));
    }

    // --- 2. TEST LIST ---
    @Test
    void testList_Success() throws Exception {
        List<Product> lista = new ArrayList<>();
        lista.add(new Product());
        when(productR.findAll()).thenReturn(lista);

        List<ProductsDTO> result = productService.list();
        assertEquals(1, result.size());
    }

    // --- 3. TEST CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        ProductRequest req = new ProductRequest();
        req.setCategoryId(1L);
        req.setPrice(10.0);
        req.setId(1L); // Id categoria usato nel findById del service

        Category cat = new Category();
        when(categoryR.findById(1L)).thenReturn(Optional.of(cat));
        when(modelM.product(any(), any())).thenReturn(new Product());

        productService.create(req);
        verify(productR, times(1)).save(any());
    }

    @Test
    void testCreate_ValidationErrors() {
        ProductRequest req = new ProductRequest();
        // Caso categoria nulla
        assertThrows(Exception.class, () -> productService.create(req));
        
        req.setCategoryId(1L);
        // Caso prezzo nullo
        assertThrows(Exception.class, () -> productService.create(req));
    }

    // --- 4. TEST UPDATE (Copre tutti gli IF) ---
    @Test
    void testUpdate_FullSuccess() throws Exception {
        ProudctUpdate req = new ProudctUpdate();
        req.setId(1L);
        req.setCategoryId(2L);
        req.setDescription("Nuova");
        req.setGender("MAN"); // Deve essere un valore valido dell'enum Genders
        req.setName("Test");

        Product existingProduct = new Product();
        Category newCategory = new Category();

        when(productR.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(categoryR.findById(2L)).thenReturn(Optional.of(newCategory));

        productService.update(req);
        
        verify(productR, times(1)).save(existingProduct);
        assertEquals("Nuova", existingProduct.getDescription());
    }

    @Test
    void testUpdate_ProductNotFound() {
        ProudctUpdate req = new ProudctUpdate();
        req.setId(99L);
        when(productR.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> productService.update(req));
    }

    // --- 5. TEST DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        Product p = new Product();
        when(productR.findById(1L)).thenReturn(Optional.of(p));

        productService.delete(1L);
        verify(productR, times(1)).delete(p);
    }

    // --- 6. TEST GET PRODUCT MODEL BY ID ---
    @Test
    void testGetProductModelById_Success() throws Exception {
        Product p = new Product();
        when(productR.findById(1L)).thenReturn(Optional.of(p));

        Product result = productService.getProductModelById(1L);
        assertNotNull(result);
    }
}