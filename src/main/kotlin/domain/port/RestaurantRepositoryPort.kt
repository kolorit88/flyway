package domain.port
import domain.model.Restaurant

interface RestaurantRepositoryPort : BaseRepositoryPort<Restaurant> {
    fun findByName(name: String): Restaurant?
}