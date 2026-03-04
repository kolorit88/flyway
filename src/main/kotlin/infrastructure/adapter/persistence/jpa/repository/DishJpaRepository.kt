package infrastructure.adapter.persistence.jpa.repository

import org.example.example.infrastructure.adapter.persistence.jpa.entity.DishEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface DishJpaRepository : JpaRepository<DishEntity, Long> {
    fun findByName(name: String): DishEntity?
}