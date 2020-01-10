package services.budget

import domain.budget.{Awards, Budget}

import scala.concurrent.Future

trait BudgetService {

  def awardBusary(awards: Awards):Future[Boolean]
  def addBudget(budget:Budget):Future[Boolean]
  def totalAwards: Future[List[Awards]]
  def totalSumAwards:Future[BigDecimal]
  def totalBudget:Future[BigDecimal]

}
