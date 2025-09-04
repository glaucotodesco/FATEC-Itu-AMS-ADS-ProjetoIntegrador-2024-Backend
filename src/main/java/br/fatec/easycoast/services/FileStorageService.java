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
        this.rootLocation = Paths.get("images");
	}
    
    public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new IllegalArgumentException("Failed to store empty file!");
			}
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(file.getOriginalFilename()))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				throw new IllegalArgumentException(
						"Cannot store file outside current directory!");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Failed to store file!", e);
		}
	}

    public Path load(String filename) {
		try{
			return rootLocation.resolve(filename);
		} catch (InvalidPathException e){
			throw new EntityNotFoundException("File not found!");
		}
	}

    public Resource loadAsResource(String filename) {
		try {
			Path file =  this.load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new EntityNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new EntityNotFoundException("Could not read file: " + filename, e);
		}
	}

    public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new IllegalArgumentException("Could not initialize storage", e);
		}
	}
}
