package repository.budget.impl.cockcroachdb

import domain.budget.{Disbursement, DisbursementType}
import repository.budget.{DisbursementRepository, DisbursementTypeRepository}
import repository.budget.impl.cockcroachdb.tables.DisbursementTypeTable

import scala.concurrent.Future

object DisbursementTypeRepositoryImpl extends DisbursementTypeRepository {
  override def saveEntity(entity: DisbursementType): Future[Option[DisbursementType]] =
  DisbursementTypeTable.saveEntity(entity)
  override def getEntities: Future[Seq[DisbursementType]] =
    DisbursementTypeTable.getEntities

  override def getEntity(id: String): Future[Option[DisbursementType]] =
  DisbursementTypeTable.saveEntity(id)
  override def deleteEntity(entity: DisbursementType): Future[Boolean] =
  DisbursementTypeTable.deleteEntity(entity.id) map (_.isValidInt)
  override def createTable: Future[Boolean] =
    Future.successful(DisbursementTypeTable.createTable)
}
