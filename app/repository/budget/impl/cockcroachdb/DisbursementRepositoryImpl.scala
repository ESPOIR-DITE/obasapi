package repository.budget.impl.cockcroachdb

import domain.budget.Disbursement
import repository.budget.DisbursementRepository
import repository.budget.impl.cockcroachdb.tables.DisbursementTables

import scala.concurrent.Future

object DisbursementRepositoryImpl extends DisbursementRepository{
  override def saveEntity(entity: Disbursement): Future[Option[Disbursement]] =
    DisbursementTables.saveEntity(entity)

  override def getEntities: Future[Seq[Disbursement]] =
    DisbursementTables.getEntyties

  override def getEntity(id: String): Future[Option[Disbursement]] =
    DisbursementTables.getEntity(id)

  override def deleteEntity(entity: Disbursement): Future[Boolean] = {
    DisbursementTables.deleteEntity(entity.id).map(_.isValidInt)
  }

  override def createTable: Future[Boolean] =
    Future.successful(DisbursementTables.createTable)
}
