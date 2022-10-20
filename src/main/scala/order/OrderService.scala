package order

import customer.CustomerBalanceService
import item.ItemValidationService
import order.db.OrderRepo
import scala.concurrent.{ExecutionContext, Future}

class OrderService(customerBalance: CustomerBalanceService, itemValidation: ItemValidationService, orderRepo: OrderRepo)
                  (implicit executionContext: ExecutionContext) {

  def create(orderCommand: OrderRequest): Future[Int] = {
    for {
      totalAmount <- itemValidation.validate(orderCommand.order.items)
      isDeductedSuccessfully <- customerBalance.deduct(totalAmount, orderCommand.customerId) if isDeductedSuccessfully
      savedOrderId <- orderRepo.saveOrder(orderCommand.customerId, orderCommand.order.items)
    } yield savedOrderId
  }

}
