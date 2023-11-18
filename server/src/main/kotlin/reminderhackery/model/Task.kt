package reminderhackery.model

import kotlinx.serialization.Serializable
import reminderhackery.utils.StringEncodedUTCDateTimeSerializer
import java.time.ZonedDateTime

@Serializable
data class Task(
    val id: String?,

    val description: String,

    @Serializable(with = StringEncodedUTCDateTimeSerializer::class)
    val deadline: ZonedDateTime,

    val complete: Boolean
)
