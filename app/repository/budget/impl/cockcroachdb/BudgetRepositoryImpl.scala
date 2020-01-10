package repository.budget.impl.cockcroachdb

import domain.budget.Budget
import repository.budget.BudgetRepository

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

import repository.budget.impl.cockcroachdb.tables.BudgetTables
object BudgetRepositoryImpl extends BudgetRepository{
  override def saveEntity(entity: Budget): Future[Option[Budget]] =
    BudgetTables.saveEntity(entity)

  override def getEntities: Future[Seq[Budget]] =
    BudgetTables.getEntities

  override def getEntity(id: String): Future[Option[Budget]] =
    BudgetTables.getEntity(id)

  override def deleteEntity(entity: Budget): Future[Boolean] = {
    BudgetTables.deleteEntity(entity.id).map(_.isValidInt)
  }

  override def createTable: Future[Boolean] =
    Future.successful(BudgetTables.createTable)
}
