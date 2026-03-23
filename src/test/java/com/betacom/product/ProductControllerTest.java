package com.betacom.product;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.betacom.controllers.ProductController;
import com.betacom.services.interfaces.InterfaceProductService;

class ProductControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceProductService productS;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Utilizziamo lo standaloneSetup per isolare il controller
        this.mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    // --- 1. TEST CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        doNothing().when(productS).create(any());
        mockMvc.perform(post("/rest/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }

    @Test
    void testCreate_Error() throws Exception {
        doThrow(new RuntimeException("Errore creazione")).when(productS).create(any());
        mockMvc.perform(post("/rest/product/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore creazione"));
    }

    // --- 2. TEST UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        doNothing().when(productS).update(any());
        mockMvc.perform(put("/rest/product/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Salvataggio completato"));
    }

    @Test
    void testUpdate_Error() throws Exception {
        // Questo test causa lo stack trace che vedi in console, ma è corretto!
        doThrow(new RuntimeException("Errore simulato")).when(productS).update(any());
        mockMvc.perform(put("/rest/product/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }

    // --- 3. TEST DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(productS).delete(anyLong());
        mockMvc.perform(delete("/rest/product/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    @Test
    void testDelete_Error() throws Exception {
        doThrow(new RuntimeException()).when(productS).delete(anyLong());
        mockMvc.perform(delete("/rest/product/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }

    // --- 4. TEST LIST ALL ---
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

    // --- 5. TEST FIND BY ID ---
    @Test
    void testFindById_Success() throws Exception {
        // Se il metodo getById restituisce un oggetto, restituiamo null o un'istanza vuota
        when(productS.getById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/rest/product/findById").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Error() throws Exception {
        when(productS.getById(anyLong())).thenThrow(new RuntimeException("ID non trovato"));
        mockMvc.perform(get("/rest/product/findById").param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID non trovato"));
    }
}