package br.fatec.easycoast.services;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;

@Service
public class FileStorageService {
    private final Path rootLocation;

	public FileStorageService() {
		//Setting the base image directory
        this.rootLocation = Paths.get("images");
	}
    
    public void store(MultipartFile file, String newName) {
		//Verify if the file will have a custom name
		String name = newName.length() != 0 ? newName : file.getOriginalFilename();
		
		try {
			if (file.isEmpty()) {
				throw new IllegalArgumentException("Failed to store empty file!");
			}
			//The directory and name the file will be saved
			Path destinationFile = this.rootLocation.resolve(Paths.get(name))
													.normalize().toAbsolutePath();
			//Check if the directory is correct
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new IllegalArgumentException("Cannot store file outside current directory!");
			}
			try (InputStream inputStream = file.getInputStream()) {
				//Save the file in the directory
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Failed to store file!", e);
		}
	}

	//Save a file without a custom name
	public void store(MultipartFile file){
		store(file, "");
	}

    public Path load(String filename) {
		try{
			//Get the possible URI
			Path uri = rootLocation.resolve(filename);
			//Get it as a resource
			Resource resource = new UrlResource(uri.toUri());
			//If it exists, return the URI
			if (resource.exists() || resource.isReadable()) {return uri;}
			//Else
			else {return null;}
		} catch (InvalidPathException e){
			return null;
		} catch (MalformedURLException e){
			return null;
		}
	}

    public Resource loadAsResource(String filename) {
		try {
			//Get the path of the file
			Path file =  this.load(filename);
			if(file == null) throw new EntityNotFoundException("Could not find file: " + filename);

			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new EntityNotFoundException("Could not read file: " + filename);
			}
		}
		catch (MalformedURLException e) {
			throw new EntityNotFoundException("Could not read file: " + filename, e);
		}
	}

	public void deleteFile(String filename){
		Path file = this.load(filename);
		if(file == null) throw new EntityNotFoundException("Couldn't found the file: " + filename);

		try {
			//Move the file to the "deleted" directory
			Files.move(file, rootLocation.resolve("deleted").resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new EntityNotFoundException("Couldn't read the file:" + filename);
		}
	}

    public void init() {
		try {
			//Creating the needed directories
			Files.createDirectories(rootLocation);
			Files.createDirectories(rootLocation.resolve("deleted"));
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Could not initialize storage");
		}
	}
}
