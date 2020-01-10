package services.util.generic.impl.cockroach

import java.time.LocalDateTime

import domain.util.generic.GenericStatus
import repository.util.generic.GenericStatusRepository
import services.util.generic.GenericStatusService
import util.GenericLookupData

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class GenericStatusServiceImpl extends GenericStatusService{

  override def saveEntity(entity: GenericStatus): Future[Option[GenericStatus]] = {
    GenericStatusRepository.roach.saveEntity(entity)
  }

  override def getEntities: Future[Seq[GenericStatus]] = {
    GenericStatusRepository.roach.getEntities
  }

  override def getEntity(id: String): Future[Option[GenericStatus]] = {
    GenericStatusRepository.roach.getEntity(id)
  }

  override def deleteEntity(entity: GenericStatus): Future[Boolean] = {
    GenericStatusRepository.roach.deleteEntity(entity)
  }

  override def createTable: Future[Boolean] = {
    GenericStatusRepository.roach.createTable
  }

  override def createInitialData: Future[Seq[Boolean]] = {
    Future.sequence(GenericLookupData.GENERICSTATUS.map(status => {
      val gs = GenericStatus.build(status)
      saveEntity(gs).map(s => s.isDefined)
    }))
  }

  override def getIncompleteStatus: Future[Option[GenericStatus]] =
    GenericStatusRepository.roach.getIncompleteStatus
}

