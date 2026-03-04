package infrastructure.adapter.controller

import org.example.example.infrastructure.dto.requests.user.UserData
import application.dto.response.UserResponse
import domain.model.User
import domain.service.UserService
import org.example.example.infrastructure.dto.requests.user.UserUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import shared.exception.BusinessException
import shared.utils.mapper.UserMapper


@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {
    init {
        println("UserController created!")
    }
    @GetMapping
    fun listUsers(): ResponseEntity<List<UserResponse>> {
        val usersDomain: List<User> = userService.getAllUsers()
        try {
            val response : List<UserResponse> = usersDomain.map { userMapper.toResponse(it) }
            return ResponseEntity.ok(response)

        } catch (e: IllegalArgumentException) {
            throw BusinessException.InvalidUserData("Invalid user data: ${e.message}")
        }
    }

    @PostMapping
    fun createUser(@RequestBody userData: UserData): ResponseEntity<UserResponse> {
        return try {
            val userFromData = userMapper.toDomain(userData)
            val (resultUser, wasCreated) = userService.createOrGetUser(userFromData)
            val response = userMapper.toResponse(resultUser)

            val status = if (wasCreated) HttpStatus.CREATED else HttpStatus.OK
            ResponseEntity.status(status).body(response)

        } catch (e: BusinessException.UserNotFound) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Int): ResponseEntity<UserResponse> {
        val user = try {
             userService.getUserById(id.toLong())
        }
        catch (e: BusinessException.UserNotFound) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }

        val response = userMapper.toResponse(user)
        return ResponseEntity.ok(response)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Int, @RequestBody updateRequest: UserUpdateRequest): ResponseEntity<UserResponse> {
        return try {
            val user = userMapper.toDomain(updateRequest).copy(id = id.toLong())
            val updatedUser = userService.updateUser(user)
            val response = userMapper.toResponse(updatedUser)
            ResponseEntity.ok(response)
        }

        catch (e: IllegalArgumentException){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null)
        }
        catch (e: BusinessException.UserNotFound) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
        catch (e: BusinessException.EmailAlreadyExists) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null) // в задании этого нет по-моему, но я оставлю
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUserById(@PathVariable id: Int): ResponseEntity<Boolean> {
        return try {
            val result = userService.deleteUserById(id.toLong())
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(result)
        }
        catch (e: BusinessException.UserNotFound){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
        }
    }

}