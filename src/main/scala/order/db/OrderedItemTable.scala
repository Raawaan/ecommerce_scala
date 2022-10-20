package order.db

import item.db.ItemTable

object OrderedItemTable {

  val profile: slick.jdbc.JdbcProfile = slick.jdbc.MySQLProfile

  import profile.api._
  import slick.jdbc.{GetResult => GR}
  import slick.lifted.Rep

  /** Entity class storing rows of table OrderedItems
   *
   * @param id      Database column id SqlType(INT), AutoInc, PrimaryKey
   * @param orderId Database column order_id SqlType(INT)
   * @param itemId  Database column item_id SqlType(INT), Default(None) */
  case class OrderedItemsRow(id: Int, orderId: Int, itemId: Option[Int] = None)

  /** GetResult implicit for fetching OrderedItemsRow objects using plain SQL queries */
  implicit def GetResultOrderedItemsRow(implicit e0: GR[Int], e1: GR[Option[Int]]): GR[OrderedItemsRow] = GR {
    prs =>
      import prs._
      OrderedItemsRow.tupled((<<[Int], <<[Int], <<?[Int]))
  }

  /** Table description of table ordered_items. Objects of this class serve as prototypes for rows in queries. */
  class OrderedItems(_tableTag: Tag) extends profile.api.Table[OrderedItemsRow](_tableTag, Some("ecommerce"), "ordered_items") {
    def * = (id, orderId, itemId).<>(OrderedItemsRow.tupled, OrderedItemsRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(orderId), itemId)).shaped.<>({ r => import r._; _1.map(_ => OrderedItemsRow.tupled((_1.get, _2.get, _3))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column order_id SqlType(INT) */
    val orderId: Rep[Int] = column[Int]("order_id")
    /** Database column item_id SqlType(INT), Default(None) */
    val itemId: Rep[Option[Int]] = column[Option[Int]]("item_id", O.Default(None))

    /** Foreign key referencing Item (database name ordered_items_item_id_fk) */
    lazy val itemFk = foreignKey("ordered_items_item_id_fk", itemId, ItemTable.Item)(r => Rep.Some(r.id), onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Restrict)
    /** Foreign key referencing Order (database name ordered_items_order_id_fk) */
    lazy val orderFk = foreignKey("ordered_items_order_id_fk", orderId, OrderTable.Order)(r => r.id, onUpdate = ForeignKeyAction.Restrict, onDelete = ForeignKeyAction.Restrict)
  }

  /** Collection-like TableQuery object for table OrderedItems */
  lazy val OrderedItems = new TableQuery(tag => new OrderedItems(tag))
}
