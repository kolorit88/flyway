package infrastructure.adapter.persistence.mock

import domain.model.Dish
import domain.port.DishRepositoryPort

class DishMockRepositoryAdapter : BaseMockRepositoryAdapter<Dish>(), DishRepositoryPort {

}