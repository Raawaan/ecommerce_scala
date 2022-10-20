import akka.actor.typed.ActorSystem
import akka.actor.typed.scaladsl.Behaviors
import akka.http.scaladsl.Http
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.backend.Database
import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Application {

  def main(args: Array[String]): Unit = {

    implicit val system: ActorSystem[Nothing] = ActorSystem(Behaviors.empty, "ecommerce-system")
    implicit val executionContext: ExecutionContextExecutor = system.executionContext
    implicit val db: MySQLProfile.backend.Database = Database.forConfig("mysql")

    val route = new DependencyInjector().inject()

    val bindingFuture = Http().newServerAt("localhost", 8080).bind(route)

    println(s"Press RETURN to stop...")

    StdIn.readLine()

    bindingFuture
      .flatMap(_.unbind())
      .onComplete(_ => system.terminate())
  }
}
//    slick.codegen.SourceCodeGenerator.main(
//      Array("slick.jdbc.MySQLProfile", "com.mysql.cj.jdbc.Driver", "jdbc:mysql://localhost:3308/ecommerce", "here", "", "root", "4kk45C4L4")
//    )
