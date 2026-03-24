package com.betacom.cart;

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

import com.betacom.controllers.CartController;
import com.betacom.dto.request.cart.CartRequest;
import com.betacom.services.interfaces.InterfaceCartService;

class CartControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceCartService cartS;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(cartController).build();
    }

    // --- 1. UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        doNothing().when(cartS).update(any(CartRequest.class));

        mockMvc.perform(put("/rest/cart/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1}")) // JSON minimo per la request
                .andExpect(status().isCreated())
                .andExpect(content().string("Salvataggio completato"));
    }

    @Test
    void testUpdate_Catch() throws Exception {
        doThrow(new RuntimeException()).when(cartS).update(any());

        mockMvc.perform(put("/rest/cart/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }

    // --- 2. DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(cartS).delete(anyLong());

        mockMvc.perform(delete("/rest/cart/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    @Test
    void testDelete_Catch() throws Exception {
        doThrow(new RuntimeException()).when(cartS).delete(anyLong());

        mockMvc.perform(delete("/rest/cart/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }

    // --- 3. LIST ALL ---
    @Test
    void testListAll_Success() throws Exception {
        when(cartS.list()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rest/cart/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAll_Catch() throws Exception {
        when(cartS.list()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/rest/cart/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }

    // --- 4. FIND BY ID ---
    @Test
    void testFindById_Success() throws Exception {
        when(cartS.getById(anyLong())).thenReturn(null); // Ritorna null o un DTO finto

        mockMvc.perform(get("/rest/cart/findById").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Catch() throws Exception {
        // Il tuo controller nel catch di findById fa: r = e.getMessage()
        String errorMsg = "Cart non presente nel DB";
        when(cartS.getById(anyLong())).thenThrow(new RuntimeException(errorMsg));

        mockMvc.perform(get("/rest/cart/findById").param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMsg));
    }
}