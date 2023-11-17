package reminderhackery.integration

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import reminderhackery.model.Task
import reminderhackery.testcommon.IntegrationTestBase
import reminderhackery.testcommon.StubData.dateOf
import reminderhackery.testcommon.StubData.dateTimeOf
import reminderhackery.utils.TemporalUtils.dateTimeNow
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GetDueTasksIntegrationTest : IntegrationTestBase() {

    private val today = dateTimeNow()
    private val yesterday = today.minusDays(1)
    private val tomorrow = today.plusDays(1)

    @Test
    fun getDueTasksThenReturnsTasksDueNow() {
        testApplication {
            // Given
            val client = httpClient()

            val tasks = listOf(
                Task(null, "task-description", yesterday),
                Task(null, "task-description", today),
                Task(null, "task-description", tomorrow)
            )

            tasks.forEach { task ->
                val createTaskResponse = client.post("/tasks") {
                    contentType(ContentType.Application.Json)
                    setBody(task)
                }
                assertEquals(HttpStatusCode.Created, createTaskResponse.status)
            }

            // When
            val getTasksResponse = client.get("/tasks/due") {
                contentType(ContentType.Application.Json)
            }

            // Then
            val response: List<Task> = getTasksResponse.body()
            assertEquals(2, response.size)

            tasks.zip(response).forEach { (expected, actual) ->
                assertEquals(expected.description, actual.description)
                assertEquals(expected.deadline, actual.deadline)
                assertNotNull(actual.id)
            }
        }
    }
}