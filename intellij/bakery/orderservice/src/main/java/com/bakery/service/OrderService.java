package com.bakery.service;

import com.bakery.client.ProductClient;
import com.bakery.client.UserClient;
import com.bakery.dto.ProductDto;
import com.bakery.dto.UserDto;
import com.bakery.entity.Order;
import com.bakery.entity.OrderItem;
import com.bakery.enums.OrderStatus;
import com.bakery.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repo;
    private final UserClient userClient;
    private final ProductClient productClient;

    public Order createOrder(Order order) {
        UserDto user = userClient.getUserById(order.getId());
        if (user == null) {
            throw new RuntimeException("user not found");
        }

        for (OrderItem item : order.getOrderItems()) {
            ProductDto product = productClient.getSingleProduct(item.getProductId());
            if (product == null) {
                throw new RuntimeException("Product not found: " + item.getProductId());
            }
        }

        order.setStatus(OrderStatus.PENDING);
        return repo.save(order);
    }

    public List<Order> getAllOrder() {
        return repo.findAll();
    }

    public Order getOrderById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Order not found!"));
    }

    public Order updateOrder(Long id, Order updatedOrder) {
        Order existingOrder = getOrderById(id);
        existingOrder.setOrderItems(updatedOrder.getOrderItems());
        existingOrder.setStatus(updatedOrder.getStatus());
        return repo.save(existingOrder);
    }

    public void deleteOrder(Long id) {
        repo.deleteById(id);
    }
}
