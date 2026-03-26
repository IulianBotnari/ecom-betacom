package com.betacom.services.interfaces;

import org.springframework.web.multipart.MultipartFile;

public interface InterfaceUploadService {
	String saveImage(MultipartFile file, Long id) throws Exception;
	
	void removeImage(String fileName) throws Exception;
	
	String buildUrl(String filename);
	
}
