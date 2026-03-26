package com.betacom.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.betacom.dto.request.wish_list.WishlistCreateRequest;
import com.betacom.dto.request.wish_list.WishlistUpdateRequest;
import com.betacom.dto.response.wish_list.WishListDTO;
import com.betacom.services.interfaces.InterfaceWhishListService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rest/wishlist")
public class WishListController {

    private final InterfaceWhishListService wishS;

    @PostMapping("create")
    public ResponseEntity<Object> create(@Valid @RequestBody WishlistCreateRequest request) {
        try {
            wishS.create(request);
            return ResponseEntity.status(HttpStatus.CREATED).body("Creazione wishlist avvenuta con successo");
        } catch (Exception e) {
            log.error("Errore durante creazione wishlist", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("update")
    public ResponseEntity<Object> update(@Valid @RequestBody WishlistUpdateRequest request) {
        try {
            wishS.update(request);
            return ResponseEntity.status(HttpStatus.OK).body("Aggiornamento wishlist completato");
        } catch (Exception e) {
            log.error("Errore durante update wishlist", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        try {
            wishS.delete(id);
            return ResponseEntity.status(HttpStatus.OK).body("Eliminazione wishlist completata");
        } catch (Exception e) {
            log.error("Errore durante delete wishlist", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("listAll")
    public ResponseEntity<Object> listAll() {
        try {
            List<WishListDTO> list = wishS.list();
            return ResponseEntity.status(HttpStatus.OK).body(list);
        } catch (Exception e) {
            log.error("Errore durante listAll wishlist", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("findById/{id}")
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            WishListDTO item = wishS.getById(id);
            return ResponseEntity.status(HttpStatus.OK).body(item);
        } catch (Exception e) {
            log.error("Errore durante findById wishlist", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}