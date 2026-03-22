package domain.port
import domain.model.Dish

interface DishRepositoryPort : BaseRepositoryPort<Dish> {
    fun findByName(name: String): Dish?
    fun findAllByRestaurantId(restaurantId: Long): List<Dish>
}