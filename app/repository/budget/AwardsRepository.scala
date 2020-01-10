package repository.budget

import domain.budget.Awards
import repository.Repository
import repository.budget.impl.cockcroachdb.AwardsRepositoryImpl

trait AwardsRepository extends Repository[Awards]{
}

object AwardsRepository{
 def repository: AwardsRepositoryImpl.type = AwardsRepositoryImpl
}
