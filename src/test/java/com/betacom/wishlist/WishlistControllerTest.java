package com.betacom.wishlist;

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

import com.betacom.controllers.WishListController;
import com.betacom.services.interfaces.InterfaceWhishListService;

class WishListControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InterfaceWhishListService wishListS;

    @InjectMocks
    private WishListController wishListController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wishListController).build();
    }

    // --- CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        doNothing().when(wishListS).create(any());

        mockMvc.perform(post("/rest/wishlist/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"userId\":1,\"productId\":2}"))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreate_Error() throws Exception {
        doThrow(new RuntimeException()).when(wishListS).create(any());

        mockMvc.perform(post("/rest/wishlist/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().isBadRequest());
    }

    // --- DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        doNothing().when(wishListS).delete(anyLong());

        mockMvc.perform(delete("/rest/wishlist/delete?id=1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDelete_Error() throws Exception {
        doThrow(new RuntimeException()).when(wishListS).delete(anyLong());

        mockMvc.perform(delete("/rest/wishlist/delete?id=1"))
                .andExpect(status().isBadRequest());
    }

    // --- LIST ---
    @Test
    void testListAll_Success() throws Exception {
        when(wishListS.list()).thenReturn(java.util.List.of());

        mockMvc.perform(get("/rest/wishlist/listAll"))
                .andExpect(status().isOk());
    }

    @Test
    void testListAll_Error() throws Exception {
        when(wishListS.list()).thenThrow(new RuntimeException());

        mockMvc.perform(get("/rest/wishlist/listAll"))
                .andExpect(status().isBadRequest());
    }
}