package reminderhackery.model

import kotlinx.serialization.Serializable

@Serializable
data class Ping(
    val message: String,
    val number: Int
)
