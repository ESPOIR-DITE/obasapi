package domain.budget

import java.time.LocalDateTime

import cats.kernel.Monoid
import play.api.libs.json.Json

case class Budget(id:String,
                  description:String,
                  amount: BigDecimal,
                  date:LocalDateTime)

object Budget{
  implicit lazy val budgeFmt = Json.format[Budget]
implicit val budgetMonoid: Monoid[Budget] =new Monoid[Budget] {
  override def empty: Budget = Budget("","",BigDecimal(0.00),LocalDateTime.now())
  override def combine(x: Budget, y: Budget): Budget = Budget("","",y.amount+x.amount,LocalDateTime.now())
}
}
