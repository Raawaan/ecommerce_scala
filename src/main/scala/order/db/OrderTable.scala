package order.db

import customer.db.CustomerTable

object OrderTable {
  val profile: slick.jdbc.JdbcProfile = slick.jdbc.MySQLProfile
  import profile.api._

  import slick.lifted.Rep
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table Order
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param customerId Database column customer_id SqlType(INT) */
  case class OrderRow(id: Int, customerId: Int)
  /** GetResult implicit for fetching OrderRow objects using plain SQL queries */
  implicit def GetResultOrderRow(implicit e0: GR[Int]): GR[OrderRow] = GR{
    prs => import prs._
      OrderRow.tupled((<<[Int], <<[Int]))
  }
  /** Table description of table order. Objects of this class serve as prototypes for rows in queries. */
  class Order(_tableTag: Tag) extends profile.api.Table[OrderRow](_tableTag, Some("ecommerce"), "order") {
    def * = (id, customerId).<>(OrderRow.tupled, OrderRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(customerId))).shaped.<>({r=>import r._; _1.map(_=> OrderRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column customer_id SqlType(INT) */
    val customerId: Rep[Int] = column[Int]("customer_id")

    /** Foreign key referencing Customer (database name order_customer_id_fk) */
    lazy val customerFk = foreignKey("order_customer_id_fk", customerId, CustomerTable.Customer)(r => r.id, onUpdate=ForeignKeyAction.Restrict, onDelete=ForeignKeyAction.Restrict)
  }
  /** Collection-like TableQuery object for table Order */
  lazy val Order = new TableQuery(tag => new Order(tag))

}
