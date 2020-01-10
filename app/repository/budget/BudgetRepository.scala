package repository.budget

import domain.budget.Budget
import repository.Repository
import repository.budget.impl.cockcroachdb.BudgetRepositoryImpl

import scala.concurrent.Future

trait BudgetRepository extends Repository[Budget]{

}

object BudgetRepository{
 def repository: BudgetRepositoryImpl.type = BudgetRepositoryImpl
}
