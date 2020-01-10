package repository.budget.impl.cockcroachdb

import domain.budget.Awards
import repository.budget.AwardsRepository
import repository.budget.impl.cockcroachdb.tables.AwardsTables

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object AwardsRepositoryImpl extends AwardsRepository{
  override def saveEntity(entity: Awards): Future[Option[Awards]] =
    AwardsTables.saveEntity(entity)

  override def getEntities: Future[Seq[Awards]] =
    AwardsTables.getEntities

  override def getEntity(id: String): Future[Option[Awards]] =
    AwardsTables.getEntity(id)

  override def deleteEntity(entity: Awards): Future[Boolean] = {
    AwardsTables.deleteEntity(entity.email) map (_.isValidInt)
  }

  override def createTable: Future[Boolean] =
    Future.successful(AwardsTables.createTable)
}
