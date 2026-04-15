package com.betacom.card;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.betacom.controllers.CardController;
import com.betacom.dto.request.card.CardRequest;
import com.betacom.model.Card;
import com.betacom.services.interfaces.InterfaceCardService;

public class CardControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceCardService cardS; // Mock del servizio chiamato dal controller

    @InjectMocks
    private CardController cardController; // Il controller in cui iniettare il mock

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        // Costruiamo l'ambiente di test per il controller
        this.mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    // --- TEST CREATE SUCCESS ---
    @Test
    void testCreate_Success() throws Exception {
        // Mocking: quando cardS.create viene chiamato, ritorna un oggetto Card qualsiasi
        when(cardS.create(any(CardRequest.class))).thenReturn(new Card());

        // Prepariamo il JSON (deve rispettare le tue validazioni @NotBlank, @Pattern ecc.)
        String cardJson = "{"
                + "\"cardHolder\": \"Mario Rossi\","
                + "\"cardNumber\": \"1234567812345678\","
                + "\"expiryDate\": \"12/25\","
                + "\"cvv\": \"123\""
                + "}";

        mockMvc.perform(post("/rest/card/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardJson))
                .andExpect(status().isCreated()) // Verifica che torni 201 CREATED
                .andExpect(content().string("Creazione avvenuta con successo"));
    }

    // --- TEST CREATE FAILURE (Exception) ---
    @Test
    void testCreate_Failure() throws Exception {
        // Simuliamo che il service lanci un'eccezione
        when(cardS.create(any(CardRequest.class))).thenThrow(new Exception("Database error"));

        String cardJson = "{"
                + "\"cardHolder\": \"Mario Rossi\","
                + "\"cardNumber\": \"1234567812345678\","
                + "\"expiryDate\": \"12/25\","
                + "\"cvv\": \"123\""
                + "}";

        mockMvc.perform(post("/rest/card/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardJson))
                .andExpect(status().isBadRequest()) // Verifica che torni 400 BAD REQUEST come nel tuo catch
                .andExpect(content().string("Errore durnate il salvataggio"));
    }
    
    
 // --- TEST UPDATE SUCCESS ---
    @Test
    void testUpdate_Success() throws Exception {
        // Mockiamo il service: non deve fare nulla (void)
        doNothing().when(cardS).update(any(CardRequest.class));

        // Il JSON della carta (senza l'ID, perché l'ID è nell'URL)
        String cardJson = "{"
                + "\"cardHolder\": \"Mario Rossi\","
                + "\"cardNumber\": \"1234567812345678\","
                + "\"expiryDate\": \"12/25\","
                + "\"cvv\": \"123\""
                + "}";

        // Eseguiamo la PUT verso /update/1 (dove 1 è l'ID)
        mockMvc.perform(put("/rest/card/update/1") 
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardJson))
                .andExpect(status().isCreated()) // Nel tuo controller restituisci CREATED
                .andExpect(content().string("Salvataggio completato"));
    }

    // --- TEST UPDATE ERROR ---
    @Test
    void testUpdate_Error() throws Exception {
        // 1. Forza il service a lanciare eccezione anche con dati corretti
        doThrow(new RuntimeException("DB Error")).when(cardS).update(any(CardRequest.class));

        // 2. Invia un JSON COMPLETO (altrimenti si ferma alla validazione @NotBlank)
        String validJson = "{"
                + "\"cardHolder\": \"Mario Rossi\","
                + "\"cardNumber\": \"1234567812345678\","
                + "\"expiryDate\": \"12/25\","
                + "\"cvv\": \"123\""
                + "}";

        mockMvc.perform(put("/rest/card/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(validJson)) 
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }
    
    @Test
    void testUpdate_Controller_NotFound() throws Exception {
        // 1. Mock: il service lancia eccezione perché la carta non c'è
        doThrow(new Exception("card non presente in DB")).when(cardS).update(any(CardRequest.class));

        String cardJson = "{"
                + "\"cardHolder\": \"Mario Rossi\","
                + "\"cardNumber\": \"1234567812345678\","
                + "\"expiryDate\": \"12/25\","
                + "\"cvv\": \"123\""
                + "}";

        // 2. Esecuzione: l'ID 99 non esiste
        mockMvc.perform(put("/rest/card/update/99")
                .contentType(MediaType.APPLICATION_JSON)
                .content(cardJson))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }
    
    @Test
    void testDelete_Success() throws Exception {
        // Mock: il service non deve fare nulla
        doNothing().when(cardS).delete(1L);

        mockMvc.perform(delete("/rest/card/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }

    // --- TEST DELETE ERROR ---
    @Test
    void testDelete_Error() throws Exception {
        // Mock: il service lancia un'eccezione (es. card non trovata)
        doThrow(new Exception("Card non trovata")).when(cardS).delete(99L);

        mockMvc.perform(delete("/rest/card/delete/99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }
    
    
 // --- TEST LIST ALL SUCCESS ---
    @Test
    void testListAll_Success() throws Exception {
        // Mock: il service restituisce una lista vuota (o con dati, non importa per lo status OK)
        when(cardS.list()).thenReturn(new java.util.ArrayList<>());

        mockMvc.perform(get("/rest/card/listAll"))
                .andExpect(status().isOk());
    }

    // --- TEST LIST ALL ERROR ---
    @Test
    void testListAll_Error() throws Exception {
        // Mock: il service lancia un'eccezione
        when(cardS.list()).thenThrow(new RuntimeException("Database error"));

        mockMvc.perform(get("/rest/card/listAll"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durante il recupero della lista"));
    }
    
    
    @Test
    void testFindById_Success() throws Exception {
        // Mock: il service restituisce un DTO
        when(cardS.getById(1L)).thenReturn(new com.betacom.dto.response.card.CardDTO());

        mockMvc.perform(get("/rest/card/findById")
                .param("id", "1")) // Passiamo l'id come RequestParam
                .andExpect(status().isOk());
    }

    // --- TEST FIND BY ID ERROR ---
    @Test
    void testFindById_Error() throws Exception {
        // Simula il service che lancia l'eccezione con il messaggio specifico
        when(cardS.getById(99L)).thenThrow(new Exception("carta non presente in DB"));

        mockMvc.perform(get("/rest/card/findById")
                .param("id", "99"))
                .andExpect(status().isBadRequest())
                // Verifichiamo che il body contenga il messaggio dell'eccezione come nel tuo catch (r = e.getMessage())
                .andExpect(content().string("carta non presente in DB"));
    }
    
    
   
    
}

