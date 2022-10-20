import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.server._
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalamock.scalatest.MockFactory
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.backend.Database
import scala.concurrent.ExecutionContextExecutor

class HealthCheckSpec extends AnyWordSpec with ScalatestRouteTest with Matchers with ScalaFutures
  with MockFactory {
  implicit val actor: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "ecommerce-system")
  implicit val executionContext: ExecutionContextExecutor = actor.executionContext
  implicit val db: MySQLProfile.backend.Database = Database.forConfig("h2db")

  private val route: Route = new DependencyInjector().inject()
  "health check endpoint" should {
    "return i'm running for GET requests to the /health path" in {
      Get("/health") ~> route ~> check {
        responseAs[String] shouldEqual "<h1>I'm running/h1>"
      }
    }
  }
}
