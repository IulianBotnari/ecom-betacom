package com.betacom.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.betacom.dto.request.review.ReviewCreateRequest;
import com.betacom.dto.request.review.ReviewUpdateRequest;
import com.betacom.dto.response.review.ReviewDTO;
import com.betacom.services.interfaces.InterfaceReviewService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/review")
public class ReviewController {

    private final InterfaceReviewService reviewS;

    @PostMapping("create")
    public ResponseEntity<Object> create(@Valid @RequestBody ReviewCreateRequest request) {
        try {
            reviewS.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Creazione review avvenuta con successo");
        } catch (Exception e) {
            log.error("Errore durante la creazione review", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@Valid @RequestBody ReviewUpdateRequest request) {
        try {
            reviewS.update(request);
            return ResponseEntity.status(HttpStatus.OK).body("Aggiornamento review completato");
        } catch (Exception e) {
            log.error("Errore durante l'update review", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            reviewS.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminazione review completata");
        } catch (Exception e) {
            log.error("Errore durante delete review", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("listAll")
    public ResponseEntity<Object> listAll() {
        try {
            List<ReviewDTO> reviews = reviewS.list();
            return ResponseEntity.status(HttpStatus.OK).body(reviews);
        } catch (Exception e) {
            log.error("Errore durante listAll review", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("findById")
    public ResponseEntity<Object> findById(@RequestParam Long id) {
        try {
            ReviewDTO review = reviewS.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(review);
        } catch (Exception e) {
            log.error("Errore durante findById review", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}