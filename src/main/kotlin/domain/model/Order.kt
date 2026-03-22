package org.example.example.domain.model

import domain.model.Dish
import domain.model.common.IHaveID
import java.time.LocalDateTime

data class Order(
    override var id: Long? = null,
    val userId: Long,
    var status: OrderStatus,
    val createdAt: LocalDateTime,
    val dishes: List<Dish>
) : IHaveID<Order> {
    init {
        require(dishes.isNotEmpty()) { "Order must contain at least one dish" }
    }

    fun updateStatus(newStatus: OrderStatus): Order {
        return this.copy(status = newStatus)
    }

    override fun withId(id: Long): Order {
        return this.copy(id = id)
    }
}