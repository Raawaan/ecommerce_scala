package order

import item.ItemJsonFormatMarshaller.itemFormat
import spray.json.DefaultJsonProtocol.{jsonFormat1, listFormat}
import spray.json.RootJsonFormat

object OrderJsonFormatMarshaller {
  implicit val orderFormat: RootJsonFormat[Order] = jsonFormat1(Order)

}
