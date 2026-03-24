package com.betacom.cartItem;


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

import com.betacom.controllers.CartItemController;
import com.betacom.dto.request.cart_item.CartItemRequest;
import com.betacom.services.interfaces.InterfaceCartItemService;

class CartItemControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceCartItemService cartItmS;

    @InjectMocks
    private CartItemController cartItemController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        this.mockMvc = MockMvcBuilders.standaloneSetup(cartItemController).build();
    }

   
    @Test
    void testCreate_Success() throws Exception {
        doNothing().when(cartItmS).create(any(CartItemRequest.class));

        mockMvc.perform(post("/rest/cartItem/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"productId\":1, \"quantity\":2}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }

    @Test
    void testCreate_Catch() throws Exception {
        doThrow(new RuntimeException()).when(cartItmS).create(any());

        mockMvc.perform(post("/rest/cartItem/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durnate il salvataggio"));
    }

   
    @Test
    void testUpdate_Success() throws Exception {
        doNothing().when(cartItmS).update(any(CartItemRequest.class));

        mockMvc.perform(put("/rest/cartItem/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cartItemId\":1, \"quantity\":5}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Salvataggio completato"));
    }

    @Test
    void testUpdate_Catch() throws Exception {
        doThrow(new RuntimeException()).when(cartItmS).update(any());

        mockMvc.perform(put("/rest/cartItem/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }

   
    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(cartItmS).delete(anyLong());

        // Nota: nel tuo controller il metodo delete si chiama erroneamente 'create', 
        // ma risponde all'indirizzo /delete/{id}
        mockMvc.perform(delete("/rest/cartItem/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    @Test
    void testDelete_Catch() throws Exception {
        doThrow(new RuntimeException()).when(cartItmS).delete(anyLong());

        mockMvc.perform(delete("/rest/cartItem/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }

   
    @Test
    void testListAll_Success() throws Exception {
        when(cartItmS.list()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rest/cartItem/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAll_Catch() throws Exception {
        when(cartItmS.list()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/rest/cartItem/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }

   
    @Test
    void testFindById_Success() throws Exception {
        when(cartItmS.getById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/rest/cartItem/findById").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Catch() throws Exception {
        String errorMsg = "elemento carrello non presente in DB";
        when(cartItmS.getById(anyLong())).thenThrow(new RuntimeException(errorMsg));

        mockMvc.perform(get("/rest/cartItem/findById").param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMsg));
    }
}