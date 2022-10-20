package order.db

import item.Item
import order.db.OrderTable.{Order, OrderRow}
import OrderedItemTable.{OrderedItems, OrderedItemsRow}
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._
import scala.concurrent.{ExecutionContext, Future}

class OrderRepo(db: MySQLProfile.backend.Database)(implicit executionContext: ExecutionContext) {

  def saveOrder(customerId: Int, item: List[Item]): Future[Int] = {
    val saveOrder = (for {
      savedOrderId <- Order returning Order.map(_.id) += OrderRow(0, customerId)
      _ <- saveOrderedItems(savedOrderId, item)
    } yield savedOrderId).transactionally
    db.run(saveOrder).recover(e => throw new Exception("failed to save order"))
  }

  private def saveOrderedItems(orderId: Int, orderedItems: List[Item]): DBIOAction[Unit, NoStream, Effect.Write] = {
    val orderedItemsRow = orderedItems
      .map(orderedItem => OrderedItemsRow(0, orderId = orderId, itemId = Some(orderedItem.id.toInt)))
    val actions = OrderedItems returning OrderedItems.map(_.id) ++= orderedItemsRow
      DBIO.seq(actions)
  }

}
