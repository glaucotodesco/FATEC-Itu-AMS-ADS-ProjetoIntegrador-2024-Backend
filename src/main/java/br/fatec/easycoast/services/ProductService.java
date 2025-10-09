package br.fatec.easycoast.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.fatec.easycoast.dtos.product.ProductRequest;
import br.fatec.easycoast.dtos.product.ProductResponse;
import br.fatec.easycoast.entities.AddonCategory;
import br.fatec.easycoast.entities.Item;
import br.fatec.easycoast.entities.Product;
import br.fatec.easycoast.mappers.ProductMapper;
import br.fatec.easycoast.repositories.AddonCategoryRepository;
import br.fatec.easycoast.repositories.ItemRepository;
import br.fatec.easycoast.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AddonCategoryRepository addonCategoryRepository;

    @Autowired
    private ItemRepository itemRepository;

    public ProductResponse getProductById(int id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        return ProductMapper.toDTO(product);
    }

    public List<ProductResponse> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ProductResponse saveProduct(ProductRequest request) {
        Product product = ProductMapper.toEntity(request);

        if (request.addonCategories() != null) {
            List<AddonCategory> categories = request.addonCategories().stream()
                    .map(ac -> addonCategoryRepository.findById(ac.getId())
                            .orElseThrow(() -> new EntityNotFoundException("AddonCategory not found")))
                    .collect(Collectors.toList());
            product.setAddonsCategories(categories);
        }

        if (request.items() != null) {
            List<Item> items = request.items().stream()
                    .map(i -> itemRepository.findById(i.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Item not found")))
                    .collect(Collectors.toList());
            product.setItems(items);
        }

        return ProductMapper.toDTO(productRepository.save(product));
    }

    public void updateProduct(int id, ProductRequest request) {
        Product product = productRepository.getReferenceById(id);

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setDiscount(request.discount());
        product.setAvailability(request.availability());
        product.setSubcategory(request.subcategory());

        if (request.addonCategories() != null) {
            List<AddonCategory> categories = request.addonCategories().stream()
                    .map(ac -> addonCategoryRepository.findById(ac.getId())
                            .orElseThrow(() -> new EntityNotFoundException("AddonCategory not found")))
                    .collect(Collectors.toList());
            product.setAddonsCategories(categories);
        }

        if (request.items() != null) {
            List<Item> items = request.items().stream()
                    .map(i -> itemRepository.findById(i.getId())
                            .orElseThrow(() -> new EntityNotFoundException("Item not found")))
                    .collect(Collectors.toList());
            product.setItems(items);
        }

        productRepository.save(product);
    }

    public void deleteProduct(int id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found!");
        }
        productRepository.deleteById(id);
    }

    public List<Product> findByNameContainingIgnoreCase(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }
}
