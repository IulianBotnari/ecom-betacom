package com.betacom.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.betacom.controllers.ProductController;
import com.betacom.dto.request.product.ProductRequest;
import com.betacom.dto.request.product.ProudctUpdate;
import com.betacom.enums.Genders;
import com.betacom.services.interfaces.InterfaceProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceProductService productS;

    @InjectMocks
    private ProductController productController;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    // --- CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        doNothing().when(productS).create(any(), any());

        ProductRequest request = new ProductRequest();
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());

        MockMultipartFile product = new MockMultipartFile("product", "", "application/json",
                objectMapper.writeValueAsBytes(request));

        mockMvc.perform(multipart("/rest/product/create").file(file).file(product))
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }

    @Test
    void testCreate_Error() throws Exception {
        doThrow(new RuntimeException("Errore creazione")).when(productS).create(any(), any());

        ProductRequest request = new ProductRequest();
        MockMultipartFile file = new MockMultipartFile("file", "test.png", "image/png", "test".getBytes());
        MockMultipartFile product = new MockMultipartFile("product", "", "application/json",
                objectMapper.writeValueAsBytes(request));

        mockMvc.perform(multipart("/rest/product/create").file(file).file(product))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore creazione"));
    }

    // --- UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        doNothing().when(productS).update(any(), any());

        ProudctUpdate request = new ProudctUpdate();
        MockMultipartFile product = new MockMultipartFile("product", "", "application/json",
                objectMapper.writeValueAsBytes(request));

        mockMvc.perform(multipart("/rest/product/update").file(product))
                .andExpect(status().isCreated())
                .andExpect(content().string("Salvataggio completato"));
    }

    @Test
    void testUpdate_Error() throws Exception {
        doThrow(new RuntimeException()).when(productS).update(any(), any());

        ProudctUpdate request = new ProudctUpdate();
        MockMultipartFile product = new MockMultipartFile("product", "", "application/json",
                objectMapper.writeValueAsBytes(request));

        mockMvc.perform(multipart("/rest/product/update").file(product))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }

    // --- DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(productS).delete(anyLong());

        mockMvc.perform(delete("/rest/product/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    @Test
    void testDelete_Error() throws Exception {
        doThrow(new RuntimeException("Errore")).when(productS).delete(anyLong());

        mockMvc.perform(delete("/rest/product/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscitaErrore"));
    }

    // --- LIST ALL ---
    @Test
    void testListAll_Success() throws Exception {
        when(productS.list()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rest/product/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAll_Error() throws Exception {
        when(productS.list()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/rest/product/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }

    // --- FIND BY ID ---
    @Test
    void testFindById_Success() throws Exception {
        when(productS.getById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/rest/product/findById/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Error() throws Exception {
        when(productS.getById(anyLong())).thenThrow(new RuntimeException("Prodotto non trovato"));

        mockMvc.perform(get("/rest/product/findById/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Prodotto non trovato"));
    }

    // --- MULTI FILTER ---
    @Test
    void testMultiFilter_Success() throws Exception {
        when(productS.multiFilter(any(), any(), any(), any(), any(), any()))
                .thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rest/product/multiFilter"))
                .andExpect(status().isOk());
    }

    @Test
    void testMultiFilter_Error() throws Exception {
        when(productS.multiFilter(any(), any(), any(), any(), any(), any()))
                .thenThrow(new RuntimeException());

        mockMvc.perform(get("/rest/product/multiFilter"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Impossibile recuperare i prodotti"));
    }
}