package reminderhackery.utils

import reminderhackery.utils.TemporalUtils.formatDate
import reminderhackery.utils.TemporalUtils.formatDateTime
import reminderhackery.utils.TemporalUtils.fromLegacyDate
import reminderhackery.utils.TemporalUtils.fromLegacyDateTime
import reminderhackery.utils.TemporalUtils.parseDate
import reminderhackery.utils.TemporalUtils.parseDateTime
import reminderhackery.utils.TemporalUtils.toDate
import reminderhackery.utils.TemporalUtils.toDateTime
import reminderhackery.utils.TemporalUtils.toLegacyDate
import reminderhackery.utils.TemporalUtils.toLegacyDateTime
import kotlin.test.Test
import kotlin.test.assertEquals

class TemporalUtilsTest {

    @Test
    fun canRoundTripDateTime() {
        val dateTime = parseDateTime("2011-12-03T10:15:30Z")
        val serializedDateTime = formatDateTime(dateTime)
        val deserializedDateTime = parseDateTime(serializedDateTime)
        assertEquals(dateTime, deserializedDateTime)
    }

    @Test
    fun canRoundTripDate() {
        val date = parseDate("2011-12-03")
        val serializedDate = formatDate(date)
        val deserializedDate = parseDate(serializedDate)
        assertEquals(date, deserializedDate)
    }

    @Test
    fun canRoundTripToLegacyDateTime() {
        val dateTime = parseDateTime("2011-12-03T10:15:30Z")
        val legacyDateTime = toLegacyDateTime(dateTime)
        val roundTrippedDateTime = fromLegacyDateTime(legacyDateTime)
        assertEquals(dateTime, roundTrippedDateTime)
    }

    @Test
    fun canRoundTripToLegacyDate() {
        val date = parseDate("2011-12-03")
        val legacyDate = toLegacyDate(date)
        val roundTrippedDate = fromLegacyDate(legacyDate)
        assertEquals(date, roundTrippedDate)
    }

    @Test
    fun canRoundTripDateAndDateTime() {
        val date = parseDate("2011-12-03")
        val dateTime = toDateTime(date)
        val roundTrippedDate = toDate(dateTime)
        assertEquals(date, roundTrippedDate)
    }
}