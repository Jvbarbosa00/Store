package br.com.jvbarbosa.store.service;

import br.com.jvbarbosa.store.controller.exception.ResourceNotFoundException;
import br.com.jvbarbosa.store.model.*;
import br.com.jvbarbosa.store.repository.OrderItemRepository;
import br.com.jvbarbosa.store.repository.OrderRepository;
import br.com.jvbarbosa.store.repository.ProductRepository;
import br.com.jvbarbosa.store.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ProductRepository productRepository;

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Transactional
    public Order save(Order order){
        if (order.getMoment() == null) {
            order.setMoment(Instant.now());
        }
        Long clientId = order.getClient().getId();

        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found!"));

        order.setClient(client);

       Order savedOrder =  orderRepository.save(order);

       for (OrderItem item : order.getItems()){
           item.setOrder(savedOrder);
           Product product = productRepository.findById(item.getProduct().getId())
                   .orElseThrow(() -> new ResourceNotFoundException("Product not found!"));
            if (product.getStock() >= item.getQuantity()){
                int newStock = product.getStock() - item.getQuantity();
                product.setStock(newStock);

                productRepository.save(product);
            } else {
                throw new IllegalArgumentException("Insufficient stock for product: " + product.getName());
            }
           item.setPrice(product.getPrice());
           item.setProduct(product);

           orderItemRepository.save(item);
       }

       return savedOrder;
    }

    public void delete(Long id) {
        orderRepository.deleteById(id);
    }
}
