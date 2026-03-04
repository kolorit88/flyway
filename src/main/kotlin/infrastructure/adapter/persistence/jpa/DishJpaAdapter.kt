package infrastructure.adapter.persistence.jpa

import domain.model.Dish
import domain.port.DishRepositoryPort
import infrastructure.adapter.persistence.jpa.repository.DishJpaRepository
import org.example.example.infrastructure.adapter.persistence.jpa.entity.DishEntity
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("db")
class DishJpaAdapter(
    private val dishJpaRepository: DishJpaRepository
) : DishRepositoryPort {

    override fun findAll(): List<Dish> {
        return dishJpaRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: Long): Dish? {
        return dishJpaRepository.findById(id).map { it.toDomain() }.orElse(null)
    }

    override fun create(entity: Dish): Dish {
        val dishEntity = DishEntity.fromDomain(entity)
        val savedEntity = dishJpaRepository.save(dishEntity)
        return savedEntity.toDomain()
    }

    override fun update(entity: Dish): Dish {
        val existingEntity = dishJpaRepository.findById(entity.id!!)
            .orElseThrow { IllegalArgumentException("Dish with id ${entity.id} not found") }

        val updatedEntity = DishEntity.fromDomain(entity)
        val savedEntity = dishJpaRepository.save(updatedEntity)
        return savedEntity.toDomain()
    }

    override fun deleteById(id: Long): Boolean {
        return if (dishJpaRepository.existsById(id)) {
            dishJpaRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    override fun findByName(name: String): Dish? {
        return dishJpaRepository.findByName(name)?.toDomain()
    }
}