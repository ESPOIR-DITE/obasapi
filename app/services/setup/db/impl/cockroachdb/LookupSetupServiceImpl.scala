package services.setup.db.impl.cockroachdb

import services.application.ApplicantTypeService
import services.setup.db.LookupSetupService
import services.util.generic.GenericStatusService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class LookupSetupServiceImpl extends LookupSetupService {

  def loadGenericStatusLookupData(): Future[Boolean] = for {
    results <- GenericStatusService.roach.createInitialData
  } yield results.forall( _ == true)

  def loadGenericApplicantTypeData(): Future[Boolean] = for {
    results <- ApplicantTypeService.roach.createInitialData
  } yield results.forall( _ == true)

  override def loadLookupData: Future[Boolean] = {
    loadGenericStatusLookupData()
    loadGenericApplicantTypeData()
  }
}
