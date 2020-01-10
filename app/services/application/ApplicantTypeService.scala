package services.application

import domain.application.ApplicantType
import services.CrudService
import services.application.Impl.cockroachdb.ApplicantTypeServiceImpl

import scala.concurrent.Future

trait ApplicantTypeService extends CrudService[ApplicantType] {
  def createInitialData: Future[Seq[Boolean]]
  def getMatricApplicantType: Future[Option[ApplicantType]]
}

object ApplicantTypeService {
  def roach: ApplicantTypeService = new ApplicantTypeServiceImpl()
}
