package com.suvankar.blogapis.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface FileService {
	String uploadImage  (String Path,MultipartFile file)throws IOException;
	InputStream getResource(String path,String fileName) throws FileNotFoundException;
}
