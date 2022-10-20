package item

import item.db.ItemRepo
import item.db.ItemTable.ItemRow
import org.mockito.Mockito
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ItemValidationServiceTest extends AnyFlatSpec with should.Matchers with ScalaFutures {
  val itemRepo: ItemRepo = Mockito.mock(classOf[ItemRepo])
  val itemValidation = new ItemValidationService(itemRepo)
  val items = List(Item("name", 1L))

  "validate amount above min" should "success for 1000" in {
    Mockito.when(itemRepo.getTotalAmount(items)).thenReturn(Future(1000))
    whenReady(itemValidation.isAboveMin(items)) { isAboveMin => isAboveMin shouldBe true }
  }
  "validate amount above min" should "fail for 80" in {
    Mockito.when(itemRepo.getTotalAmount(items)).thenReturn(Future(80))
    whenReady(itemValidation.isAboveMin(items)) { isAboveMin => isAboveMin shouldBe false }
  }

  "validate amount isInRange" should "success for 200" in {
    val itemsRows = Seq(
      ItemRow(1, Some("name"), Some(100), Some(true)),
      ItemRow(2, Some("name"), Some(100), Some(true))
    )
    itemValidation.isInRange(itemsRows) shouldBe true
  }

  "validate amount isInRange" should "fail for 1600" in {
    val itemsRows = Seq(
      ItemRow(1, Some("name"), Some(100), Some(true)),
      ItemRow(2, Some("name"), Some(1500), Some(true)))
    itemValidation.isInRange(itemsRows)shouldBe false
  }

  "validate amount isNotFraud" should "success for 300" in {
    val itemsRows = Seq(
      ItemRow(1, Some("name"), Some(100), Some(true)),
      ItemRow(2, Some("name"), Some(200), Some(true)))
    Mockito.when(itemRepo.getOrderedItems(items)).thenReturn(Future(itemsRows))
    whenReady(itemValidation.isNotFraud(items,itemValidation.isInRange)) { isNotFraud => isNotFraud shouldBe true }
  }

  "validate amount isNotFraud" should "fail for 1700" in {
    val itemsRows = Seq(
      ItemRow(1, Some("name"), Some(300), Some(true)),
      ItemRow(2, Some("name"), Some(1500), Some(true)))
    Mockito.when(itemRepo.getOrderedItems(items)).thenReturn(Future(itemsRows))

    whenReady(itemValidation.isNotFraud(items,itemValidation.isInRange)) { isNotFraud => isNotFraud shouldBe false }
  }

  "validate items" should "success" in {
    val itemsRows = Seq(
      ItemRow(1, Some("name"), Some(200), Some(true)))
    Mockito.when(itemRepo.getOrderedItems(items)).thenReturn(Future(itemsRows))
    Mockito.when(itemRepo.isItemsExists(items)).thenReturn(Future(true))
    Mockito.when(itemRepo.containsUnavailable(items)).thenReturn(Future(false))
    Mockito.when(itemRepo.getTotalAmount(items)).thenReturn(Future(200))
    Mockito.when(itemRepo.getOrderedItems(items)).thenReturn(Future(itemsRows))

    whenReady(itemValidation.validate(items)) { isValid => isValid shouldBe 200 }
  }

  "validate items" should "not complete for unavailable item" in {
    val itemsRows = Seq(
      ItemRow(1, Some("name"), Some(200), Some(true)))
    Mockito.when(itemRepo.getOrderedItems(items)).thenReturn(Future(itemsRows))
    Mockito.when(itemRepo.isItemsExists(items)).thenReturn(Future(true))
    Mockito.when(itemRepo.containsUnavailable(items)).thenReturn(Future(true))
    Mockito.when(itemRepo.getTotalAmount(items)).thenReturn(Future(200))
    Mockito.when(itemRepo.getOrderedItems(items)).thenReturn(Future(itemsRows))

   itemValidation.validate(items).isCompleted shouldBe false
  }

}
