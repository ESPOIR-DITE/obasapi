package domain.budget

import java.time.LocalDateTime
import cats.kernel.Monoid


case class Awards(id: String,
                  email: String,
                  amount: BigDecimal,
                  date: LocalDateTime)

object Awards {
  implicit lazy val awardsFmt = Json.format[Awards]
  implicit val awardMonoid: Monoid[Awards] = new Monoid[Awards] {
    def empty: Awards = Awards("", "", BigDecimal(0.00), LocalDateTime.now())
    def combine(x: Awards, y: Awards): Awards = Awards("","", y.amount + x.amount, LocalDateTime.now())
  }
}
