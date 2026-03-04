package shared.utils.mapper
import domain.model.Dish
import org.example.example.infrastructure.dto.requests.dish.DishCreateRequest
import org.example.example.infrastructure.dto.requests.dish.DishData
import org.example.example.infrastructure.dto.requests.dish.DishUpdateRequest
import org.example.example.infrastructure.dto.response.DishResponse
import org.springframework.stereotype.Component

@Component
class DishMapper {

    fun toResponse(dish: Dish): DishResponse {
        return DishResponse(
            id = dish.id,
            name = dish.name,
            description = dish.description,
            price = dish.price,
            isAvailable = dish.isAvailable
        )
    }

    fun toDomain(dishData: DishData): Dish {
        return Dish(
            id = null,
            name = dishData.name,
            description = dishData.description,
            price = dishData.price,
            isAvailable = dishData.isAvailable
        )
    }

    fun toDomain(createRequest: DishCreateRequest): Dish {
        return Dish(
            id = null,
            name = createRequest.name,
            description = createRequest.description,
            price = createRequest.price,
            isAvailable = createRequest.isAvailable
        )
    }

    fun toDomain(updateRequest: DishUpdateRequest): Dish {
        return Dish(
            id = null,
            name = updateRequest.name,
            description = updateRequest.description,
            price = updateRequest.price,
            isAvailable = updateRequest.isAvailable
        )
    }
}