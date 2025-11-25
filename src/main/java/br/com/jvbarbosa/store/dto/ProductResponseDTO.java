package br.com.jvbarbosa.store.dto;

import java.math.BigDecimal;
import java.util.Set;

public record ProductResponseDTO(Long id, String name, String description, BigDecimal price, Integer stock,
                                 Set<CategoryDTO> categoryDTOS) {
}
