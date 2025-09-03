package br.fatec.easycoast.resources;

import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.fatec.easycoast.services.FileStorageService;

@Controller
@CrossOrigin
@RequestMapping("images")
public class FileStorageController {
    @Autowired
    private FileStorageService storageService;

    @PostMapping()
	public String handleFileUpload(@RequestParam() MultipartFile file,
			RedirectAttributes redirectAttributes) {

		storageService.store(file);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");

		return "redirect:/";
	}

    @GetMapping("/{filename}")
	@ResponseBody
	public ResponseEntity<byte[]> serveFile(@PathVariable String filename) {

		Resource file = storageService.loadAsResource(filename);

		if (file == null) return ResponseEntity.notFound().build();

        try {
            byte[] content = Files.readAllBytes(storageService.load(filename).normalize());
            String type = Files.probeContentType(storageService.load(filename).normalize());
            if (type == null) type = "application/octet-stream";
            
            return ResponseEntity.ok()
                                .contentType(MediaType.parseMediaType(type))
                                .body(content);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
	}
}
