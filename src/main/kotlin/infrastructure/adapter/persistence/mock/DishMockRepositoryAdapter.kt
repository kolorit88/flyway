package infrastructure.adapter.persistence.mock
import domain.model.Dish
import domain.port.DishRepositoryPort
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("mock")
class DishMockRepositoryAdapter : BaseMockRepositoryAdapter<Dish>(), DishRepositoryPort {
    override fun findByName(name: String): Dish? {
        return storage.values.find { it.name.equals(name, ignoreCase = true) }
    }

    override fun findAllByRestaurantId(restaurantId: Long): List<Dish> {
        TODO("Not yet implemented")
    }
}