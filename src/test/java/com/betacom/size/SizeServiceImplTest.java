package com.betacom.size;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.betacom.dto.request.size.SizeRequest;
import com.betacom.dto.response.size.SizeDTO;
import com.betacom.dto_mappers.map_model.ModelMappers;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.repository.ProductRepository;
import com.betacom.repository.SizeRepository;
import com.betacom.services.implementations.SizeServiceImpl;

class SizeServiceImplTest {

	@Mock
    private SizeRepository sizeR;
    @Mock
    private ProductRepository productR;
    @Mock
    private ModelMappers modelM;

    @InjectMocks
    private SizeServiceImpl sizeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- 1. GET BY ID ---
    @Test
    void testGetById_Success() throws Exception {
        Size size = new Size();
        size.setId(1L);
        when(sizeR.findById(1L)).thenReturn(Optional.of(size));

        SizeDTO result = sizeService.getById(1L);
        assertNotNull(result);
    }

    @Test
    void testGetById_NotFound() {
        when(sizeR.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> sizeService.getById(1L));
    }

    // --- 2. LIST ---
    @Test
    void testList_Success() throws Exception {
        List<Size> list = new ArrayList<>();
        list.add(new Size());
        when(sizeR.findAll()).thenReturn(list);

        List<SizeDTO> result = sizeService.list();
        assertEquals(1, result.size());
    }

    // --- 3. CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        SizeRequest req = new SizeRequest();
        req.setProductId(1L);
        
        Product p = new Product();
        when(productR.findById(1L)).thenReturn(Optional.of(p));
        when(modelM.size(any(), any())).thenReturn(new Size());

        sizeService.create(req);
        verify(sizeR, times(1)).save(any());
    }

    @Test
    void testCreate_ProductNotFound() {
        SizeRequest req = new SizeRequest();
        req.setProductId(99L);
        when(productR.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> sizeService.create(req));
    }

    // --- 4. UPDATE (Copre tutti gli IF) ---
    @Test
    void testUpdate_FullSuccess() throws Exception {
        SizeRequest req = new SizeRequest();
        req.setId(10L);
        req.setProductId(1L);
        req.setSize("S"); // Deve essere un valore valido dell'enum Sizes
        req.setQuantity(50);

        Size existingSize = new Size();
        Product p = new Product();

        when(sizeR.findById(10L)).thenReturn(Optional.of(existingSize));
        when(productR.findById(10L)).thenReturn(Optional.of(p)); // Nota: il tuo service usa request.getId() nel findById del prodotto nell'update

        sizeService.update(req);
        
        verify(sizeR, times(1)).save(existingSize);
        assertEquals(50, existingSize.getQuantity());
    }

    @Test
    void testUpdate_SizeNotFound() {
        SizeRequest req = new SizeRequest();
        req.setId(1L);
        when(sizeR.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> sizeService.update(req));
    }

    // --- 5. DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        Size size = new Size();
        when(sizeR.findById(1L)).thenReturn(Optional.of(size));

        sizeService.delete(1L);
        verify(sizeR, times(1)).delete(size);
    }}