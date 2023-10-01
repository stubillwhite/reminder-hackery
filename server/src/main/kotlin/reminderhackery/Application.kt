package reminderhackery

import io.ktor.server.application.*
import reminderhackery.persistence.TaskDAO
import reminderhackery.plugins.configureContentNegotation
import reminderhackery.plugins.configureHTTP
import reminderhackery.plugins.configureRouting
import reminderhackery.services.TaskService
import reminderhackery.utils.ConfigUtils.loadConfig

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.yaml references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    val appEnvironment = environment.config.propertyOrNull("app.environment")?.getString()

    val appConfig = loadConfig(appEnvironment)
    val taskService = TaskService(TaskDAO())

    configureHTTP()
    configureRouting(taskService)
    configureContentNegotation()
}
