package item.db


object ItemTable {

  val profile: slick.jdbc.JdbcProfile = slick.jdbc.MySQLProfile
  import profile.api._

  import slick.lifted.Rep
  import slick.jdbc.{GetResult => GR}

  /** Entity class storing rows of table Item
   *
   * @param id          Database column id SqlType(INT), AutoInc, PrimaryKey
   * @param name        Database column name SqlType(VARCHAR), Length(200,true), Default(None)
   * @param price       Database column price SqlType(INT), Default(None)
   * @param isavailable Database column isAvailable SqlType(BIT), Default(None) */
  case class ItemRow(id: Int, name: Option[String] = None, price: Option[Int] = None, isavailable: Option[Boolean] = None)

  /** GetResult implicit for fetching ItemRow objects using plain SQL queries */
  implicit def GetResultItemRow(implicit e0: GR[Int], e1: GR[Option[String]], e2: GR[Option[Int]], e3: GR[Option[Boolean]]): GR[ItemRow] = GR {
    prs =>
      import prs._
      ItemRow.tupled((<<[Int], <<?[String], <<?[Int], <<?[Boolean]))
  }

  /** Table description of table item. Objects of this class serve as prototypes for rows in queries. */
  class Item(_tableTag: Tag) extends profile.api.Table[ItemRow](_tableTag, Some("ecommerce"), "item") {
    def * = (id, name, price, isavailable).<>(ItemRow.tupled, ItemRow.unapply)

    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), name, price, isavailable)).shaped.<>({ r => import r._; _1.map(_ => ItemRow.tupled((_1.get, _2, _3, _4))) }, (_: Any) => throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(200,true), Default(None) */
    val name: Rep[Option[String]] = column[Option[String]]("name", O.Length(200, varying = true), O.Default(None))
    /** Database column price SqlType(INT), Default(None) */
    val price: Rep[Option[Int]] = column[Option[Int]]("price", O.Default(None))
    /** Database column isAvailable SqlType(BIT), Default(None) */
    val isavailable: Rep[Option[Boolean]] = column[Option[Boolean]]("isAvailable", O.Default(None))
  }

  /** Collection-like TableQuery object for table Item */
  lazy val Item = new TableQuery(tag => new Item(tag))

}
