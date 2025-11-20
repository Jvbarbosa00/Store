package br.com.jvbarbosa.store.repository;

import br.com.jvbarbosa.store.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
