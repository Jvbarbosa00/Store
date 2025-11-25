package br.com.jvbarbosa.store.dto;

public record OrderItemResponseDTO(String name, Integer quantity, Double price, Double subtotal) {
}
