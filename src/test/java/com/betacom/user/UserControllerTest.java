package com.betacom.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.betacom.controllers.UserController;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.dto.request.user.UserUpdateRequest;
import com.betacom.dto.response.user.UserDTO;
import com.betacom.enums.Roles;
import com.betacom.services.interfaces.InterfaceUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

class UserControllerTest {

	private MockMvc mockMvc;

    @Mock
    private InterfaceUserService userS;

    @InjectMocks
    private UserController userController;
    
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Utilizziamo lo standaloneSetup per isolare il controller
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void create() throws Exception {
        UserCreateRequest request = UserCreateRequest.builder()
                .name("Mario")
                .lastName("Rossi")
                .birthday(LocalDate.of(1990, 1, 1))
                .codiceFiscale("RSSMRA90A01H501U")
                .email("mario.rossi@example.com")
                .password("password123")
                .phone("1234567890")
                .role(Roles.USER)
                .build();

        mockMvc.perform(post("/rest/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }

    @Test
    void createFail() throws Exception {
    	UserCreateRequest request = UserCreateRequest.builder()
                .name("Mario")
                .lastName("Rossi")
                .birthday(LocalDate.of(1990, 1, 1))
                .codiceFiscale("RSSMRA90A01H501U")
                .email("mario.rossi@example.com")
                .password("password123")
                .phone("1234567890")
                .role(Roles.USER)
                .build();

        doThrow(new RuntimeException("Errore"))
            .when(userS).create(any());

        mockMvc.perform(post("/rest/user/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void update() throws Exception {
    	 UserUpdateRequest request = UserUpdateRequest.builder()
    			 .id((long) 1)
                 .name("Dario")
                 .build();

         mockMvc.perform(put("/rest/user/update")
                         .contentType(MediaType.APPLICATION_JSON)
                         .content(objectMapper.writeValueAsString(request)))
                 .andExpect(status().isOk())
                 .andExpect(content().string("Salvataggio completato"));
    }
    
    @Test
    void updateFail() throws Exception {
    	UserUpdateRequest request = UserUpdateRequest.builder()
   			 .id((long) 10)
                .name("Dario")
                .build();

    	doThrow(new RuntimeException("Utente non trovato"))
        .when(userS).update(any());

		mockMvc.perform(put("/rest/user/update").
				contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
		.andExpect(status().isBadRequest());
    }
    
    @Test
    void delete() throws Exception {

        doNothing().when(userS).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/user/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }
    
    @Test
    void deleteFail() throws Exception {

        doThrow(new RuntimeException("Errore"))
                .when(userS).delete(anyLong());

        mockMvc.perform(MockMvcRequestBuilders.delete("/rest/user/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }
    
    @Test
    void listAll() throws Exception {

        when(userS.list()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rest/user/listAll"))
                .andExpect(status().isOk());
    }
    
    @Test
    void listAllFail() throws Exception {

        when(userS.list()).thenThrow(new RuntimeException("Errore"));

        mockMvc.perform(get("/rest/user/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }
    
    @Test
    void findById() throws Exception {

        when(userS.getById(anyLong())).thenReturn(new UserDTO());

        mockMvc.perform(get("/rest/user/findById")
                        .param("id", "1"))
                .andExpect(status().isOk());
    }
    
    @Test
    void findByIdFail() throws Exception {

        when(userS.getById(anyLong()))
                .thenThrow(new RuntimeException("Utente non trovato"));

        mockMvc.perform(get("/rest/user/findById")
                        .param("id", "1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Utente non trovato"));
    }
}