package reminderhackery.model

import kotlinx.serialization.Serializable

@Serializable
data class Pong(
    val message: String,
    val number: Int
)
