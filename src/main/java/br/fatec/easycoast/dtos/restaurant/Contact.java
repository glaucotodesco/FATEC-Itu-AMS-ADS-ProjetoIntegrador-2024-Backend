package br.fatec.easycoast.dtos.restaurant;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;

@Embeddable
public class Contact {
    @NotNull(message = "Type can't be blank")
    private String type;
    @NotNull(message = "Contact can't be blank")
    private String contact;

    public Contact() {}
    
    public Contact(String site, String contact) {
        this.type = site;
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String site) {
        this.type = site;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String url) {
        this.contact = url;
    }  
}
