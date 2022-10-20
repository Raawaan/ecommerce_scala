package order

import order.OrderJsonFormatMarshaller.orderFormat
import spray.json.DefaultJsonProtocol.{IntJsonFormat, jsonFormat2}
import spray.json.RootJsonFormat

object OrderCommandJsonFormatMarshaller {
  implicit val orderCommandFormat: RootJsonFormat[OrderRequest] = jsonFormat2(OrderRequest)

}
