package br.fatec.easycoast.resources;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.fatec.easycoast.services.FileStorageService;
import jakarta.persistence.EntityNotFoundException;

@Controller
@CrossOrigin
@RequestMapping("images")
public class FileStorageController {
    @Autowired
    private FileStorageService storageService;

    @PostMapping()
	public ResponseEntity<Void> handleFileUpload(@RequestParam() MultipartFile file) {
        storageService.store(file);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{filename}")
                .buildAndExpand(file.getOriginalFilename())
                .toUri();

		return ResponseEntity.created(location).build();
	}

    @GetMapping("/{filename}")
	@ResponseBody
	public ResponseEntity<byte[]> showImage(@PathVariable String filename) {
        Path file = storageService.load(filename).normalize();

        try {
            byte[] content = Files.readAllBytes(file);
            String type = Files.probeContentType(file);
            if (type == null) type = "application/octet-stream";
            
            return ResponseEntity.ok()
                                .contentType(MediaType.parseMediaType(type))
                                .body(content);
        } catch (IOException e) {
            throw new EntityNotFoundException("Couldn't read file: " + filename);
        }
	}

    @GetMapping("/{filename}/download")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null) throw new EntityNotFoundException("Couldn't read file: " + filename);

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"" + file.getFilename() + "\"").body(file);
	}
}
