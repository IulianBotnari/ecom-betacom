package com.betacom.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.betacom.services.interfaces.InterfaceUploadService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/rest/upload/")
public class UploadController {
	
	private final InterfaceUploadService uploadS;
	
	@PostMapping(value = "/image", consumes = "multipart/form-data")
	public ResponseEntity<Object> uploadImage(@RequestParam MultipartFile file, @RequestParam Long id){
		Object response = null;
		HttpStatus status = HttpStatus.CREATED;
		try {
			
		 if (file.getContentType() == null || !file.getContentType().startsWith("image/")) {
			 response ="File non valido";
			 status= HttpStatus.BAD_REQUEST;
			 return ResponseEntity.status(status).body(response);	            
		 }
		 
		 uploadS.saveImage(file, id);
		 return ResponseEntity.status(status).body(response);
	
		} catch (Exception e) {
			status = HttpStatus.BAD_REQUEST;
			response = "Errore durnate il salvataggio";
		}
		return ResponseEntity.status(status).body(response);
	}
	
	
	@GetMapping("getUrl")
	public ResponseEntity<Object> getUrl(@RequestParam (required = true) String filename) {
		Object response = new Object();
		HttpStatus status = HttpStatus.OK;
		
		response = uploadS.buildUrl(filename);
		return ResponseEntity.status(status).body(response);
	}

}
