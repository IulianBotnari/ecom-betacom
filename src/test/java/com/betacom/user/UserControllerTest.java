package com.betacom.user;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.enums.Roles;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule());

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
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
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Creazione avvenuta con successo")));
    }

    @Test
    void createFail() throws Exception {
    	UserCreateRequest request = UserCreateRequest.builder()
//    			.name("Mario")
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
    	.andExpect(status().isBadRequest())
    	.andExpect(content().string(org.hamcrest.Matchers.containsString("Campo name mancante")));
    }
}