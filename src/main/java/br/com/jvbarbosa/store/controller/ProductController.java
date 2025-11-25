package br.com.jvbarbosa.store.controller;

import br.com.jvbarbosa.store.dto.ProductCreateDTO;
import br.com.jvbarbosa.store.dto.ProductResponseDTO;
import br.com.jvbarbosa.store.dto.mapper.ProductMapper;
import br.com.jvbarbosa.store.model.Category;
import br.com.jvbarbosa.store.model.Product;
import br.com.jvbarbosa.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        Product productToSave = productMapper.toEntity(productCreateDTO);
        Product savedProduct = productService.save(productToSave);

        ProductResponseDTO responseDTO = productMapper.toDTO(savedProduct);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDTO>> getAllProducts(){
        List<Product> products = productService.findAll();

        List<ProductResponseDTO> responseDTOS = products.stream().map(productMapper::toDTO).toList();
        return ResponseEntity.ok(responseDTOS);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getByIdProducts(@PathVariable Long id){
        Optional<Product> productData = productService.findById(id);

        return productData.map(product -> ResponseEntity.ok(productMapper.toDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(@PathVariable long id,
                                                 @RequestBody ProductCreateDTO productDTO) {

        return productService.findById(id).map(existingProduct -> {
            existingProduct.setName(productDTO.name());
            existingProduct.setDescription(productDTO.description());
            existingProduct.setPrice(productDTO.price());
            existingProduct.setStock(productDTO.stock());
            if (productDTO.categoryIds() != null){
                existingProduct.getCategories().clear();

                existingProduct.getCategories().addAll(productDTO.categoryIds().stream().map(categoryId -> {
                    Category c = new Category();
                    c.setId(categoryId);
                    return c;
                }).collect(Collectors.toSet()));
            }

            Product updatedProduct = productService.save(existingProduct);

            return ResponseEntity.ok(productMapper.toDTO(updatedProduct));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id){
        if (productService.findById(id).isPresent()) {
            productService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}