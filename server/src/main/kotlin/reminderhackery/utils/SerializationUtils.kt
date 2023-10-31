package reminderhackery.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import reminderhackery.utils.TemporalUtils.formatDate
import reminderhackery.utils.TemporalUtils.formatDateTime
import reminderhackery.utils.TemporalUtils.parseDate
import reminderhackery.utils.TemporalUtils.parseDateTime
import java.time.LocalDate
import java.time.ZonedDateTime

object StringEncodedUTCDateTimeSerializer : KSerializer<ZonedDateTime> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ZonedDateTime) {
        encoder.encodeString(formatDateTime(value))
    }

    override fun deserialize(decoder: Decoder): ZonedDateTime {
        val string = decoder.decodeString()
        return parseDateTime(string)
    }
}

object StringEncodedUTCDateSerializer : KSerializer<LocalDate> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Date", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: LocalDate) {
        encoder.encodeString(formatDate(value))
    }

    override fun deserialize(decoder: Decoder): LocalDate {
        val string = decoder.decodeString()
        return parseDate(string)
    }
}