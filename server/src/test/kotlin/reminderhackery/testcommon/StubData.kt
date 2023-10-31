package reminderhackery.testcommon

import reminderhackery.utils.TemporalUtils
import java.time.LocalDate
import java.time.ZonedDateTime

object StubData {

    fun dateTimeOf(s: String): ZonedDateTime {
        return TemporalUtils.parseDateTime(s)
    }

    fun dateOf(s: String): LocalDate {
        return TemporalUtils.parseDate(s)
    }
}