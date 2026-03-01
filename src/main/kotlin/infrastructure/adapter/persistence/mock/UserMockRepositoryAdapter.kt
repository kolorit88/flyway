package infrastructure.adapter.persistence.mock

import domain.model.User
import domain.port.UserRepositoryPort

class UserMockRepositoryAdapter : UserRepositoryPort, BaseMockRepositoryAdapter<User>() {

}