package br.fatec.easycoast.dtos.product;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import br.fatec.easycoast.dtos.subcategory.SubcategoryResponse; // Importe o DTO de subcategoria

import java.util.Collections;
import java.util.List;

public record ProductResponse(
                Integer id,
                String name,
                String description,
                Double price,
                Double discount,
                Boolean availability,
                String imageurl, // Seguindo o padrão do seu DTO existente

                // Ignora o campo "products" dentro da subcategoria para evitar loop
                @JsonIgnoreProperties("products") SubcategoryResponse subcategory,

                // Para o nosso teste, manteremos as listas complexas como vazias
                List<Object> addonCategories,
                List<Object> items) {
        // Construtor compacto para inicializar as listas vazias
        public ProductResponse(Integer id, String name, String description, Double price, Double discount,
                        Boolean availability, String imageurl, SubcategoryResponse subcategory) {
                this(id, name, description, price, discount, availability, imageurl, subcategory,
                                Collections.emptyList(), Collections.emptyList());
        }
}