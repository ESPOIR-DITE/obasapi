package repository.budget.impl.cockcroachdb

import domain.budget.Disbursement
import repository.budget.DisbursementRepository

import scala.concurrent.Future

object DisbursementRepositoryImpl extends DisbursementRepository{
  override def saveEntity(entity: Disbursement): Future[Option[Disbursement]] = Future[Seq[Disbursement]]=
    DisbursementTable.

  override def getEntities: Future[Seq[Disbursement]] = ???

  override def getEntity(id: String): Future[Option[Disbursement]] = ???

  override def deleteEntity(entity: Disbursement): Future[Boolean] = ???

  override def createTable: Future[Boolean] = ???
}
