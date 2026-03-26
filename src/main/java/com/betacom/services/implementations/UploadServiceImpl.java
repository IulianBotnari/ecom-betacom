package com.betacom.services.implementations;

import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.betacom.services.interfaces.InterfaceUploadService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements InterfaceUploadService {@Override
	public String saveImage(MultipartFile file, Integer id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeImage(String fileName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String buildUrl(String filename) {
		// TODO Auto-generated method stub
		return null;
	}
	//@Value("${app.upload.dir:iploads}")
	// private String uploadUrl = Paths.get(this);
	


}
