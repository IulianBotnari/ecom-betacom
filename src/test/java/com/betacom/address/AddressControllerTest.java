package com.betacom.address;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

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
                
                .build();

        mockMvc.perform(post("/rest/address/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }

}
