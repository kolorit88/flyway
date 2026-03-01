package domain.service

import domain.model.Dish

interface DishService {
    fun getAllDishes() : List<Dish>
}