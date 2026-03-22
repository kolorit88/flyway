package infrastructure.adapter.persistence.jpa.repository

import infrastructure.adapter.persistence.jpa.entity.OrderStatus
import infrastructure.adapter.persistence.jpa.entity.OrderEntity
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface OrderJpaRepository : JpaRepository<OrderEntity, Long> {

    @EntityGraph(attributePaths = ["dishes", "user"])
    override fun findById(id: Long): java.util.Optional<OrderEntity>

    @EntityGraph(attributePaths = ["dishes", "user"])
    fun findAllByUserId(userId: Long): List<OrderEntity>

    @EntityGraph(attributePaths = ["dishes", "user"])
    fun findAllByStatus(status: OrderStatus): List<OrderEntity>

    @EntityGraph(attributePaths = ["dishes", "user"])
    fun findAllByUserIdAndStatus(userId: Long, status: OrderStatus): List<OrderEntity>
}