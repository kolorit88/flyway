package domain.port
import domain.model.Dish

interface DishRepositoryPort : BaseRepositoryPort<Dish> {
    fun findByName(name: String): Dish?
}