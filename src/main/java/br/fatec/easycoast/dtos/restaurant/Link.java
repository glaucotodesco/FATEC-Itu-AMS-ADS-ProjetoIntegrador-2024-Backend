package br.fatec.easycoast.dtos.restaurant;

import jakarta.persistence.Embeddable;

@Embeddable
public class Link {
    private String site;
    private String url;

    public Link() {}

    
    public Link(String site, String url) {
        this.site = site;
        this.url = url;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }  
}
