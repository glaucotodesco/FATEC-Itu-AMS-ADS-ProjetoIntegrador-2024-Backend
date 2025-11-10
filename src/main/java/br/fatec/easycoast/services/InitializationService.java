package br.fatec.easycoast.services;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class InitializationService {
    @Value("${spring.profiles.active}")
	private String buildStage;

    private static final String INIT_FILE_PATH = "owner_initialized.flag";
    
    public boolean isOwnerInitialized() {
        if(buildStage.equals("dev")) return true;
        File file = new File(INIT_FILE_PATH);
        return file.exists();
    }
    
    public void markOwnerAsInitialized() {
        if(buildStage.equals("dev")) return;
        try {
            File file = new File(INIT_FILE_PATH);
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException("Failed to create initialization flag file", e);
        }
    }
}