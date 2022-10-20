package item

import akka.actor.typed.ActorSystem
import item.db.ItemRepo
import slick.jdbc.MySQLProfile

import scala.concurrent.ExecutionContext

class ItemDependencyInjector(implicit executionContext: ExecutionContext,
                             system: ActorSystem[Nothing],
                             db: MySQLProfile.backend.Database) {

  def inject: ItemValidationService = {
    val itemRepo = new ItemRepo(db)
    val itemValidation = new ItemValidationService(itemRepo)
    itemValidation
  }
}
