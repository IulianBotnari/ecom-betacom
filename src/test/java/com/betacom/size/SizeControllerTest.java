package com.betacom.size;

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

import com.betacom.controllers.SizeController;
import com.betacom.dto.request.size.SizeRequest;
import com.betacom.services.interfaces.InterfaceSizeService;

class SizeControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceSizeService sizeService;

    @InjectMocks
    private SizeController sizeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Costruiamo il mockMvc isolato sul controller
        this.mockMvc = MockMvcBuilders.standaloneSetup(sizeController).build();
    }

    // --- 1. CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        doNothing().when(sizeService).create(any(SizeRequest.class));

        mockMvc.perform(post("/rest/size/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"taglia\":\"XL\"}")) // JSON di esempio per SizeRequest
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }

    @Test
    void testCreate_Catch() throws Exception {
        doThrow(new RuntimeException("Errore custom")).when(sizeService).create(any());

        mockMvc.perform(post("/rest/size/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore custom"));
    }

    // --- 2. UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        doNothing().when(sizeService).update(any());

        mockMvc.perform(put("/rest/size/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"taglia\":\"L\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Salvataggio completato"));
    }

    @Test
    void testUpdate_Catch() throws Exception {
        doThrow(new RuntimeException()).when(sizeService).update(any());

        mockMvc.perform(put("/rest/size/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }

    // --- 3. DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(sizeService).delete(anyLong());

        mockMvc.perform(delete("/rest/size/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    @Test
    void testDelete_Catch() throws Exception {
        doThrow(new RuntimeException()).when(sizeService).delete(anyLong());

        mockMvc.perform(delete("/rest/size/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }

    // --- 4. LIST ALL ---
    @Test
    void testListAll_Success() throws Exception {
        when(sizeService.list()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rest/size/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAll_Catch() throws Exception {
        when(sizeService.list()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/rest/size/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }

    // --- 5. FIND BY ID ---
    @Test
    void testFindById_Success() throws Exception {
        when(sizeService.getById(anyLong())).thenReturn(null);

        mockMvc.perform(get("/rest/size/findById").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Catch() throws Exception {
        when(sizeService.getById(anyLong())).thenThrow(new RuntimeException("Size non trovata"));

        mockMvc.perform(get("/rest/size/findById").param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Size non trovata"));
    }
}