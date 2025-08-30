package com.bakery.service;

import com.bakery.client.ProductClient;
import com.bakery.client.UserClient;
import com.bakery.dto.OrderItemDTO;
import com.bakery.dto.OrderResponseDTO;
import com.bakery.entity.Order;
import com.bakery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repo;
    private final UserClient userClient;
    private final ProductClient productClient;

    public OrderResponseDTO createOrder(Order order) {
        return mapOrderResponseDTO(repo.save(order));
    }

    public List<OrderResponseDTO> getAllOrder() {
        return repo.findAll().stream()
                .map(this::mapOrderResponseDTO)
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderById(Long id) {
        return repo.findById(id).map(this::mapOrderResponseDTO).orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    public void deleteOrder(Long id) {
        repo.deleteById(id);
    }

    private OrderResponseDTO mapOrderResponseDTO(Order order) {
        List<OrderItemDTO> itemDTOs = order.getOrderItems().stream()
                .map(item -> OrderItemDTO.builder()
                        .productId(item.getProductId())
                        .quantity(item.getQuantity())
                        .priceAtOrder(item.getPriceAtOrder())
                        .build()
                )
                .toList();

        return OrderResponseDTO.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .status(order.getStatus().name())
                .orderDate(order.getOrderDate())
                .items(itemDTOs)
                .build();
    }
}
