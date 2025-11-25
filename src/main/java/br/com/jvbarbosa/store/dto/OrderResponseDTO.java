package br.com.jvbarbosa.store.dto;

import br.com.jvbarbosa.store.model.enums.OrderStatus;

import java.time.Instant;
import java.util.List;

public record OrderResponseDTO(Long id, UserResponseDTO client, List<OrderItemResponseDTO> items,
                               Instant moment, OrderStatus status, Double total) {
}
