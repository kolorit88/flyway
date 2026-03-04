package shared.exception

sealed class BusinessException(message: String) : RuntimeException(message) {
    class UserNotFound(userId: Long) : BusinessException("User with id $userId not found")
    class UserValidationError() : BusinessException("Invalid user data")
    class EmailAlreadyExists(email: String) : BusinessException("Email $email already exists")
    class InvalidUserData(message: String) : BusinessException(message)

    class DishNotFound(dishId: Long) : BusinessException("Dish with id $dishId not found")
    class DishValidationError(message: String) : BusinessException(message)
    class DishNameAlreadyExists(name: String) : BusinessException("Dish with name '$name' already exists")
}