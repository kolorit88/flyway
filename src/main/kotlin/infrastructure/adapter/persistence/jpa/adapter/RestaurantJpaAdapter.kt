package org.example.example.infrastructure.adapter.persistence.jpa.adapter


import domain.model.Restaurant
import domain.port.RestaurantRepositoryPort
import infrastructure.adapter.persistence.jpa.repository.RestaurantJpaRepository
import infrastructure.adapter.persistence.jpa.entity.RestaurantEntity
import infrastructure.adapter.persistence.jpa.repository.DishJpaRepository
import jakarta.transaction.Transactional
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("db")
class RestaurantJpaAdapter(
    private val restaurantJpaRepository: RestaurantJpaRepository,
    private val dishJpaRepository: DishJpaRepository
) : RestaurantRepositoryPort {

    override fun findAll(): List<Restaurant> {
        return restaurantJpaRepository.findAll().map { it.toDomain() }
    }

    override fun findById(id: Long): Restaurant? {
        return restaurantJpaRepository.findById(id).map { it.toDomain() }.orElse(null)
    }

    override fun create(entity: Restaurant): Restaurant {
        val restaurantEntity = RestaurantEntity.fromDomain(entity)
        val savedEntity = restaurantJpaRepository.save(restaurantEntity)
        return savedEntity.toDomain()
    }

    override fun update(entity: Restaurant): Restaurant {
        val existingEntity = restaurantJpaRepository.findById(entity.id!!)
            .orElseThrow { IllegalArgumentException("Restaurant with id ${entity.id} not found") }

        val updatedEntity = RestaurantEntity.fromDomain(entity)
        val savedEntity = restaurantJpaRepository.save(updatedEntity)
        return savedEntity.toDomain()
    }

    @Transactional
    override fun deleteById(id: Long): Boolean {
        return if (restaurantJpaRepository.existsById(id)) {
            val dishes = dishJpaRepository.findAllByRestaurantId(id)
            dishJpaRepository.deleteAll(dishes)

            restaurantJpaRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    override fun findByName(name: String): Restaurant? {
        return restaurantJpaRepository.findByName(name)?.toDomain()
    }
}