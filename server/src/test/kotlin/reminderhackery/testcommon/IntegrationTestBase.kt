package reminderhackery.testcommon

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.testing.*
import kotlinx.serialization.json.Json
import org.testcontainers.containers.DockerComposeContainer
import reminderhackery.persistence.RemindersDB
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

open class IntegrationTestBase {

    private val container = DockerComposeContainer(File("docker-compose.yml"))
        .withExposedService("postgres", 5432)

    @BeforeTest
    fun beforeAll() {
        container.start()
        RemindersDB.createBlankDatabase()
    }

    @AfterTest
    fun afterAll() {
        container.stop()
    }

    protected fun ApplicationTestBuilder.httpClient(): HttpClient {
        val client = createClient {
            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
        return client
    }
}