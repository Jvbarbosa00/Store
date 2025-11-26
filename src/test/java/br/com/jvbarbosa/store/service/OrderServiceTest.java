package br.com.jvbarbosa.store.service;

import br.com.jvbarbosa.store.model.Order;
import br.com.jvbarbosa.store.model.OrderItem;
import br.com.jvbarbosa.store.model.Product;
import br.com.jvbarbosa.store.model.User;
import br.com.jvbarbosa.store.model.enums.OrderStatus;
import br.com.jvbarbosa.store.repository.OrderItemRepository;
import br.com.jvbarbosa.store.repository.OrderRepository;
import br.com.jvbarbosa.store.repository.ProductRepository;
import br.com.jvbarbosa.store.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private ProductRepository productRepository;

    @Test
    void shouldCreateOrderWhenStockIsSufficient(){
        User u1 = new User();
        u1.setId(1L);
        u1.setName("Test");
        u1.setEmail("test@example.com.br");
        u1.setPassword("1234@Test");

        Product p1 = new Product();
        p1.setId(1L);
        p1.setName("ProductTest");
        p1.setDescription("ProductTestDescription");
        p1.setPrice(BigDecimal.valueOf(99.99));
        p1.setStock(200);

        Order o1 = new Order();
        o1.setMoment(Instant.parse("2025-06-20T19:53:07Z"));
        o1.setOrderStatus(OrderStatus.PAID);
        o1.setClient(u1);

        OrderItem oi1 = new OrderItem(o1, p1, 37, p1.getPrice().doubleValue());
        o1.getItems().add(oi1);

        when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
        when(productRepository.findById(1L)).thenReturn(Optional.of(p1));
        when(orderRepository.save(any())).thenReturn(o1);

        Order savedOrder = orderService.save(o1);

        Assertions.assertNotNull(savedOrder);

        Assertions.assertEquals(163, p1.getStock());
    }

    @Test
    void shouldThrowExceptionWhenStockIsInsufficient(){
        User u1 = new User();
        u1.setId(1L);

        Product p1 = new Product();
        p1.setId(1L);
        p1.setStock(10);

        Order o1 = new Order();
        o1.setClient(u1);

        OrderItem oi1 = new OrderItem(o1, p1, 20, 100.0);
        o1.getItems().add(oi1);

        when(userRepository.findById(1L)).thenReturn(Optional.of(u1));
        when(productRepository.findById(1L)).thenReturn(Optional.of(p1));
        when(orderRepository.save(any())).thenReturn(o1);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            orderService.save(o1);
        });
    }
}
