package br.fatec.easycoast.services;

import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import br.fatec.easycoast.services.enums.Folder;
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
    
    public void store(MultipartFile file, String newName, Folder folder) {
		//Verify if the file will have a custom name
		String name = newName.length() != 0 ? newName : file.getOriginalFilename();
		
		try {
			if (file.isEmpty()) {
				throw new IllegalArgumentException("Failed to store empty file!");
			}
			//The directory and name the file will be saved
			Path destinationFile = folder != null ? this.rootLocation.resolve(folder.getFolderName())
													.resolve(Paths.get(name))
													.normalize().toAbsolutePath() :
													this.rootLocation.resolve(Paths.get(name))
													.normalize().toAbsolutePath();
			
			try (InputStream inputStream = file.getInputStream()) {
				//Save the file in the directory
				Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Failed to store file!", e);
		}
	}

	//Save a file without a custom name and default folder
	public void store(MultipartFile file){
		store(file, "", null);
	}

	//Save a file with a custom name and default folder
	public void store(MultipartFile file, String newName){
		store(file, newName, null);
	}

    public Path load(String filename, Folder folder) {
		try{
			//Get the possible URI
			Path uri = folder != null ? rootLocation.resolve(folder.getFolderName()).resolve(filename) : 
										rootLocation.resolve(filename);
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

	public Path load(String filename){
		return load(filename, null);
	}

    public Resource loadAsResource(String filename, Folder folder) {
		try {
			//Get the path of the file
			Path file =  this.load(filename, folder);
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

	public Resource loadAsResource(String filename){
		return loadAsResource(filename, null);
	}

	public void deleteFile(String filename, Folder folder){
		Path file = this.load(filename, folder);
		if(file == null) throw new EntityNotFoundException("Couldn't found the file: " + filename);

		try {
			//Move the file to the "deleted" directory
			Files.move(file, rootLocation.resolve("deleted").resolve(file.getFileName()), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new EntityNotFoundException("Couldn't read the file:" + filename);
		}
	}

	public void deleteFile(String filename){
		deleteFile(filename, null);
	}

	public void renameFile(String filename, String newFilename, Folder folder){
		Path file = this.load(filename, folder);
		if(file == null) throw new EntityNotFoundException("Couldn't found the file: " + filename);

		try {
			//Move the file to the "deleted" directory
			Files.move(file, file.getParent().resolve(newFilename), StandardCopyOption.REPLACE_EXISTING);
		} catch (IOException e) {
			throw new EntityNotFoundException("Couldn't read the file:" + filename);
		}
	}

    public void init() {
		try {
			//Creating the needed directories
			Files.createDirectories(rootLocation);
			Files.createDirectories(rootLocation.resolve("deleted"));
			Files.createDirectories(rootLocation.resolve("restaurantImages"));
			Files.createDirectories(rootLocation.resolve("restaurantAboutUs"));
			Files.createDirectories(rootLocation.resolve("restaurantHighlights"));
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Could not initialize storage");
		}
	}
}
