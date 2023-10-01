package reminderhackery.model

import kotlinx.serialization.Serializable

@Serializable
data class Task(
    val id: String?,
    val description: String
)
