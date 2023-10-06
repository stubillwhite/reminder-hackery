package reminderhackery.services

import org.slf4j.LoggerFactory
import reminderhackery.model.Task
import reminderhackery.persistence.TaskDAO

class TaskService(private val taskDAO: TaskDAO) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun createTask(task: Task): Task {
        val createdTask = taskDAO.createTask(task)
        logger.info("Created task ${createdTask}")
        return createdTask
    }

    fun getTasks(): List<Task> {
        val tasks = taskDAO.getTasks()
        return tasks
    }
}