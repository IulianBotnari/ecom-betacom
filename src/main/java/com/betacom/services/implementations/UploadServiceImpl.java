package com.betacom.services.implementations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.betacom.model.Product;
import com.betacom.repository.ProductRepository;
import com.betacom.services.interfaces.InterfaceUploadService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class UploadServiceImpl implements InterfaceUploadService {
	
	private final Path uploadPath;
	private final ProductRepository productR;
	
	
	
	public UploadServiceImpl(@Value("${app.upload.dir:uploads}") String uploadDir, ProductRepository productR) {
        this.uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        this.productR = productR;
        init();
    }
	
	
	private void init() {
		try {
			if (Files.notExists(uploadPath)) {
				Files.createDirectories(uploadPath);
			}
		} catch (IOException e) {
			throw new RuntimeException("Errore durante la creazione della cartella upload");
		}
	}
	
	@Override
	public String saveImage(MultipartFile file, Long id) throws Exception {
		log.debug("saveImage {}", id);
		
		Assert.isTrue(!file.isEmpty(),() ->"Nessun file caricato");
		
		String originalName = file.getOriginalFilename();
		String extension = "";
		String originalNameMod = originalName.trim().replaceAll("\\s+", "_");
		
		log.debug("originalName: {}" , originalNameMod);
		  extension = Optional.ofNullable(originalName)
	                .filter(name -> name.contains("."))
	                .map(name -> name.substring(name.lastIndexOf(".")))
	                .orElse("");
		  
		  String uniqueName =  originalName.substring(0, originalName.lastIndexOf(".")) + "-" +  UUID.randomUUID().toString() + extension;
		  
		  Path destinationFile = uploadPath.resolve(uniqueName);
		
		  try {
	            Files.copy(file.getInputStream(), destinationFile, StandardCopyOption.REPLACE_EXISTING);
	            Product product = productR.findById( id)
	            	.orElseThrow(() -> new Exception("Prodotto non trovato"));	
	            product.setImage(uniqueName);
	            
	        } catch (IOException e) {
	            throw new RuntimeException("Errore durante il salvataggio del immagine");
	        }
	    
	        return uniqueName;
	}

	@Override
	public void removeImage(String fileName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String buildUrl(String filename) {
		return ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/images/")   
                .path(filename)               
                .toUriString(); 	
	
	}



}
