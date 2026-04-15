package com.betacom.category;
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

import com.betacom.controllers.CategoryController;
import com.betacom.services.interfaces.InterfaceCategoryService;

class CategoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceCategoryService categoryS;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
    } 

    @Test
    void testCreateSuccess() throws Exception {
        doNothing().when(categoryS).create(any());

        mockMvc.perform(post("/rest/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"descrizione\":\"Elettronica\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }

    @Test
    void testCreateError() throws Exception {
        doThrow(new RuntimeException()).when(categoryS).create(any());

        mockMvc.perform(post("/rest/category/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durnate il salvataggio"));
    }

    @Test
    void testUpdateSuccess() throws Exception {
        doNothing().when(categoryS).update(any());

        mockMvc.perform(put("/rest/category/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"descrizione\":\"Informatica\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Salvataggio completato"));
    }

    @Test
    void testUpdateError() throws Exception {

        doThrow(new RuntimeException()).when(categoryS).update(any());

        mockMvc.perform(put("/rest/category/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }


    @Test
    void testDeleteSuccess() throws Exception {
        doNothing().when(categoryS).delete(anyLong());

        mockMvc.perform(delete("/rest/category/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    @Test
    void testDeleteError() throws Exception {
        doThrow(new RuntimeException()).when(categoryS).delete(anyLong());

        mockMvc.perform(delete("/rest/category/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }

    @Test
    void testListAllSuccess() throws Exception {
        when(categoryS.list()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/rest/category/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAllError() throws Exception {
        when(categoryS.list()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/rest/category/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }

}