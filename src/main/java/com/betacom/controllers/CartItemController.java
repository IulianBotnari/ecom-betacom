package com.betacom.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.betacom.dto.request.cart_item.CartItemRequest;
import com.betacom.dto.request.user.UserCreateRequest;
import com.betacom.dto.request.user.UserUpdateRequest;
import com.betacom.services.interfaces.InterfaceCartItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping ("/rest/cartItem/")
public class CartItemController {
	
	private final InterfaceCartItemService cartItmS;
	
	@PostMapping(path = "create")
	public ResponseEntity<Object> create(@Valid @RequestBody CartItemRequest request) {
		Object response = null;
		HttpStatus status = HttpStatus.CREATED;
		
		try {
			cartItmS.create(request);
			response = "Creazione avvenuta con successo";
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			response = "Errore durnate il salvataggio";
		}
		return ResponseEntity.status(status).body(response);
	}
	
	
	
	@PutMapping(path = "update")
	public ResponseEntity<Object> update(@Valid @RequestBody(required = true) CartItemRequest request){
		Object response = null;
		HttpStatus status = HttpStatus.CREATED;
		
		try {
			cartItmS.update(request);
			response = "Salvataggio completato";
		} catch (Exception e) {
			response = "Salvataggio non riuscito";
			status = HttpStatus.BAD_REQUEST;
			e.printStackTrace();
		}
		
		return ResponseEntity.status(status).body(response);
	}
	
	
	@DeleteMapping(path = "delete/{id}")
	public ResponseEntity<Object> create(@RequestParam(required = true) Long id){
		Object response = null;
		
		HttpStatus status = HttpStatus.CREATED;
		
		try {
			cartItmS.delete(id);
			response = "Eliminazione completata";
		} catch (Exception e) {
			response = "Eliminazione non riuscita";
			status = HttpStatus.BAD_REQUEST;
		}
		
		
		return ResponseEntity.status(status).body(response);
	}
	
	@GetMapping(path = "listAll")	
	public ResponseEntity<Object> listAll(){
		
		HttpStatus status = HttpStatus.OK;
		Object response = null;
		try {
			response = cartItmS.list();
		} catch (Exception e) {
			response = "Errore durante il recupero della lista";
			status = HttpStatus.BAD_REQUEST;
		}
		
		return ResponseEntity.status(status).body(response);
	}
	
	@GetMapping("/findById")
	public ResponseEntity<Object> findById(@RequestParam (required = true) Long id) {
		Object r = new Object();
		HttpStatus status = HttpStatus.OK;
		try {
			r = cartItmS.getById(id);
		} catch (Exception e) {
			r = e.getMessage();
			status = HttpStatus.BAD_REQUEST;
		}
		return ResponseEntity.status(status).body(r);
	}

}
