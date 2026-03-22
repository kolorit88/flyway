package domain.port
import domain.model.Dish
import org.example.example.domain.model.Order

interface DishRepositoryPort : BaseRepositoryPort<Dish> {
    fun findByName(name: String): Dish?
    fun findAllByRestaurantId(restaurantId: Long): List<Dish>
    fun findOrdersContainingDish(dishId: Long): List<Order>
}