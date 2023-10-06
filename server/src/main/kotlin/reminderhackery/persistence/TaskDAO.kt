package reminderhackery.persistence

import reminderhackery.model.Task
import java.util.*

class TaskDAO {

    private var tasks: List<Task> = listOf()

    fun createTask(task: Task): Task {
        if (task.id != null) {
            throw IllegalArgumentException("Attempting to create a task which already has an id ${task}")
        }

        val newTask = task.copy(id = generateRandomId())
        tasks += newTask

        return newTask
    }

    fun getTasks(): List<Task> {
        return tasks;
    }

    private fun generateRandomId(): String {
        return UUID.randomUUID().toString()
    }
}