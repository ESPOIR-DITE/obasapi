package services.application.Impl.cockroachdb

import domain.application.ApplicantType
import repository.application.ApplicantTypeRepository
import services.application.ApplicantTypeService
import util.GenericLookupData

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ApplicantTypeServiceImpl extends ApplicantTypeService{

  override def saveEntity(entity: ApplicantType): Future[Option[ApplicantType]] =
    ApplicantTypeRepository.roach.saveEntity(entity)

  override def getEntities: Future[Seq[ApplicantType]] = {
    ApplicantTypeRepository.roach.getEntities
  }

  override def getEntity(applicantTypeId: String): Future[Option[ApplicantType]] = {
    ApplicantTypeRepository.roach.getEntity(applicantTypeId)
  }

  override def deleteEntity(entity: ApplicantType): Future[Boolean] = {
    ApplicantTypeRepository.roach.deleteEntity(entity)
  }

  override def createTable: Future[Boolean] = {
    ApplicantTypeRepository.roach.createTable
  }

  override def createInitialData: Future[Seq[Boolean]] = {
    Future.sequence(GenericLookupData.GENERIC_APPLICANT.map(applicantType => {
      val a = ApplicantType.build(applicantType)
      saveEntity(a).map(s => s.isDefined)
    }))
  }

  override def getMatricApplicantType: Future[Option[ApplicantType]] = ApplicantTypeRepository.roach.getMatricApplicantType
}

