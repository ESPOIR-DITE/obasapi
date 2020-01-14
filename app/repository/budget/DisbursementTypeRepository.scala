package repository.budget

import domain.budget.DisbursementType
import repository.Repository
import repository.budget.impl.cockcroachdb.DisbursementTypeRepositoryImpl

trait DisbursementTypeRepository extends Repository[DisbursementType]{

}
object DisbursementTypeRepository{
  def repository: DisbursementTypeRepositoryImpl.type =DisbursementTypeRepositoryImpl
}
