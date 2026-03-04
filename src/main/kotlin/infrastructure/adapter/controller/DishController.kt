package infrastructure.adapter.controller

import domain.model.Dish
import domain.service.DishService
import org.example.example.infrastructure.dto.requests.dish.DishCreateRequest
import org.example.example.infrastructure.dto.requests.dish.DishUpdateRequest
import org.example.example.infrastructure.dto.response.DishResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import shared.exception.BusinessException
import shared.utils.mapper.DishMapper

@RestController
@RequestMapping("/api/v1/dishes")
class DishController(
    private val dishService: DishService,
    private val dishMapper: DishMapper
) {

    @GetMapping
    fun listDishes(@RequestParam(required = false) namePart: String?): ResponseEntity<List<DishResponse>> {
        return try {
            val dishesDomain: List<Dish> = dishService.getAllDishes(namePart)
            val response: List<DishResponse> = dishesDomain.map { dishMapper.toResponse(it) }
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            throw BusinessException.DishValidationError("Invalid dish data: ${e.message}")
        }
    }

    @PostMapping
    fun createDish(@RequestBody createRequest: DishCreateRequest): ResponseEntity<DishResponse> {
        return try {
            val dishFromRequest = dishMapper.toDomain(createRequest)
            val (resultDish, wasCreated) = dishService.createOrGetDish(dishFromRequest)
            val response = dishMapper.toResponse(resultDish)

            val status = if (wasCreated) HttpStatus.CREATED else HttpStatus.OK
            ResponseEntity.status(status).body(response)

        } catch (e: BusinessException.DishValidationError) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
    }

    @GetMapping("/{id}")
    fun getDishById(@PathVariable id: Long): ResponseEntity<DishResponse> {
        val dish = try {
            dishService.getDishById(id)
        } catch (e: BusinessException.DishNotFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        val response = dishMapper.toResponse(dish)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun updateDish(@PathVariable id: Long, @RequestBody updateRequest: DishUpdateRequest): ResponseEntity<DishResponse> {
        return try {
            val dish = dishMapper.toDomain(updateRequest).copy(id = id)
            val updatedDish = dishService.updateDish(dish)
            val response = dishMapper.toResponse(updatedDish)
            ResponseEntity.ok(response)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        } catch (e: BusinessException.DishNotFound) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        } catch (e: BusinessException.DishNameAlreadyExists) {
            ResponseEntity.status(HttpStatus.CONFLICT).body(null)
        }
    }

    @DeleteMapping("/{id}")
    fun deleteDish(@PathVariable id: Long): ResponseEntity<Unit> {
        return try {
            dishService.deleteDishById(id)
            ResponseEntity.status(HttpStatus.NO_CONTENT).build()
        } catch (e: BusinessException.DishNotFound) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        }
    }
}