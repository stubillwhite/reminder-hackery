package reminderhackery.integration

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
import reminderhackery.model.Task
import reminderhackery.testcommon.IntegrationTestBase
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GetTasksIntegrationTest : IntegrationTestBase() {

    @Test
    fun getTasksThenReturnsTasksResponse() {
        testApplication {
            // Given
            val client = httpClient()

            val task = Task(null, "task-description")

            val createTaskResponse = client.post("/tasks") {
                contentType(ContentType.Application.Json)
                setBody(task)
            }
            assertEquals(HttpStatusCode.Created, createTaskResponse.status)

            // When
            val getTasksResponse = client.get("/tasks") {
                contentType(ContentType.Application.Json)
                setBody(task)
            }

            // Then
            val actual: List<Task> = getTasksResponse.body()
            assertEquals(1, actual.size)

            val actualTask = actual[0]
            assertEquals(task.description, actualTask.description)
            assertNotNull(actualTask.id)
        }
    }
}