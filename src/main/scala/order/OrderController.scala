package order

import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.server.Directives.{complete, onComplete}
import akka.http.scaladsl.server.Route
import scala.util.{Failure, Success}

class OrderController(orderCreator: OrderService) {
  def create(order: OrderRequest): Route = {
    val future = orderCreator.create(order)
    onComplete(future) {
      case Failure(exception) => complete(HttpResponse(InternalServerError, entity = exception.getMessage))
      case Success(orderId) => complete(s"order created id : ${orderId}")

    }
  }
}
