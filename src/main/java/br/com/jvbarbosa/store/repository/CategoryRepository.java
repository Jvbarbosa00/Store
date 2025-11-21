package br.com.jvbarbosa.store.repository;

import br.com.jvbarbosa.store.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
