package reminderhackery.persistence

import kotliquery.HikariCP
import kotliquery.LoanPattern.using
import kotliquery.Row
import kotliquery.queryOf
import kotliquery.sessionOf
import org.slf4j.LoggerFactory
import reminderhackery.model.Task
import java.util.*

class TaskDAO(private val name: String) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    private var tasks: List<Task> = listOf()

    fun createTask(task: Task): Task {
        if (task.id != null) {
            throw IllegalArgumentException("Attempting to create a task which already has an id ${task}")
        }

        val id = UUID.randomUUID().toString()

        return using(sessionOf(HikariCP.dataSource(name))) { session ->
            val sql = """
                INSERT INTO tasks
                  (id, description)
                VALUES
                  (?, ?)
            """.trimIndent()

            val (_, description) = task

            session.run(queryOf(sql, id, description).asUpdate)

            logger.info("Created task $task with ID $id")

            task.copy(id = id)
        }
    }

    fun updateTask(task: Task): Task {
        return using(sessionOf(HikariCP.dataSource(name))) { session ->
            val sql = """
                UPDATE tasks
                  SET description = ?
                WHERE 
                  id = ?
            """.trimIndent()

            val (id, description) = task

            session.run(queryOf(sql, description, id).asUpdate)

            logger.info("Update task $task")

            task
        }
    }

    fun getTasks(): List<Task> {
        return using(sessionOf(HikariCP.dataSource(name))) { session ->
            val sql = """
                SELECT
                    *
                FROM tasks
            """.trimIndent()

            session.run(queryOf(sql).map { extractTask(it) }.asList)
        }
    }

    private fun extractTask(row: Row): Task {
        return Task(
            row.string("id"),
            row.string("description")
        )
    }
}