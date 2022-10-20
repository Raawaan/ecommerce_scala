package item

import spray.json.DefaultJsonProtocol.{LongJsonFormat, StringJsonFormat, jsonFormat2}
import spray.json.RootJsonFormat

object ItemJsonFormatMarshaller {
  implicit val itemFormat: RootJsonFormat[Item] = jsonFormat2(Item)

}
