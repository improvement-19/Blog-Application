package com.suvankar.blogapis.services.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.suvankar.blogapis.services.FileService;

@Service
public class FileServiceImple implements FileService{
	
	@Override
	public String uploadImage(String path,MultipartFile file) throws IOException {
		//filename
		String name=file.getOriginalFilename();
		
		//random name generate as per ID
		String randomID=UUID.randomUUID().toString();
		String fileName1=randomID.concat(name.substring(name.lastIndexOf(".")));
		
		//fullpath
		String filePath=path+File.separator+fileName1;
		
		
		//create new folder if not created
		File f=new File(path);
		if(!f.exists())
		{
			f.mkdir();
		}
		
		//file copy
		//Files.copy(file.getInputStream(), Paths.get(filePath));
		Files.copy(file.getInputStream(), Paths.get(filePath));
		return fileName1;
	}

	@Override
	public InputStream getResource(String path, String fileName) throws FileNotFoundException {
		String fullPath=path+File.separator+fileName;
		InputStream is=new FileInputStream(fullPath);
		return is;
	}
}
