package repository.budget

import domain.budget.Disbursement
import repository.Repository
import repository.budget.impl.cockcroachdb.DisbursementRepositoryImpl

trait DisbursementRepository extends Repository[Disbursement]{

}
object DisbursementRepository{
  def repository: DisbursementRepositoryImpl.type = DisbursementRepositoryImpl
}