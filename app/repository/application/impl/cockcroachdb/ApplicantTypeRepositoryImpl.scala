package repository.application.impl.cockcroachdb

import domain.application.ApplicantType
import repository.application.ApplicantTypeRepository
import repository.application.impl.cockcroachdb.tables.ApplicantTypeTable

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class ApplicantTypeRepositoryImpl extends ApplicantTypeRepository{

  override def saveEntity(entity: ApplicantType): Future[Option[ApplicantType]] = {
    ApplicantTypeTable.saveEntity(entity)
  }

  override def getEntities: Future[Seq[ApplicantType]] = {
    ApplicantTypeTable.getEntities
  }

  override def getEntity(applicantTypeId: String): Future[Option[ApplicantType]] = {
    ApplicantTypeTable.getEntity(applicantTypeId)
  }

  override def deleteEntity(entity: ApplicantType): Future[Boolean] = {
    ApplicantTypeTable.deleteEntity(entity.id)map(value=> value.isValidInt)
  }

  override def createTable: Future[Boolean] = {
    Future.successful(ApplicantTypeTable.createTable)
  }

  override def getMatricApplicantType: Future[Option[ApplicantType]] = ApplicantTypeTable.getMatricApplicantType
}
