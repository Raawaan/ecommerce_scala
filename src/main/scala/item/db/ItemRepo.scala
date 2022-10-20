package item.db

import item.Item
import item.db.ItemTable.ItemRow
import slick.jdbc.MySQLProfile
import slick.jdbc.MySQLProfile.api._

import scala.concurrent.{ExecutionContext, Future}

class ItemRepo(db: MySQLProfile.backend.Database)(implicit executionContext:ExecutionContext) {

  def isItemsExists(items: List[Item]): Future[Boolean] = {
    db.run(
      ItemTable
        .Item
        .filter(item => item.id.inSet(items.map(_.id.toInt)))
        .length
        .result
        .map(count => count == items.length)
    ).recover(e=>true)
  }

  def containsUnavailable(items: List[Item]): Future[Boolean] = {
    db.run(
      ItemTable
        .Item
        .filter(item => item.id.inSet(items.map(_.id.toInt)) && !item.isavailable)
        .exists
        .result
    )
  }

  def getTotalAmount(items: List[Item]): Future[Int] = {
    db.run(
      ItemTable
        .Item
        .filter(item => item.id.inSet(items.map(_.id.toInt)) && item.isavailable)
        .map(_.price)
        .sum
        .getOrElse(0)
        .result
    )
  }

   def getOrderedItems(items: List[Item]): Future[Seq[ItemRow]] = {
    db.run(
      ItemTable
        .Item
        .filter(item => item.id.inSet(items.map(_.id.toInt)))
        .result
    )
  }

}
