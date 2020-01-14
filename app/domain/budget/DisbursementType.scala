package domain.budget

import cats.kernel.Monoid
import play.api.libs.json.Json

case class DisbursementType(
                           id : String,
                           disbursementType:String,
                           describe: String
                           )
object DisbursementType{
  implicit lazy val disbursementFmt = Json.format[DisbursementType]
  implicit val disbursementMonoid = new Monoid[Disbursement] {
    override def empty: Disbursement = ???

    override def combine(x: Disbursement, y: Disbursement): Disbursement = ???
  }
}
