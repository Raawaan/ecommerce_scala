package customer

import customer.client.PaymentClient
import scala.concurrent.{ExecutionContext, Future}

class CustomerBalanceService(paymentClient: PaymentClient)(implicit executionContext: ExecutionContext) {

  def deduct(amount: Int, customerId: Int): Future[Boolean] = {
    paymentClient.deduct(amount, customerId).recover(e => throw new Exception("failed to deduct amount"))
  }

}
