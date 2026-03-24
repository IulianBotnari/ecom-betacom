package com.betacom.address;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.betacom.controllers.AddressController;
import com.betacom.controllers.UserController;
import com.betacom.dto.request.address.AddressCreateRequest;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.enums.Roles;
import com.betacom.services.interfaces.InterfaceAddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class AddressControllerTest {
	private MockMvc mockMvc;

    @Mock
    private InterfaceAddressService addressS;

    @InjectMocks
    private AddressController addressController;
    
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Utilizziamo lo standaloneSetup per isolare il controller
        this.mockMvc = MockMvcBuilders.standaloneSetup(addressController).build();
    }

    @Test
    void create() throws Exception {
        AddressCreateRequest request = AddressCreateRequest.builder()
                .city("Padova")
                .street("Via Roma")
                .civic("10")
                .staircase("A")
                .province("PD")
                .cap("35100")
                .country("Italia")
                .residence(true)
                .domicile(false)
                .defaultAddress(true)
                .userId(1L)
                .build();

        mockMvc.perform(post("/rest/address/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }
    
    @Test
    void testCreate_Error() throws Exception {
        doThrow(new RuntimeException("Errore creazione")).when(addressS).create(any());
        mockMvc.perform(post("/rest/address/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // --- 2. TEST UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        doNothing().when(addressS).update(any());
        mockMvc.perform(put("/rest/address/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Salvataggio completato"));
    }

    @Test
    void testUpdate_Error() throws Exception {
        // Questo test causa lo stack trace che vedi in console, ma è corretto!
        doThrow(new RuntimeException("Errore simulato")).when(addressS).update(any());
        mockMvc.perform(put("/rest/address/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }

    // --- 3. TEST DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(addressS).delete(anyLong());
        mockMvc.perform(delete("/rest/address/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    @Test
    void testDelete_Error() throws Exception {
        doThrow(new RuntimeException()).when(addressS).delete(anyLong());
        mockMvc.perform(delete("/rest/address/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }

    // --- 4. TEST LIST ALL ---
    @Test
    void testListAll_Success() throws Exception {
        when(addressS.list()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/rest/address/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAll_Error() throws Exception {
        when(addressS.list()).thenThrow(new RuntimeException());
        mockMvc.perform(get("/rest/address/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }

    // --- 5. TEST FIND BY ID ---
    @Test
    void testFindById_Success() throws Exception {
        // Se il metodo getById restituisce un oggetto, restituiamo null o un'istanza vuota
        when(addressS.getById(anyLong())).thenReturn(null);
        mockMvc.perform(get("/rest/address/findById").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Error() throws Exception {
        when(addressS.getById(anyLong())).thenThrow(new RuntimeException("ID non trovato"));
        mockMvc.perform(get("/rest/address/findById").param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("ID non trovato"));
    }

}
