package com.betacom.user;

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

import com.betacom.controllers.UserController;
import com.betacom.dto.request.login.LoginRequest;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.dto.request.user.UserUpdateRequest;
import com.betacom.dto.response.login.LoginDTO;
import com.betacom.dto.response.user.UserDTO;
import com.betacom.enums.Roles;
import com.betacom.services.interfaces.InterfaceUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class UserControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceUserService userS;

    @InjectMocks
    private UserController userController;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    // --- CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        UserCreateRequest req = UserCreateRequest.builder()
                .name("Mario")
                .lastName("Rossi")
                .email("mario@test.com")
                .password("1234")
                .birthday(LocalDate.of(1990, 1, 1))
                .phone("1234567890")
                .build();

        mockMvc.perform(post("/rest/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreate_Error() throws Exception {
        doThrow(new RuntimeException()).when(userS).create(any());

        mockMvc.perform(post("/rest/user/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // --- UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        doNothing().when(userS).update(any());

        UserUpdateRequest req = new UserUpdateRequest();
        req.setId(1L);

        mockMvc.perform(put("/rest/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk())
                .andExpect(content().string("Salvataggio completato"));
    }

    @Test
    void testUpdate_Error() throws Exception {
        doThrow(new RuntimeException()).when(userS).update(any());
        mockMvc.perform(put("/rest/user/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }

    // --- DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(userS).delete(anyLong());
        mockMvc.perform(delete("/rest/user/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    @Test
    void testDelete_Error() throws Exception {
        doThrow(new RuntimeException()).when(userS).delete(anyLong());
        mockMvc.perform(delete("/rest/user/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }

    // --- LIST ALL ---
    @Test
    void testListAll_Success() throws Exception {
        when(userS.list()).thenReturn(new ArrayList<>());
        mockMvc.perform(get("/rest/user/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAll_Error() throws Exception {
        when(userS.list()).thenThrow(new RuntimeException());
        mockMvc.perform(get("/rest/user/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }

    // --- FIND BY ID ---
    @Test
    void testFindById_Success() throws Exception {
        when(userS.getById(anyLong())).thenReturn(new UserDTO());
        mockMvc.perform(get("/rest/user/findById/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Error() throws Exception {
        when(userS.getById(anyLong())).thenThrow(new RuntimeException("Non trovato"));
        mockMvc.perform(get("/rest/user/findById/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Non trovato"));
    }

    // --- LOGIN ---
    @Test
    void testLogin_Success() throws Exception {
        when(userS.login(any(), any(), any())).thenReturn(new LoginDTO());

        LoginRequest req = new LoginRequest();
        req.setEmail("mario@test.com");
        req.setPassword("1234");

        mockMvc.perform(post("/rest/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isOk());
    }

    @Test
    void testLogin_Error() throws Exception {
        when(userS.login(any(), any(), any())).thenThrow(new RuntimeException("Errore login"));

        LoginRequest req = new LoginRequest();
        req.setEmail("mario@test.com");
        req.setPassword("1234");

        mockMvc.perform(post("/rest/user/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore login"));
    }

}