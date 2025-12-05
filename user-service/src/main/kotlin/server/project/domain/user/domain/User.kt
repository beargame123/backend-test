package server.project.domain.user.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document


@Document(collection = "users")
class User(
    var name: String,
    var email: String,
) {
    @Id
    var userId: String? = null
        private set
}