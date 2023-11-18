package reminderhackery.services

import org.slf4j.LoggerFactory
import reminderhackery.model.Task
import reminderhackery.persistence.TaskDAO
import reminderhackery.utils.TemporalUtils.dateTimeNow

class TaskService(private val taskDAO: TaskDAO) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun createTask(task: Task): Task {
        val createdTask = taskDAO.createTask(task)
        logger.info("Created task ${createdTask}")
        return createdTask
    }

    fun updateTask(task: Task): Task {
        val updatedTask = taskDAO.updateTask(task)
        logger.info("Updated task ${updatedTask}")
        return updatedTask
    }

    fun getTasks(): List<Task> {
        return taskDAO.getTasks()
    }

    fun getDueTasks(): List<Task> {
        return taskDAO.getDueTasks(dateTimeNow())
    }
}