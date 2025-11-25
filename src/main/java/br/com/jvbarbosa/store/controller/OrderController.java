package br.com.jvbarbosa.store.controller;

import br.com.jvbarbosa.store.dto.OrderCreateDTO;
import br.com.jvbarbosa.store.dto.OrderResponseDTO;
import br.com.jvbarbosa.store.dto.mapper.OrderMapper;
import br.com.jvbarbosa.store.model.Order;
import br.com.jvbarbosa.store.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@RequestBody OrderCreateDTO orderCreateDTO){
        Order orderToSave = orderMapper.toEntity(orderCreateDTO);

        Order savedOrder = orderService.save(orderToSave);

        OrderResponseDTO responseDTO = orderMapper.toDTO(savedOrder);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders(){
        List<Order> orders = orderService.findAll();

        List<OrderResponseDTO> orderDTOS = orders.stream().map(order -> orderMapper.toDTO(order)).toList();

        return ResponseEntity.ok(orderDTOS);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Long id){
        Optional<Order> orderData = orderService.findById(id);

        return orderData.map(order -> ResponseEntity.ok(orderMapper.toDTO(order)))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id){
        if (orderService.findById(id).isPresent()){
            orderService.delete(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
