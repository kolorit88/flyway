package infrastructure.adapter.controller

import application.dto.requests.UserData
import application.dto.response.UserResponse
import domain.model.User
import domain.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import shared.utils.mapper.UserMapper

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {
    @GetMapping
    fun listUsers(): ResponseEntity<List<UserResponse>> {
        val usersDomain: List<User> = userService.getAllUsers()
        val response : List<UserResponse> = usersDomain.map { userMapper.toResponse(it) }
        return ResponseEntity.ok(response)
    }

    @PostMapping
    fun createUser(@RequestBody userData: UserData): ResponseEntity<UserResponse> {
        val userFromData = userMapper.toDomain(userData)
        val newUserDomain = userService.createUser(userFromData)
        val response = userMapper.toResponse(newUserDomain)
        return ResponseEntity.ok(response)
    }


}