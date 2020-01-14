package repository.budget

import domain.budget.DisbursementType
import repository.Repository

trait DisbursementTypeRepository extends Repository[DisbursementType]{

}
object DisbursementTypeRepository{
  def repository: DisbursementTypeRepository.type =DisbursementTypeRepository
}
