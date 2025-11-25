package br.com.jvbarbosa.store.dto.mapper;

import br.com.jvbarbosa.store.dto.OrderCreateDTO;
import br.com.jvbarbosa.store.dto.OrderItemDTO;
import br.com.jvbarbosa.store.dto.OrderItemResponseDTO;
import br.com.jvbarbosa.store.dto.OrderResponseDTO;
import br.com.jvbarbosa.store.model.Order;
import br.com.jvbarbosa.store.model.OrderItem;
import br.com.jvbarbosa.store.model.Product;
import br.com.jvbarbosa.store.model.User;
import br.com.jvbarbosa.store.model.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    @Autowired
    private UserMapper userMapper;

    public Order toEntity(OrderCreateDTO dto){
        Order order = new Order();

        User client = new User();
        client.setId(dto.userId());
        order.setClient(client);

        order.getItems().addAll(dto.itemDTOList().stream().map(orderItemDTO -> {
            OrderItem item = new OrderItem();

            item.setQuantity(orderItemDTO.quantity());

            Product product = new Product();
            product.setId(orderItemDTO.productId());
            item.setProduct(product);

            item.setOrder(order);

            return item;

        }).toList());

        return order;

    }

    public OrderResponseDTO toDTO(Order order){
        List<OrderItemResponseDTO> itemsDTO = order.getItems().stream()
                .map(orderItem -> new OrderItemResponseDTO(
                        orderItem.getProduct().getName(),
                        orderItem.getQuantity(),
                        orderItem.getPrice(),
                        orderItem.getSubTotal()
                )).toList();

        OrderStatus statusEnum = OrderStatus.valueOf(order.getOrderStatus());

        return new OrderResponseDTO(
                order.getId(),
                userMapper.toDTO(order.getClient()),
                itemsDTO,
                order.getMoment(),
                statusEnum,
                order.getTotal()
        );
    }


}
