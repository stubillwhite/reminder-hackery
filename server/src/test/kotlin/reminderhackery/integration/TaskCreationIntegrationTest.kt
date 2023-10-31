package reminderhackery.integration

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import reminderhackery.model.Task
import reminderhackery.testcommon.IntegrationTestBase
import reminderhackery.testcommon.StubData
import reminderhackery.testcommon.StubData.dateTimeOf
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class TaskCreationIntegrationTest : IntegrationTestBase() {

    @Test
    fun createTaskThenCreatesAndReturnsCreatedTask() {
        testApplication {

            val client = httpClient()

            val task = Task(null, "task-description", dateTimeOf("2022-01-02T03:04:05.000Z"))

            val response = client.post("/tasks") {
                contentType(ContentType.Application.Json)
                setBody(task)
            }
            assertEquals(HttpStatusCode.Created, response.status)

            val actual: Task = response.body()
            assertNotNull(actual.id)
            assertEquals(task.description, actual.description)
        }
    }
}