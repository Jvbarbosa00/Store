package br.com.jvbarbosa.store.dto;

import java.util.List;

public record OrderCreateDTO(Long userId, List<OrderItemDTO> itemDTOList) {
}
