package reminderhackery.integration

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import reminderhackery.model.Task
import reminderhackery.testcommon.IntegrationTestBase
import reminderhackery.testcommon.StubData.dateTimeOf
import reminderhackery.testcommon.assertTaskFieldsEqual
import reminderhackery.utils.TemporalUtils
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GetTasksIntegrationTest : IntegrationTestBase() {

    private val today = TemporalUtils.dateTimeNow()
    private val yesterday = today.minusDays(1)
    private val tomorrow = today.plusDays(1)

    @Test
    fun getTasksThenReturnsTasksResponse() {
        testApplication {
            // Given
            val client = httpClient()

            val tasks = listOf(
                Task(null, "task-description", yesterday, false, null),
                Task(null, "task-description", today, false, 1),
                Task(null, "task-description", tomorrow, false, 2)
            )

            tasks.forEach { task ->
                val createTaskResponse = client.post("/tasks") {
                    contentType(ContentType.Application.Json)
                    setBody(task)
                }
                assertEquals(HttpStatusCode.Created, createTaskResponse.status)
            }

            // When
            val getTasksResponse = client.get("/tasks") {
                contentType(ContentType.Application.Json)
            }

            // Then
            val response: List<Task> = getTasksResponse.body()
            assertEquals(3, response.size)

            tasks.zip(response).forEach { (expected, actual) ->
                assertTaskFieldsEqual(expected, actual)
            }
        }
    }
}