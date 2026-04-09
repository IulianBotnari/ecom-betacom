package com.betacom.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.request.product.ProudctUpdate;
import com.betacom.enums.Genders;
import com.betacom.enums.Sizes;
import com.betacom.model.Category;
import com.betacom.model.OrderedItemsDetails;
import com.betacom.model.Product;
import com.betacom.model.Size;
import com.betacom.repository.CategoryRepository;
import com.betacom.repository.OrderedItemsDetailsRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.repository.SizeRepository;
import com.betacom.services.implementations.ProductServiceImpl;
import com.betacom.services.interfaces.InterfaceUploadService;
import com.betacom.dto_mappers.map_model.ModelMappers;

public class ProductServiceImplTest {

    @Mock private ProductRepository productR;
    @Mock private CategoryRepository categoryR;
    @Mock private SizeRepository sizeR;
    @Mock private OrderedItemsDetailsRepository orderDetR;
    @Mock private ModelMappers modelM;
    @Mock private InterfaceUploadService uploadS;

    @InjectMocks
    private ProductServiceImpl productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- GET BY ID ---
    @Test
    void testGetById_Success() throws Exception {
        Product p = new Product();
        when(productR.findById(1L)).thenReturn(Optional.of(p));

        productService.getById(1L);

        verify(productR).findById(1L);
    }

    @Test
    void testGetById_Fail() {
        when(productR.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> productService.getById(1L));
        assertEquals("Prodotto non trovato", ex.getMessage());
        verify(productR).findById(1L);
    }

    // --- LIST ---
    @Test
    void testList_Success() throws Exception {
        when(productR.findAll()).thenReturn(List.of(new Product(), new Product()));

        List<?> result = productService.list();

        verify(productR).findAll();
        assert(result.size() == 2);
    }

    // --- CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        ProductRequest req = new ProductRequest();
        req.setCategoryId(1L);
        req.setPrice(10.0);
        req.setQuantity(5);
        req.setSize("M"); // Taglia valida

        Category cat = new Category();
        when(categoryR.findById(1L)).thenReturn(Optional.of(cat));
        when(modelM.product(any(), any())).thenReturn(new Product());
        when(productR.save(any())).thenReturn(new Product());
        when(uploadS.saveImage(any())).thenReturn("img.png");

        productService.create(req, null);

        verify(categoryR).findById(1L);
        verify(productR).save(any());
        verify(sizeR).save(any());
    }

    @Test
    void testCreate_Fail_CategoryNull() {
        ProductRequest req = new ProductRequest();
        req.setPrice(10.0);

        Exception ex = assertThrows(Exception.class, () -> productService.create(req, null));
        assertEquals("Campo categoria id non puo essere vuoto", ex.getMessage());
    }

    @Test
    void testCreate_Fail_PriceNull() {
        ProductRequest req = new ProductRequest();
        req.setCategoryId(1L);

        Exception ex = assertThrows(Exception.class, () -> productService.create(req, null));
        assertEquals("Campo prezzo non puo essere vuoto", ex.getMessage());
    }

    // --- UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        ProudctUpdate req = new ProudctUpdate();
        req.setId(1L);
        req.setCategoryId(1L);
        req.setDescription("Nuova descrizione");
        req.setGender("MALE");
        req.setMaterial("Cotone");
        req.setPrice(20.0);
        req.setDiscount(10.0);
        req.setName("Maglietta");

        Product p = new Product();
        Category cat = new Category();

        when(productR.findById(1L)).thenReturn(Optional.of(p));
        when(categoryR.findById(1L)).thenReturn(Optional.of(cat));
        when(productR.save(any())).thenReturn(p);
        when(uploadS.saveImage(any())).thenReturn("img.png");

        productService.update(req, null);

        verify(productR).findById(1L);
        verify(productR).save(any());
    }

    @Test
    void testUpdate_Fail_ProductNotFound() {
        ProudctUpdate req = new ProudctUpdate();
        req.setId(1L);

        when(productR.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> productService.update(req, null));
        assertEquals("Prodotto non trovato in db", ex.getMessage());
    }

    // --- DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        Product p = new Product();
        OrderedItemsDetails od = new OrderedItemsDetails();
        od.setProduct(p);

        when(productR.findById(1L)).thenReturn(Optional.of(p));
        when(orderDetR.findAll()).thenReturn(List.of(od));

        productService.delete(1L);

        verify(productR).delete(p);
        verify(orderDetR).save(od);
    }

    @Test
    void testDelete_Fail() {
        when(productR.findById(1L)).thenReturn(Optional.empty());

        Exception ex = assertThrows(Exception.class, () -> productService.delete(1L));
        assertEquals("Prodotto non trovato", ex.getMessage());
    }

    // --- MULTI FILTER ---
    @Test
    void testMultiFilter_Success() throws Exception {
        when(productR.findProductsByFilters(any(), any(), any(), any(), any(), any()))
                .thenReturn(List.of(new Product(), new Product()));

        List<?> result = productService.multiFilter(null, null, null, null, null, null);
        verify(productR).findProductsByFilters(any(), any(), any(), any(), any(), any());
        assert(result.size() == 2);
    }
}