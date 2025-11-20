package br.com.jvbarbosa.store.repository;

import br.com.jvbarbosa.store.model.OrderItem;
import br.com.jvbarbosa.store.model.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
