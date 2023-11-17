package reminderhackery.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.ExperimentalSerializationApi
import reminderhackery.model.Ping
import reminderhackery.model.Pong
import reminderhackery.model.Task
import reminderhackery.services.TaskService

@OptIn(ExperimentalSerializationApi::class)
fun Application.configureRouting(taskService: TaskService) {

    routing {
        staticResources("/", "web")

        post("/ping") {
            val ping = call.receive<Ping>()
            val pong = Pong(ping.message, ping.number)
            call.respond(pong)
        }

        post("/tasks") {
            val task = call.receive<Task>()
            val createdTask = taskService.createTask(task)
            call.response.status(HttpStatusCode.Created)
            call.respond(createdTask)
        }

        put("/tasks") {
            val task = call.receive<Task>()
            val createdTask = taskService.updateTask(task)
            call.response.status(HttpStatusCode.Created)
            call.respond(createdTask)
        }

        get("/tasks") {
            val tasks = taskService.getTasks()
            call.respond(tasks)
        }

        get("/tasks/due") {
            val tasks = taskService.getDueTasks()
            call.respond(tasks)
        }
    }
}
