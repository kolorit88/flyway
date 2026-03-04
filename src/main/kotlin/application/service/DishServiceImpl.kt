package application.service

import domain.model.Dish
import domain.port.DishRepositoryPort
import domain.service.DishService
import org.springframework.stereotype.Service
import shared.exception.BusinessException

@Service
class DishServiceImpl(
    private val dishRepositoryPort: DishRepositoryPort
) : DishService {

    override fun getDishById(dishId: Long): Dish {
        return dishRepositoryPort.findById(dishId)
            ?: throw BusinessException.DishNotFound(dishId)
    }

    override fun getAllDishes(namePart: String?): List<Dish> {
        val allDishes = dishRepositoryPort.findAll()

        return if (!namePart.isNullOrBlank()) {
            allDishes.filter { it.name.contains(namePart, ignoreCase = true) }
        } else {
            allDishes
        }
    }

    override fun findByName(name: String): Dish? {
        return dishRepositoryPort.findByName(name)
    }

    override fun createOrGetDish(dish: Dish): Pair<Dish, Boolean> {
        val existingDish = findByName(dish.name)

        if (existingDish != null) {
            return Pair(existingDish, false)
        }

        return try {
            Pair(dishRepositoryPort.create(dish), true)
        } catch (e: IllegalArgumentException) {
            throw BusinessException.DishValidationError(e.message ?: "Invalid dish data")
        }
    }

    override fun updateDish(dish: Dish): Dish {
        val existingDish = dishRepositoryPort.findById(dish.id!!)
            ?: throw BusinessException.DishNotFound(dish.id!!)

        // Проверяем, не существует ли другое блюдо с таким же именем
        val dishWithSameName = findByName(dish.name)
        if (dishWithSameName != null && dishWithSameName.id != dish.id) {
            throw BusinessException.DishNameAlreadyExists(dish.name)
        }

        return dishRepositoryPort.update(dish)
    }

    override fun deleteDishById(dishId: Long): Boolean {
        val dishExists = dishRepositoryPort.findById(dishId)
            ?: throw BusinessException.DishNotFound(dishId)

        return dishRepositoryPort.deleteById(dishId)
    }
}