package br.fatec.easycoast.services.enums;

public enum Folder {
    RESTAURANT_IMAGES("restaurantImages"),
    RESTAURANT_ABOUT_US("restaurantAboutUs"),
    RESTAURANT_HIGHLIGHTS("restaurantHighlights");

    private final String folderName;

    Folder(String folderName) {
        this.folderName = folderName;
    }

    public String getFolderName() {
        return folderName;
    }
}
