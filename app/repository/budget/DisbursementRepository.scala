package repository.budget

import domain.budget.Disbursement
import repository.Repository

trait DisbursementRepository extends Repository[Disbursement]{

}
object DisbursementRepository{
  def repository: DisbursementRepositoryImpl.type = DisbursementRepositoryImpl
}