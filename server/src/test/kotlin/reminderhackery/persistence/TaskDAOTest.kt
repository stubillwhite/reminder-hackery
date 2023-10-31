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
        val task = Task(null, "task-description", today)

        // When
        getTaskDAO().createTask(task)

        // Then
        val tasks = getTaskDAO().getTasks()
        assertEquals(1, tasks.size)
        assertNotNull(tasks[0].id)
        assertEquals(task.description, tasks[0].description)
        assertEquals(task.deadline, tasks[0].deadline)
    }

    @Test
    fun updateTaskThenUpdatesTask() {
        // Given
        val task = getTaskDAO().createTask(Task(null, "task-description", today))
        val expectedDescription = "updated-task-description"
        val expectedDeadline = tomorrow

        // When
        getTaskDAO().updateTask(task.copy(description = expectedDescription, deadline = expectedDeadline))

        // Then
        val tasks = getTaskDAO().getTasks()
        assertEquals(1, tasks.size)
        assertEquals(expectedDescription, tasks[0].description)
        assertEquals(expectedDeadline, tasks[0].deadline)
    }

    @Test
    fun getTasksThenReturnsTasks() {
        // Given
        getTaskDAO().createTask(Task(null, "task-1", yesterday))
        getTaskDAO().createTask(Task(null, "task-2", today))
        getTaskDAO().createTask(Task(null, "task-3", tomorrow))

        // When
        val tasks = getTaskDAO().getTasks()

        // Then
        assertEquals(3, tasks.size)
        assertContentEquals(listOf("task-1", "task-2", "task-3"), tasks.map { it.description })
        assertContentEquals(listOf(yesterday, today, tomorrow), tasks.map { it.deadline })
    }

    private fun getTaskDAO(): TaskDAO {
        return taskDAO!!
    }
}