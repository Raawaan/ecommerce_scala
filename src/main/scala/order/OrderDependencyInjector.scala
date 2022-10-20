package order

import akka.actor.typed.ActorSystem
import customer.CustomerBalanceService
import item.ItemValidationService
import order.db.OrderRepo
import slick.jdbc.MySQLProfile

import scala.concurrent.ExecutionContext

class OrderDependencyInjector(implicit executionContext: ExecutionContext,
                              system: ActorSystem[Nothing],
                              db: MySQLProfile.backend.Database) {

  def inject(customerBalance: CustomerBalanceService, itemValidation: ItemValidationService) = {
    val orderRepo = new OrderRepo(db)
    val orderCreator = new OrderService(customerBalance, itemValidation, orderRepo)
    val orderController = new OrderController(orderCreator)
    orderController
  }
}
