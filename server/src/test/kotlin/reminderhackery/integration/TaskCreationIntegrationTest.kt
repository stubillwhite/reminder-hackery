package reminderhackery.integration

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import reminderhackery.model.Task
import reminderhackery.testcommon.IntegrationTestBase
import reminderhackery.testcommon.assertTaskFieldsEqual
import reminderhackery.utils.TemporalUtils.dateTimeNow
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskCreationIntegrationTest : IntegrationTestBase() {

    @Test
    fun createTaskThenCreatesAndReturnsCreatedTask() {
        testApplication {

            val client = httpClient()

            val expected = Task(null, "task-description", dateTimeNow(), false, null)

            val response = client.post("/tasks") {
                contentType(ContentType.Application.Json)
                setBody(expected)
            }
            assertEquals(HttpStatusCode.Created, response.status)

            val actual: Task = response.body()
            assertTaskFieldsEqual(expected, actual)
        }
    }
}