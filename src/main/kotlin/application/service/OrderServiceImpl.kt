package org.example.example.application.service

import domain.port.DishRepositoryPort
import domain.port.UserRepositoryPort
import org.example.example.domain.model.Order
import org.example.example.domain.model.OrderStatus as DomainOrderStatus
import org.example.example.domain.port.OrderRepositoryPort
import org.example.example.domain.service.OrderService
import org.springframework.stereotype.Service
import shared.exception.BusinessException
import java.time.LocalDateTime

@Service
class OrderServiceImpl(
    private val orderRepositoryPort: OrderRepositoryPort,
    private val userRepositoryPort: UserRepositoryPort,
    private val dishRepositoryPort: DishRepositoryPort
) : OrderService {

    override fun getOrderById(orderId: Long): Order {
        return orderRepositoryPort.findById(orderId)
            ?: throw BusinessException.OrderNotFound(orderId)
    }

    override fun getAllOrders(userId: Long?, status: DomainOrderStatus?): List<Order> {
        return when {
            userId != null && status != null -> {
                orderRepositoryPort.findAllByUserIdAndStatus(userId, status)
            }
            userId != null -> {
                orderRepositoryPort.findAllByUserId(userId)
            }
            status != null -> {
                orderRepositoryPort.findAllByStatus(status)
            }
            else -> {
                orderRepositoryPort.findAll()
            }
        }
    }

    override fun createOrder(userId: Long, dishIds: List<Long>): Order {
        val user = userRepositoryPort.findById(userId)
            ?: throw BusinessException.UserNotFound(userId)

        if (dishIds.isEmpty()) {
            throw BusinessException.OrderValidationError("Order must contain at least one dish")
        }

        val dishes = dishIds.map { dishId ->
            dishRepositoryPort.findById(dishId)
                ?: throw BusinessException.DishNotFound(dishId)
        }

        val order = Order(
            id = null,
            userId = userId,
            status = DomainOrderStatus.PENDING,
            createdAt = LocalDateTime.now(),
            dishes = dishes
        )

        return orderRepositoryPort.create(order)
    }

    override fun updateOrderStatus(orderId: Long, newStatus: DomainOrderStatus): Order {
        val order = orderRepositoryPort.findById(orderId)
            ?: throw BusinessException.OrderNotFound(orderId)

        val updatedOrder = try {
            order.updateStatus(newStatus)
        } catch (e: IllegalArgumentException) {
            throw BusinessException.InvalidOrderStatusTransition(
                order.status.name,
                newStatus.name
            )
        } catch (e: IllegalStateException) {
            throw BusinessException.OrderValidationError(e.message ?: "Invalid status transition")
        }

        return orderRepositoryPort.update(updatedOrder)
    }
}