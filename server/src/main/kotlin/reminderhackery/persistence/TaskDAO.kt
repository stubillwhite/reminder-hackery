package reminderhackery.persistence

import kotliquery.HikariCP
import kotliquery.LoanPattern.using
import kotliquery.Row
import kotliquery.queryOf
import kotliquery.sessionOf
import org.slf4j.LoggerFactory
import reminderhackery.model.Task
import java.time.ZoneOffset
import java.time.ZonedDateTime
import java.util.*

class TaskDAO(private val name: String) {

    private val logger = LoggerFactory.getLogger(this.javaClass)

    fun createTask(task: Task): Task {
        if (task.id != null) {
            throw IllegalArgumentException("Attempting to create a task which already has an id ${task}")
        }

        val id = UUID.randomUUID().toString()

        return using(sessionOf(HikariCP.dataSource(name))) { session ->
            val sql = """
                INSERT INTO tasks
                  (id, description, deadline)
                VALUES
                  (?, ?, ?)
            """.trimIndent()

            val (_, description, deadline) = task

            session.run(queryOf(sql, id, description, deadline.toOffsetDateTime()).asUpdate)

            logger.info("Created task $task with ID $id")

            task.copy(id = id)
        }
    }

    fun updateTask(task: Task): Task {
        return using(sessionOf(HikariCP.dataSource(name))) { session ->
            val sql = """
                UPDATE tasks
                  SET description = ?, deadline = ?
                WHERE 
                  id = ?
            """.trimIndent()

            val (id, description, deadline) = task

            session.run(queryOf(sql, description, deadline.toOffsetDateTime(), id).asUpdate)

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

    fun getDueTasks(dateTime: ZonedDateTime): List<Task> {
        return using(sessionOf(HikariCP.dataSource(name))) { session ->
            val sql = """
                SELECT
                  *
                FROM tasks
                WHERE 
                  deadline <= ?
            """.trimIndent()

            session.run(queryOf(sql, dateTime).map { extractTask(it) }.asList)
        }
    }

    private fun extractTask(row: Row): Task {
        return Task(
            row.string("id"),
            row.string("description"),
            row.offsetDateTime("deadline").atZoneSameInstant(ZoneOffset.UTC)
        )
    }
}