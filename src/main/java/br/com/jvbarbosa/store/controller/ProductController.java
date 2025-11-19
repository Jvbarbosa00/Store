package br.com.jvbarbosa.store.controller;

import br.com.jvbarbosa.store.model.Product;
import br.com.jvbarbosa.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product newProduct = productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(newProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        List<Product> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getByIdProducts(@PathVariable Long id){
        Optional<Product> productData = productService.findById(id);

        return productData.map(product -> new ResponseEntity<>(product, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable long id, @RequestBody Product productDetails) {

        return productService.findById(id).map(existingProduct -> {
            existingProduct.setName(productDetails.getName());
            existingProduct.setDescription(productDetails.getDescription());
            existingProduct.setPrice(productDetails.getPrice());
            existingProduct.setStock(productDetails.getStock());

            Product updatedProduct = productService.save(existingProduct);
            return ResponseEntity.ok(updatedProduct);
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