package com.betacom.cart;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.betacom.dto.request.cart.CartRequest;
import com.betacom.dto.response.cart.CartDTO;
import com.betacom.model.Cart;
import com.betacom.model.User;
import com.betacom.repository.CartRepository;
import com.betacom.repository.UserRepository;
import com.betacom.services.implementations.CartServiceImpl;

class CartServiceImplTest {

    @Mock
    private CartRepository cartR;

    @Mock
    private UserRepository userR;

    @InjectMocks
    private CartServiceImpl cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- 1. GET BY ID ---
    @Test
    void testGetById_Success() throws Exception {
        Cart cart = new Cart();
        cart.setId(1L);
        
        
        when(cartR.findById(1L)).thenReturn(Optional.of(cart));

        CartDTO result = cartService.getById(1L);
        assertNotNull(result);
    }

    @Test
    void testGetById_NotFound() {
        when(cartR.findById(anyLong())).thenReturn(Optional.empty());
        
        Exception exception = assertThrows(Exception.class, () -> cartService.getById(1L));
        assertEquals("cart non prsente nel DB", exception.getMessage());
    }

    // --- 2. LIST ---
    @Test
    void testList_Success() throws Exception {
        List<Cart> list = new ArrayList<>();
        list.add(new Cart());
        when(cartR.findAll()).thenReturn(list);

        List<CartDTO> result = cartService.list();
        assertEquals(1, result.size());
    }

    // --- 3. CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        CartRequest req = new CartRequest();
        req.setUserId(1L);
        
        User user = new User();
        user.setId(1L);

        when(userR.findById(1L)).thenReturn(Optional.of(user));

        cartService.create(req);
        
       
        verify(cartR, times(1)).save(any(Cart.class));
    }

    @Test
    void testCreate_UserNotFound() {
        CartRequest req = new CartRequest();
        req.setUserId(99L);
        when(userR.findById(99L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> cartService.create(req));
    }

    // --- 4. UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        CartRequest req = new CartRequest();
        req.setUserId(1L); 
        Cart existingCart = new Cart();
        User user = new User();

        when(cartR.findById(1L)).thenReturn(Optional.of(existingCart));
        when(userR.findById(1L)).thenReturn(Optional.of(user));

        cartService.update(req);
        
      
        
        assertNotNull(existingCart.getUser());
    }

    // --- 5. DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        Cart cart = new Cart();
        when(cartR.findById(1L)).thenReturn(Optional.of(cart));

        cartService.delete(1L);
        verify(cartR, times(1)).delete(cart);
    }
}