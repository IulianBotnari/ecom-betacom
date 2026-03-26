package com.betacom.services.implementations;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.betacom.dto.request.review.ReviewCreateRequest;
import com.betacom.dto.request.review.ReviewUpdateRequest;
import com.betacom.dto.response.review.ReviewDTO;
import com.betacom.dto_mappers.map_dto_response.DtoResponseMapper;
import com.betacom.model.Review;
import com.betacom.repository.ReviewRepository;
import com.betacom.repository.UserRepository;
import com.betacom.repository.ProductRepository;
import com.betacom.services.interfaces.InterfaceReviewService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class ReviewServiceImpl implements InterfaceReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    // ---- GET BY ID ----
    @Override
    public ReviewDTO getById(Long id) throws Exception {
        Review review = reviewRepository.findById(id)
                .orElseThrow(() -> new Exception("Recensione non trovata"));

        return DtoResponseMapper.reviewDTO(review);
    }
    
    // ---- LIST ALL ----
    @Override
    public List<ReviewDTO> list() throws Exception {
        return reviewRepository.findAll()
                .stream()
                .map(DtoResponseMapper::reviewDTO)
                .collect(Collectors.toList());
    }
    
    // ---- CREATE ----
    @Override
    public void create(ReviewCreateRequest request) throws Exception {
        Review review = new Review();

        review.setRating(request.getRating());
        review.setReview(request.getReview());

        review.setUser(userRepository.findById(request.getUserId())
                .orElseThrow(() -> new Exception("Utente non trovato")));

        review.setProduct(productRepository.findById(request.getProductId())
                .orElseThrow(() -> new Exception("Prodotto non trovato")));

        reviewRepository.save(review);
    }

    // ---- UPDATE ----
    @Override
    public void update(ReviewUpdateRequest request) throws Exception {
        Review review = reviewRepository.findById(request.getId())
                .orElseThrow(() -> new Exception("Review non trovata"));

        if (request.getRating() != null) {
            review.setRating(request.getRating());
        }

        if (request.getReview() != null) {
            review.setReview(request.getReview());
        }

        reviewRepository.save(review);
    }

    // ---- DELETE ----
    @Override
    public void delete(Long id) throws Exception {
        if (!reviewRepository.existsById(id)) {
            throw new Exception("Recensione non trovata");
        }

        reviewRepository.deleteById(id);
    }

}