package customer

import akka.actor.typed.ActorSystem
import customer.client.PaymentClient
import slick.jdbc.MySQLProfile

import scala.concurrent.ExecutionContext

class CustomerDependencyInjector(implicit executionContext: ExecutionContext,
                                 system: ActorSystem[Nothing],
                                 db: MySQLProfile.backend.Database) {

   def inject: CustomerBalanceService = {
    val paymentClient = new PaymentClient()
    val customerBalance = new CustomerBalanceService(paymentClient)
    customerBalance
  }
}
