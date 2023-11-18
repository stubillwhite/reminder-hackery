package reminderhackery.services

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit4.MockKRule
import io.mockk.mockk
import io.mockk.slot
import org.junit.Rule
import reminderhackery.model.Task
import reminderhackery.persistence.TaskDAO
import reminderhackery.testcommon.StubData.dateTimeOf
import kotlin.test.Test
import kotlin.test.assertEquals

class TaskServiceTest {

    @get:Rule
    val mockkRule = MockKRule(this)

    @MockK
    private lateinit var mockTaskDAO: TaskDAO

    private val today = dateTimeOf("2022-01-02T03:04:05.000Z")

    @Test
    fun createTaskThenCreatesAndReturnsTask() {
        // Given
        val stubTask = mockk<Task>()
        val stubCreatedTask = mockk<Task>()
        every { mockTaskDAO.createTask(stubTask) } returns stubCreatedTask

        // When
        val actual = TaskService(mockTaskDAO).createTask(stubTask)

        // Then
        assertEquals(stubCreatedTask, actual)
    }

    @Test
    fun updateTaskGivenIncompleteTaskThenReturnsTask() {
        // Given
        val stubTask = Task("id", "description", today, false, 1)

        val stubUpdatedTask = mockk<Task>()
        every { mockTaskDAO.updateTask(stubTask) } returns stubUpdatedTask

        // When
        val actual = TaskService(mockTaskDAO).updateTask(stubTask)

        // Then
        assertEquals(stubUpdatedTask, actual)
    }

    @Test
    fun updateTaskGivenCompleteNonRecurringTaskThenUpdatesAndReturnsTask() {
        // Given
        val stubTask = Task("id", "description", today, true, null)

        val stubUpdatedTask = mockk<Task>()
        every { mockTaskDAO.updateTask(stubTask) } returns stubUpdatedTask

        // When
        val actual = TaskService(mockTaskDAO).updateTask(stubTask)

        // Then
        assertEquals(stubUpdatedTask, actual)
    }

    @Test
    fun updateTaskGivenCompleteRecurringTaskThenUpdatesWithNewRecurrenceAndReturnsTask() {
        // Given
        val stubTask = Task("id", "description", today, true, 1)

        val taskSlot = slot<Task>()
        every { mockTaskDAO.updateTask(capture(taskSlot)) } answers { taskSlot.captured }

        // When
        val actual = TaskService(mockTaskDAO).updateTask(stubTask)

        // Then
        val expected = stubTask.copy(deadline = today.plusDays(stubTask.recurrence!!.toLong()), complete = false)
        assertEquals(expected, actual)
    }

    @Test
    fun getTasksThenReturnsTasks() {
        // Given
        val stubTasks = mockk<List<Task>>()
        every { mockTaskDAO.getTasks() } returns stubTasks

        // When
        val actual = TaskService(mockTaskDAO).getTasks()

        // Then
        assertEquals(stubTasks, actual)
    }

    @Test
    fun getDueTasksThenReturnsTasks() {
        // Given
        val stubTasks = mockk<List<Task>>()
        every { mockTaskDAO.getDueTasks(any()) } returns stubTasks

        // When
        val actual = TaskService(mockTaskDAO).getDueTasks()

        // Then
        assertEquals(stubTasks, actual)
    }
}