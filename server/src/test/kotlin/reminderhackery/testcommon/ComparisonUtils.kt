package reminderhackery.testcommon

import reminderhackery.model.Task
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

fun assertTaskFieldsEqual(expected: Task, actual: Task) {
    assertNotNull(actual.id)
    assertEquals(expected.description, actual.description)
    assertEquals(expected.deadline, actual.deadline)
    assertEquals(expected.complete, actual.complete)
    assertEquals(expected.recurrence, actual.recurrence)
}