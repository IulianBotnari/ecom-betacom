package com.betacom.paymentMethod;
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

import com.betacom.controllers.PaymentMethodController;
import com.betacom.dto.request.payment_method.PaymentMethodRequest;
import com.betacom.dto.response.payment_method.PaymentMethodDTO;
import com.betacom.services.interfaces.InterfacePaymentMethodService;

public class PaymentMethodControllerTest {

    private MockMvc mockMvc;

    @Mock private InterfacePaymentMethodService pmS;
    @InjectMocks private PaymentMethodController paymentMethodController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(paymentMethodController).build();
    }

    @Test
    void testCreate_Success() throws Exception {
        doNothing().when(pmS).create(any(PaymentMethodRequest.class));

        mockMvc.perform(post("/rest/paymentMethod/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Test\", \"userId\":1}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Creazione avvenuta con successo"));
    }
    
    @Test
    void testCreate_Failure() throws Exception {
        doThrow(new RuntimeException("DB Error")).when(pmS).create(any(PaymentMethodRequest.class));

        mockMvc.perform(post("/rest/paymentMethod/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Test\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Errore durnate il salvataggio"));
    }

    @Test
    void testUpdate_Success() throws Exception {
        doNothing().when(pmS).update(any(PaymentMethodRequest.class));

        mockMvc.perform(put("/rest/paymentMethod/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Update\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Salvataggio completato"));
    }
    
    @Test
    void testUpdate_Failure() throws Exception {
        doThrow(new RuntimeException("Update Error")).when(pmS).update(any(PaymentMethodRequest.class));

        mockMvc.perform(put("/rest/paymentMethod/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"description\":\"Update\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Salvataggio non riuscito"));
    }

    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(pmS).delete(1L);

        mockMvc.perform(delete("/rest/paymentMethod/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Eliminazione completata"));
    }
    
    @Test
    void testDelete_Failure() throws Exception {
        doThrow(new Exception("Delete Error")).when(pmS).delete(1L);

        mockMvc.perform(delete("/rest/paymentMethod/delete/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Eliminazione non riuscita"));
    }

    @Test
    void testListAll_Success() throws Exception {
        mockMvc.perform(get("/rest/paymentMethod/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Success() throws Exception {
        when(pmS.getById(1L)).thenReturn(new PaymentMethodDTO());

        mockMvc.perform(get("/rest/paymentMethod/findById").param("id", "1"))
                .andExpect(status().isOk());
    }

    @Test
    void testFindById_Error() throws Exception {
        when(pmS.getById(99L)).thenThrow(new Exception("Metodo non trovato"));

        mockMvc.perform(get("/rest/paymentMethod/findById").param("id", "99"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Metodo non trovato"));
    }
}
