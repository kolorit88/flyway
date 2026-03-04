package infrastructure.adapter.persistence.mock

import domain.model.User
import domain.port.UserRepositoryPort
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("mock")
class UserMockRepositoryAdapter : UserRepositoryPort, BaseMockRepositoryAdapter<User>() {
    override fun findByEmail(email: String): User? {
        return storage.values.find { it.email.equals(email, ignoreCase = true) }
    }
}