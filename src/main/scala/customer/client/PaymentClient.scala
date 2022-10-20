package customer.client

import akka.actor.typed.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{ContentTypes, HttpEntity, HttpMethods, HttpRequest}

import scala.concurrent.{ExecutionContext, Future}

class PaymentClient(implicit system: ActorSystem[Nothing], executionContext: ExecutionContext) {
  def deduct(amount: Int,customerId:Int): Future[Boolean] = {
    val request = HttpRequest(
      method = HttpMethods.POST,
      uri = s"http://localhost:3000/account/${customerId}/deduct",
      entity=HttpEntity(
        ContentTypes.`application/json`,
        s"{\"amount\":\"${amount}\"}"
      )
    )
    Http().singleRequest(request).map(request => request.status.isSuccess())
  }
}
