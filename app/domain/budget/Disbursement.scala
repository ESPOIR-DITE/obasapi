package domain.budget

import java.time.LocalDateTime

import cats.kernel.Monoid
import play.api.libs.json.Json

case class Disbursement(
                       id:String,
                       awardID:String,
                       amount:BigDecimal,
                       disbursementType:String,
                       date:LocalDateTime
                       )
object Disbursement{
  implicit lazy val disbursementFmt = Json.format[Disbursement]

  implicit val disbursementMonoid = new Monoid[Disbursement] {
    override def empty: Disbursement = ???

    override def combine(x: Disbursement, y: Disbursement): Disbursement = ???
  }
}
