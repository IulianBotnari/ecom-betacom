package com.betacom.cartItem;


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

import com.betacom.dto.request.cart_item.CartItemRequest;
import com.betacom.dto.response.cart_item.CartItemDTO;
import com.betacom.model.Cart;
import com.betacom.model.CartItem;
import com.betacom.model.Product;
import com.betacom.repository.CartItemRepository;
import com.betacom.repository.CartRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.services.implementations.CartItemServiceImpl;

class CartItemServiceImplTest {

    @Mock
    private CartItemRepository cartItemR;
    @Mock
    private ProductRepository prodR;
    @Mock
    private CartRepository cartR;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

  
    @Test
    void testGetById_Success() throws Exception {
        CartItem item = new CartItem();
        item.setId(1L);
        when(cartItemR.findById(1L)).thenReturn(Optional.of(item));

        CartItemDTO result = cartItemService.getById(1L);
        assertNotNull(result);
    }

    @Test
    void testGetById_NotFound() {
        when(cartItemR.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(Exception.class, () -> cartItemService.getById(1L));
    }

   
    @Test
    void testList_Success() throws Exception {
        List<CartItem> list = new ArrayList<>();
        list.add(new CartItem());
        when(cartItemR.findAll()).thenReturn(list);

        List<CartItemDTO> result = cartItemService.list();
        assertEquals(1, result.size());
    }

    @Test
    void testCreate_Success() throws Exception {
        CartItemRequest req = new CartItemRequest();
        req.setQuantity(5);
        req.setCartId(1L);
        req.setProductId(1L);
        req.setSizeId(1L);

        when(prodR.findById(1L)).thenReturn(Optional.of(new Product()));
        when(cartR.findById(1L)).thenReturn(Optional.of(new Cart()));

        cartItemService.create(req);
        verify(cartItemR, times(1)).save(any(CartItem.class));
    }

    @Test
    void testCreate_MissingProductId() {
        CartItemRequest req = new CartItemRequest();
        req.setQuantity(5);
        req.setCartId(1L);
        req.setProductId(null); 

        assertThrows(Exception.class, () -> cartItemService.create(req));
    }


    @Test
    void testUpdate_FullSuccess() throws Exception {
        CartItemRequest req = new CartItemRequest();
        req.setCartItemId(10L);
        req.setQuantity(20);
        req.setProductId(1L);

        CartItem existingItem = new CartItem();
        Product p = new Product();

        when(cartItemR.findById(10L)).thenReturn(Optional.of(existingItem));
        when(prodR.findById(1L)).thenReturn(Optional.of(p));

        cartItemService.update(req);
        
        verify(cartItemR, times(1)).save(existingItem);
        assertEquals(20, existingItem.getQuantity());
    }

    @Test
    void testUpdate_ItemNotFound() {
        CartItemRequest req = new CartItemRequest();
        req.setCartItemId(1L);
        when(cartItemR.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> cartItemService.update(req));
    }

  
    @Test
    void testDelete_Success() throws Exception {
        CartItem item = new CartItem();
        when(cartItemR.findById(1L)).thenReturn(Optional.of(item));

        cartItemService.delete(1L);
        verify(cartItemR, times(1)).delete(item);
    }
}