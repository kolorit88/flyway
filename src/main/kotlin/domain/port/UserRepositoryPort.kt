package domain.port

import domain.model.User

interface UserRepositoryPort : BaseRepositoryPort<User> {
    fun findByEmail(email: String): User?
}