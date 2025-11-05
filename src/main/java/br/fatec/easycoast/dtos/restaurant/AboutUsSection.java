package br.fatec.easycoast.dtos.restaurant;

import java.net.URI;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;

@Embeddable
public class AboutUsSection {
    @NotBlank(message = "Header can't be blank")
    @Column(unique = true)
    private String header;
    @NotBlank(message = "Content can't be blank")
    private String content;
    private URI image;

    public AboutUsSection() {
    }

    public AboutUsSection(@NotBlank(message = "Header can't be blank") String header,
            @NotBlank(message = "Content can't be blank") String content, URI image) {
        this.header = header;
        this.content = content;
        this.image = image;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public URI getImage() {
        return image;
    }

    public void setImage(URI image) {
        this.image = image;
    }
}
