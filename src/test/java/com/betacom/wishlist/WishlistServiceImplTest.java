package com.betacom.wishlist;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.betacom.dto.request.wish_list.WishlistCreateRequest;
import com.betacom.dto.request.wish_list.WishlistUpdateRequest;
import com.betacom.model.Product;
import com.betacom.model.User;
import com.betacom.model.WishList;
import com.betacom.repository.ProductRepository;
import com.betacom.repository.UserRepository;
import com.betacom.repository.WishListRepository;
import com.betacom.services.implementations.WishListServiceImpl;

class WishListServiceImplTest {

    @Mock
    private WishListRepository wishListRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private WishListServiceImpl wishListService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // --- CREATE ---
    @Test
    void testCreate_Success() throws Exception {
        WishlistCreateRequest req = new WishlistCreateRequest(1L, 2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(productRepository.findById(2L)).thenReturn(Optional.of(new Product()));

        wishListService.create(req);

        verify(wishListRepository).save(any(WishList.class));
    }

    @Test
    void testCreate_UserNotFound() {
        WishlistCreateRequest req = new WishlistCreateRequest(1L, 2L);

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> wishListService.create(req));
    }

    @Test
    void testCreate_ProductNotFound() {
        WishlistCreateRequest req = new WishlistCreateRequest(1L, 2L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(productRepository.findById(2L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> wishListService.create(req));
    }

    // --- UPDATE ---
    @Test
    void testUpdate_Success() throws Exception {
        WishlistUpdateRequest req = new WishlistUpdateRequest(1L, 1L);

        WishList item = new WishList();
        when(wishListRepository.findById(1L)).thenReturn(Optional.of(item));

        wishListService.update(req);

        verify(wishListRepository).save(item);
    }

    @Test
    void testUpdate_NotFound() {
        WishlistUpdateRequest req = new WishlistUpdateRequest(1L, 1L);

        when(wishListRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> wishListService.update(req));
    }

    // --- DELETE ---
    @Test
    void testDelete_Success() throws Exception {
        WishList item = new WishList();
        when(wishListRepository.findById(1L)).thenReturn(Optional.of(item));

        wishListService.delete(1L);

        verify(wishListRepository).delete(item);
    }

    @Test
    void testDelete_NotFound() {
        when(wishListRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> wishListService.delete(1L));
    }
}