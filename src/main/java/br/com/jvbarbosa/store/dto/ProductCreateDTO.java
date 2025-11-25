package br.com.jvbarbosa.store.dto;

import java.math.BigDecimal;
import java.util.List;

public record ProductCreateDTO(String name, String description, BigDecimal price, Integer stock, List<Long> categoryIds) {
}
