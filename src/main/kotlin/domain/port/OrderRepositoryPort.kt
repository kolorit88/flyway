package org.example.example.domain.port

import domain.port.BaseRepositoryPort
import org.example.example.domain.model.Order
import org.example.example.domain.model.OrderStatus

interface OrderRepositoryPort : BaseRepositoryPort<Order> {
    fun findAllByUserId(userId: Long): List<Order>
    fun findAllByStatus(status: OrderStatus): List<Order>
    fun findAllByUserIdAndStatus(userId: Long, status: OrderStatus): List<Order>
}

