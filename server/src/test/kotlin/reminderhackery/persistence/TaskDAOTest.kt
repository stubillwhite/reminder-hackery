package reminderhackery.persistence

import org.testcontainers.containers.DockerComposeContainer
import reminderhackery.model.Task
import reminderhackery.testcommon.StubData.dateTimeOf
import java.io.File
import kotlin.test.*

class TaskDAOTest {

    private val container = DockerComposeContainer(File("docker-compose.yml"))
        .withExposedService("postgres", 5432)

    private var taskDAO: TaskDAO? = null

    @BeforeTest
    fun beforeAll() {
        container.start()
        RemindersDB.createBlankDatabase()
        taskDAO = TaskDAO("remindersdb")
    }

    @AfterTest
    fun afterAll() {
        container.stop()
    }

    private val yesterday = dateTimeOf("2022-01-01T03:04:05.000Z")
    private val today = dateTimeOf("2022-01-02T03:04:05.000Z")
    private val tomorrow = dateTimeOf("2022-01-03T03:04:05.000Z")

    @Test
    fun createTaskThenCreatesNewTask() {
        // Given
        val task = Task(null, "task-description", today, false)

        // When
        getTaskDAO().createTask(task)

        // Then
        val tasks = getTaskDAO().getTasks()
        assertEquals(1, tasks.size)
        val actual = tasks[0]
        assertNotNull(actual.id)
        assertEquals(task.description, actual.description)
        assertEquals(task.deadline, actual.deadline)
        assertEquals(task.complete, actual.complete)
    }

    @Test
    fun updateTaskThenUpdatesTask() {
        // Given
        val task = getTaskDAO().createTask(Task(null, "task-description", today, false))
        val expectedDescription = "updated-task-description"
        val expectedDeadline = tomorrow
        val expectedComplete = true

        // When
        getTaskDAO().updateTask(task.copy(description = expectedDescription, deadline = expectedDeadline, complete = expectedComplete))

        // Then
        val tasks = getTaskDAO().getTasks()
        assertEquals(1, tasks.size)
        val actual = tasks[0]
        assertEquals(expectedDescription, actual.description)
        assertEquals(expectedDeadline, actual.deadline)
        assertEquals(expectedComplete, actual.complete)
    }

    @Test
    fun getTasksThenReturnsTasks() {
        // Given
        val tasks = listOf(
            Task(null, "task-1", yesterday, true),
            Task(null, "task-2", today, false),
            Task(null, "task-3", tomorrow, false),
        )
        tasks.forEach { task -> getTaskDAO().createTask(task) }

        // When
        val actual = getTaskDAO().getTasks()

        // Then
        assertEquals(3, actual.size)
        tasks.zip(actual).forEach { (expected, actual) ->
            assertEquals(expected.description, actual.description)
            assertEquals(expected.deadline, actual.deadline)
            assertEquals(expected.complete, actual.complete)
        }
    }

    @Test
    fun getTasksThenReturnsTasksWithDeadlineBeforeNow() {
        // Given
        val tasks = listOf(
            Task(null, "task-1", yesterday, false),
            Task(null, "task-2", today, false),
            Task(null, "task-3", tomorrow, false),
        )
        tasks.forEach { task -> getTaskDAO().createTask(task) }

        // When
        val actual = getTaskDAO().getDueTasks(today)

        // Then
        assertEquals(2, actual.size)
        tasks.zip(actual).forEach { (expected, actual) ->
            assertEquals(expected.description, actual.description)
            assertEquals(expected.deadline, actual.deadline)
            assertEquals(expected.complete, actual.complete)
        }
    }

    private fun getTaskDAO(): TaskDAO {
        return taskDAO!!
    }
}