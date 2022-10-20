import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import akka.http.scaladsl.model.{ContentTypes, HttpEntity}
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import order.OrderCommandJsonFormatMarshaller.orderCommandFormat
import order.{OrderController, OrderRequest}

class Routes(orderController:OrderController) {
  val route: Route =
    path("health") {
      get {
        complete(HttpEntity(ContentTypes.`text/html(UTF-8)`, "<h1>I'm running/h1>"))
      }
    } ~
      post {
        path("checkout") {
          entity(as[OrderRequest]) { order =>
           orderController.create(order)
          }
        }
      }
}

