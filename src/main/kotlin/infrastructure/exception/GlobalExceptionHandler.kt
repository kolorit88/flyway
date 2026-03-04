package infrastructure.exception

import org.example.example.infrastructure.dto.response.ErrorResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import shared.exception.BusinessException

@RestControllerAdvice
class GlobalExceptionHandler {
    @ExceptionHandler(BusinessException.UserNotFound::class)
    fun handleUserNotFound(ex: BusinessException.UserNotFound): ResponseEntity<ErrorResponse> {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(
                status = HttpStatus.NOT_FOUND.value(),
                error = "User Not Found",
                message = ex.message ?: "User not found"
            )
        )
    }

    @ExceptionHandler(BusinessException.DishNotFound::class)
    fun handleDishNotFound(ex: BusinessException.DishNotFound): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
            ErrorResponse(
                status = HttpStatus.NOT_FOUND.value(),
                error = "Dish Not Found",
                message = ex.message ?: "Dish not found"
            )
        )
    }

    @ExceptionHandler(BusinessException.EmailAlreadyExists::class)
    fun handleEmailExists(ex: BusinessException.EmailAlreadyExists): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorResponse(
                status = HttpStatus.CONFLICT.value(),
                error = "Email Already Exists",
                message = ex.message ?: "Email already exists"
            )
        )
    }

    @ExceptionHandler(BusinessException.DishNameAlreadyExists::class)
    fun handleDishNameExists(ex: BusinessException.DishNameAlreadyExists): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(
            ErrorResponse(
                status = HttpStatus.CONFLICT.value(),
                error = "Dish Name Already Exists",
                message = ex.message ?: "Dish name already exists"
            )
        )
    }

    @ExceptionHandler(BusinessException.InvalidUserData::class, BusinessException.DishValidationError::class)
    fun handleInvalidData(ex: BusinessException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Invalid Data",
                message = ex.message ?: "Invalid data provided"
            )
        )
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            ErrorResponse(
                status = HttpStatus.BAD_REQUEST.value(),
                error = "Validation Error",
                message = ex.message ?: "Invalid input data"
            )
        )
    }

    @ExceptionHandler(Exception::class)
    fun handleGenericException(ex: Exception): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
            ErrorResponse(
                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                error = "Internal Server Error",
                message = "An unexpected error occurred"
            )
        )
    }
}