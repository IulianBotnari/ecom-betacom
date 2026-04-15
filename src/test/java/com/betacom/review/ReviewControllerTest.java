package com.betacom.review;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.betacom.controllers.ReviewController;
import com.betacom.services.interfaces.InterfaceReviewService;

class ReviewControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceReviewService reviewS;

    @InjectMocks
    private ReviewController reviewController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(reviewController).build();
    }

    @Test
    void testCreate_Success() throws Exception {
        doNothing().when(reviewS).create(any());

        mockMvc.perform(post("/rest/review/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"productId\":2,\"rating\":5,\"review\":\"top\"}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreate_Error() throws Exception {
        doThrow(new RuntimeException()).when(reviewS).create(any());

        mockMvc.perform(post("/rest/review/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(reviewS).delete(anyLong());

        mockMvc.perform(delete("/rest/review/delete?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete_Error() throws Exception {
        doThrow(new RuntimeException()).when(reviewS).delete(anyLong());

        mockMvc.perform(delete("/rest/review/delete?id=1"))
                .andExpect(status().isBadRequest());
    }
}