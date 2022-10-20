package customer.db

object CustomerTable {
  val profile: slick.jdbc.JdbcProfile = slick.jdbc.MySQLProfile
  import profile.api._

  import slick.lifted.Rep
  import slick.jdbc.{GetResult => GR}
  /** Entity class storing rows of table Customer
   *  @param id Database column id SqlType(INT), AutoInc, PrimaryKey
   *  @param name Database column name SqlType(VARCHAR), Length(50,true) */
  case class CustomerRow(id: Int, name: String)
  /** GetResult implicit for fetching CustomerRow objects using plain SQL queries */
  implicit def GetResultCustomerRow(implicit e0: GR[Int], e1: GR[String]): GR[CustomerRow] = GR{
    prs => import prs._
      CustomerRow.tupled((<<[Int], <<[String]))
  }
  /** Table description of table customer. Objects of this class serve as prototypes for rows in queries. */
  class Customer(_tableTag: Tag) extends profile.api.Table[CustomerRow](_tableTag, Some("ecommerce"), "customer") {
    def * = (id, name).<>(CustomerRow.tupled, CustomerRow.unapply)
    /** Maps whole row to an option. Useful for outer joins. */
    def ? = ((Rep.Some(id), Rep.Some(name))).shaped.<>({r=>import r._; _1.map(_=> CustomerRow.tupled((_1.get, _2.get)))}, (_:Any) =>  throw new Exception("Inserting into ? projection not supported."))

    /** Database column id SqlType(INT), AutoInc, PrimaryKey */
    val id: Rep[Int] = column[Int]("id", O.AutoInc, O.PrimaryKey)
    /** Database column name SqlType(VARCHAR), Length(50,true) */
    val name: Rep[String] = column[String]("name", O.Length(50,varying=true))
  }
  /** Collection-like TableQuery object for table Customer */
  lazy val Customer = new TableQuery(tag => new Customer(tag))

}
