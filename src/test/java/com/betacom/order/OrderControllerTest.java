package com.betacom.order;

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

import com.betacom.controllers.OrderController;
import com.betacom.services.interfaces.InterfaceOrderService;


public class OrderControllerTest {
	private MockMvc mockMvc;

    @Mock
    private InterfaceOrderService orderS;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(orderController).build();
    }

    // --- 1. TEST CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        doNothing().when(orderS).create(any());
        mockMvc.perform(post("/rest/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }

    @Test
    void testCreate_Error() throws Exception {
        doThrow(new RuntimeException("Errore creazione")).when(orderS).create(any());
        mockMvc.perform(post("/rest/order/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore creazione"));
    }

    // --- 2. TEST UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        doNothing().when(orderS).update(any());
        mockMvc.perform(put("/rest/order/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Salvataggio completato"));
    }

    @Test
    void testUpdate_Error() throws Exception {
        doThrow(new RuntimeException()).when(orderS).update(any());
        mockMvc.perform(put("/rest/order/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }

    // --- 3. TEST DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(orderS).delete(anyLong());
        mockMvc.perform(delete("/rest/order/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    @Test
    void testDelete_Error() throws Exception {
        doThrow(new RuntimeException()).when(orderS).delete(anyLong());
        mockMvc.perform(delete("/rest/order/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }

    // --- 4. TEST LIST ALL ---
    @Test
    void testListAll_Success() throws Exception {
        when(orderS.list()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/rest/order/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAll_Error() throws Exception {
        when(orderS.list()).thenThrow(new RuntimeException());
        mockMvc.perform(get("/rest/order/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }

    // --- 5. TEST FIND BY ID ---
    @Test
    void testFindById_Success() throws Exception {
        when(orderS.getById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/rest/order/findById").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Error() throws Exception {
        when(orderS.getById(anyLong())).thenThrow(new RuntimeException("ID non trovato"));
        mockMvc.perform(get("/rest/order/findById").param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID non trovato"));
    }
}
