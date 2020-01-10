package domain.budget

import java.time.LocalDateTime

import play.api.libs.json.Json

case class Budget(id:String,
                  description:String,
                  amount: BigDecimal,
                  date:LocalDateTime)

object Budget{
  implicit lazy val budgeFmt = Json.format[Budget]

}
