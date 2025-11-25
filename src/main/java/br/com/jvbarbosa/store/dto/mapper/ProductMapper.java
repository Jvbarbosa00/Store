package br.com.jvbarbosa.store.dto.mapper;

import br.com.jvbarbosa.store.dto.CategoryDTO;
import br.com.jvbarbosa.store.dto.ProductCreateDTO;
import br.com.jvbarbosa.store.dto.ProductResponseDTO;
import br.com.jvbarbosa.store.model.Category;
import br.com.jvbarbosa.store.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {

    public Product toEntity(ProductCreateDTO dto){
        Product product = new Product();
        product.setName(dto.name());
        product.setDescription(dto.description());
        product.setPrice(dto.price());
        product.setStock(dto.stock());

        if (dto.categoryIds() != null){
            product.getCategories().addAll(dto.categoryIds().stream().map(categoryId -> {
                Category category = new Category();
                category.setId(categoryId);
                return category;
            }).collect(Collectors.toSet())
            );

        }
        return product;
    }

    public ProductResponseDTO toDTO(Product product){
        return new ProductResponseDTO(
        product.getId(),
        product.getName(),
        product.getDescription(),
        product.getPrice(),
        product.getStock(),

        product.getCategories().stream().map(category -> new CategoryDTO(category.getId(),
                category.getName())).collect(Collectors.toSet())
    );
    }
}
