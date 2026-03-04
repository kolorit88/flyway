package domain.service
import domain.model.Dish

interface DishService {
    fun getDishById(dishId: Long): Dish
    fun getAllDishes(namePart: String? = null): List<Dish>
    fun createOrGetDish(dish: Dish): Pair<Dish, Boolean>
    fun findByName(name: String): Dish?
    fun updateDish(dish: Dish): Dish
    fun deleteDishById(dishId: Long): Boolean
}