package br.com.jvbarbosa.store.service;

import br.com.jvbarbosa.store.controller.exception.ResourceNotFoundException;
import br.com.jvbarbosa.store.model.Order;
import br.com.jvbarbosa.store.model.User;
import br.com.jvbarbosa.store.repository.OrderRepository;
import br.com.jvbarbosa.store.repository.UserRepository;
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

    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    public Order save(Order order){
        if (order.getMoment() == null) {
            order.setMoment(Instant.now());
        }
        Long clientId = order.getClient().getId();

        User client = userRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found!"));

        order.setClient(client);

        return orderRepository.save(order);
    }
}
