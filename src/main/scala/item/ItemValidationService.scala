package item

import item.db.ItemRepo
import item.db.ItemTable.ItemRow
import scala.concurrent.{ExecutionContext, Future}

class ItemValidationService(itemRepo: ItemRepo)(implicit executionContext: ExecutionContext) {

  def validate(items: List[Item]): Future[Int] = {
    (for {
      isItemsExist <- isItemsExists(items) if isItemsExist
      containsUnavailableItems <- containsUnavailable(items) if !containsUnavailableItems
      isAboveMin <- isAboveMin(items) if isAboveMin
      isNotFraud <- isNotFraud(items, isInRange) if isNotFraud
      totalAmount <- getTotalAmount(items)
    } yield totalAmount).recover(e => throw new Exception("failed to validate item"))
  }

  private def isItemsExists(items: List[Item]): Future[Boolean] = {
    itemRepo.isItemsExists(items)
  }

  def containsUnavailable(items: List[Item]): Future[Boolean] = {
    itemRepo.containsUnavailable(items)
  }

  def getTotalAmount(items: List[Item]): Future[Int] = {
    itemRepo.getTotalAmount(items)
  }

  def getOrderedItems(items: List[Item]): Future[Seq[ItemRow]] = {
    itemRepo.getOrderedItems(items)
  }

  def isAboveMin(items: List[Item]): Future[Boolean] = {
    for {
      amount <- getTotalAmount(items)
    } yield amount > 100
  }

  def isInRange(items: Seq[ItemRow]): Boolean = {
    items.map(_.price.get).sum < 1500
  }

  def isNotFraud(items: List[Item], validators: (Seq[ItemRow] => Boolean)*): Future[Boolean] = {
    val validationResult: Future[Seq[Boolean]] = for {
      orderedItems <- getOrderedItems(items)
    } yield validators.map(_.apply(orderedItems))

    validationResult.map(re => re.reduce((r, e) => r && e))
  }

}
