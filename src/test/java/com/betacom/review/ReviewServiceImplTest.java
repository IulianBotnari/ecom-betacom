package com.betacom.review;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import com.betacom.dto.request.review.ReviewCreateRequest;
import com.betacom.dto.request.review.ReviewUpdateRequest;
import com.betacom.model.*;
import com.betacom.repository.*;
import com.betacom.services.implementations.ReviewServiceImpl;

class ReviewServiceImplTest {

    @Mock
    private ReviewRepository reviewRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ReviewServiceImpl reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreate_Success() throws Exception {
        ReviewCreateRequest req = new ReviewCreateRequest(1L,2L,5,"test");

        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(productRepository.findById(2L)).thenReturn(Optional.of(new Product()));

        reviewService.create(req);

        verify(reviewRepository).save(any());
    }

    @Test
    void testCreate_UserNotFound() {
        ReviewCreateRequest req = new ReviewCreateRequest(1L,2L,5,"test");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> reviewService.create(req));
    }

    @Test
    void testUpdate_Success() throws Exception {
        ReviewUpdateRequest req = new ReviewUpdateRequest(1L,5,"update");

        Review review = new Review();
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        reviewService.update(req);

        verify(reviewRepository).save(review);
    }

    @Test
    void testUpdate_NotFound() {
        ReviewUpdateRequest req = new ReviewUpdateRequest(1L,5,"test");

        when(reviewRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> reviewService.update(req));
    }

    @Test
    void testDelete_Success() throws Exception {
        Review review = new Review();
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        reviewService.delete(1L);

        verify(reviewRepository).delete(review);
    }
}