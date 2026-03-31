package com.betacom.category;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.betacom.dto.request.category.CategoryRequest;
import com.betacom.dto.request.category.CategoryRequestNoID;
import com.betacom.dto.response.category.CategoryDTO;
import com.betacom.dto_mappers.map_model.ModelMappers;
import com.betacom.model.Category;
import com.betacom.repository.CategoryRepository;
import com.betacom.services.implementations.CategoryServiceImpl;

class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryR;

    @Mock
    private ModelMappers modelM;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- 1. TEST RECUPERO (Copre i 3 metodi simili) ---
    @Test
    void testGetById_Success() throws Exception {
        Category cat = new Category();
        cat.setCategory("Elettronica");
        when(categoryR.findById(1L)).thenReturn(Optional.of(cat));

        CategoryDTO result = categoryService.getById(1L);
        assertNotNull(result);
    }

    @Test
    void testGetModelMethods_Success() throws Exception {
        Category cat = new Category();
        when(categoryR.findById(1L)).thenReturn(Optional.of(cat));

        // Chiamiamo entrambi i metodi "model" per coprire le righe
        Category res1 = categoryService.getModelCategoryById(1L);
        Category res2 = categoryService.getCateoryModelById(1L);

        assertNotNull(res1);
        assertNotNull(res2);
    }

    @Test
    void testGetById_NotFound() {
        when(categoryR.findById(any())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> categoryService.getById(1L));
    }

    // --- 2. TEST LIST ---
    @Test
    void testList_Success() throws Exception {
        List<Category> list = new ArrayList<>();
        list.add(new Category());
        when(categoryR.findAll()).thenReturn(list);

        List<CategoryDTO> result = categoryService.list();
        assertEquals(1, result.size());
    }

    // --- 3. TEST CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        CategoryRequestNoID req = new CategoryRequestNoID();
        req.setCategory("Test");
        
        when(modelM.category(any(CategoryRequest.class))).thenReturn(new Category());

        categoryService.create(req);
        verify(categoryR, times(1)).save(any());
    }

    @Test
    void testCreate_ValidationError() {
    	CategoryRequestNoID req = new CategoryRequestNoID();
        req.setCategory(null); // Forza l'eccezione dell'IF

        Exception exception = assertThrows(Exception.class, () -> categoryService.create(req));
        assertEquals("Campo nome non inserito!", exception.getMessage());
    }

    // --- 4. TEST UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        CategoryRequest req = new CategoryRequest();
        req.setId(1L);
        req.setCategory("Update");

        Category existing = new Category();
        when(categoryR.findById(1L)).thenReturn(Optional.of(existing));

        categoryService.update(req);
        
        verify(categoryR, times(1)).save(existing);
        assertEquals("Update", existing.getCategory());
    }

    @Test
    void testUpdate_OnlyId() throws Exception {
        // Testiamo l'IF quando il nome categoria è null nell'update
        CategoryRequest req = new CategoryRequest();
        req.setId(1L);
        req.setCategory(null);

        Category existing = new Category();
        when(categoryR.findById(1L)).thenReturn(Optional.of(existing));

        categoryService.update(req);
        verify(categoryR).save(existing);
    }

    // --- 5. TEST DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        Category cat = new Category();
        when(categoryR.findById(1L)).thenReturn(Optional.of(cat));

        categoryService.delete(1L);
        verify(categoryR, times(1)).delete(cat);
    }
}