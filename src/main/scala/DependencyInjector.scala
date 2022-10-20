import akka.actor.typed.ActorSystem
import akka.http.scaladsl.server.Route
import customer.{CustomerDependencyInjector, CustomerBalanceService}
import item.{ItemDependencyInjector, ItemValidationService}
import order.{OrderController, OrderDependencyInjector}
import slick.jdbc.MySQLProfile

import scala.concurrent.ExecutionContext

class DependencyInjector(implicit executionContext: ExecutionContext,
                         system: ActorSystem[Nothing],
                         db: MySQLProfile.backend.Database) {
  def inject(): Route = {
    val customerBalance: CustomerBalanceService = new CustomerDependencyInjector().inject
    val itemValidation: ItemValidationService = new ItemDependencyInjector().inject
    val orderController: OrderController = new OrderDependencyInjector().inject(customerBalance, itemValidation)
    new Routes(orderController).route
  }
}
