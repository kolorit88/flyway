package domain.service

import application.dto.requests.UserData
import domain.model.User

interface UserService {
    fun getUserById(userId: Long): User?
    fun getAllUsers(): List<User>
    fun createUser(user: User): User
}