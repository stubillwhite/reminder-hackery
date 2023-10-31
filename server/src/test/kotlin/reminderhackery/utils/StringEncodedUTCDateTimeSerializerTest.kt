package reminderhackery.utils

import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import reminderhackery.testcommon.StubData.dateOf
import reminderhackery.testcommon.StubData.dateTimeOf
import java.time.LocalDate
import java.time.ZonedDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class SerializationTest {

    @Serializable
    data class StubClass(
        val string: String,

        @Serializable(with = StringEncodedUTCDateTimeSerializer::class)
        val dateTime: ZonedDateTime,

        @Serializable(with = StringEncodedUTCDateSerializer::class)
        val date: LocalDate
    )

    @Test
    fun canRoundTripDataTypes() {
        val json = Json

        val originalObject = StubClass(
            "string-value",
            dateTimeOf("2022-10-01T00:00:00.000Z"),
            dateOf("2023-09-11")
        )

        val encoded = json.encodeToString(originalObject)
        val decoded = json.decodeFromString<StubClass>(encoded)

        assertEquals(originalObject, decoded)
    }
}