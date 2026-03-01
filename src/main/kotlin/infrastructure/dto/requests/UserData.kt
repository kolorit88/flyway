package application.dto.requests

data class UserData(
    val email: String,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean = true
)