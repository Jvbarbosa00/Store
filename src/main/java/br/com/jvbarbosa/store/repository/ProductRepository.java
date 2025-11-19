package br.com.jvbarbosa.store.repository;

import br.com.jvbarbosa.store.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
