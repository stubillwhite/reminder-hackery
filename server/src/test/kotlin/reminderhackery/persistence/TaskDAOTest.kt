package reminderhackery.persistence

import org.testcontainers.containers.DockerComposeContainer
import reminderhackery.model.Task
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

    @Test
    fun createTaskThenCreatesNewTask() {
        // Given
        val task = Task(null, "task-description")

        // When
        getTaskDAO().createTask(task)

        // Then
        val tasks = getTaskDAO().getTasks()
        assertEquals(1, tasks.size)
        assertNotNull(tasks[0].id)
        assertEquals(task.description, tasks[0].description)
    }

    @Test
    fun updateTaskThenUpdatesTask() {
        // Given
        val task = getTaskDAO().createTask(Task(null, "task-description"))
        val expectedDescription = "updated-task-description"

        // When
        getTaskDAO().updateTask(task.copy(description = expectedDescription))

        // Then
        val tasks = getTaskDAO().getTasks()
        assertEquals(1, tasks.size)
        assertEquals(expectedDescription, tasks[0].description)
    }

    @Test
    fun getTasksThenReturnsTasks() {
        // Given
        getTaskDAO().createTask(Task(null, "task-1"))
        getTaskDAO().createTask(Task(null, "task-2"))
        getTaskDAO().createTask(Task(null, "task-3"))

        // When
        val tasks = getTaskDAO().getTasks()

        // Then
        assertEquals(3, tasks.size)
        val actualDescriptions = tasks.map { it.description }
        assertContentEquals(listOf("task-1", "task-2", "task-3"), actualDescriptions)
    }

    private fun getTaskDAO(): TaskDAO {
        return taskDAO!!
    }
}