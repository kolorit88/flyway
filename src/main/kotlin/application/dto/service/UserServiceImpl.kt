package application.dto.service

import application.dto.requests.UserData
import domain.model.User
import domain.port.UserRepositoryPort
import domain.service.UserService

class UserServiceImpl (
    private val userRepositoryPort: UserRepositoryPort
) : UserService
{
    override fun getUserById(userId: Long): User? {
        return userRepositoryPort.findById(userId)
    }

    override fun getAllUsers(): List<User> {
        return userRepositoryPort.findAll()
    }

    override fun createUser(user: User): User {
        return userRepositoryPort.create(user)
    }

}
