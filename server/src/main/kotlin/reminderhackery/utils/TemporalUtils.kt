package reminderhackery.utils

import java.time.LocalDate
import java.time.ZoneOffset.UTC
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

object TemporalUtils {
    private val dateTimeFormatter = DateTimeFormatter.ISO_INSTANT.withZone(UTC)
    private val dateFormatter = DateTimeFormatter.ISO_DATE.withZone(UTC)

    fun dateTimeNow(): ZonedDateTime {
        return ZonedDateTime.now().withZoneSameInstant(UTC)
    }

    fun dateNow(): LocalDate {
        return LocalDate.now()!!
    }

    fun formatDateTime(t: ZonedDateTime): String {
        return dateTimeFormatter.format(t)
    }

    fun parseDateTime(s: String): ZonedDateTime {
        return ZonedDateTime.parse(s, dateTimeFormatter)
    }

    fun formatDate(d: LocalDate): String {
        return dateFormatter.format(d)
    }

    fun parseDate(s: String): LocalDate {
        return LocalDate.parse(s, dateFormatter)
    }

    fun toDateTime(d: LocalDate): ZonedDateTime {
        return d.atStartOfDay(UTC)
    }

    fun toDate(d: ZonedDateTime): LocalDate {
        return d.toLocalDate()
    }

    fun toLegacyDateTime(d: ZonedDateTime): Date {
        return Date.from(d.toInstant())
    }

    fun toLegacyDate(d: LocalDate): Date {
        return Date.from(d.atStartOfDay(UTC).toInstant())
    }

    fun fromLegacyDateTime(d: Date): ZonedDateTime {
        return ZonedDateTime.ofInstant(d.toInstant(), UTC)
    }

    fun fromLegacyDate(d: Date): LocalDate {
        return d.toInstant().atZone(UTC).toLocalDate()
    }
}