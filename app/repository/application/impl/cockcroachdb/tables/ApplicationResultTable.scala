package repository.application.impl.cockcroachdb.tables

import java.time.LocalDateTime

import domain.application.ApplicationResult
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import util.connections.PgDBConnection
import util.connections.PgDBConnection.driver

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ApplicationResultTable(tag: Tag) extends Table[ApplicationResult] (tag, _tableName = "application_result"){

  def applicationResultId: Rep[String] = column[String]("application_result_id", O.PrimaryKey)

  def description: Rep[String] = column[String]("description")

  def date: Rep[LocalDateTime] = column[LocalDateTime]("date")

  def * : ProvenShape[ApplicationResult] = (applicationResultId, description, date) <> ((ApplicationResult.apply _).tupled, ApplicationResult.unapply)
}

object ApplicationResultTable extends TableQuery(new ApplicationResultTable(_)) {
  def db: driver.api.Database = PgDBConnection.db

  def getEntity(applicationResultId: String): Future[Option[ApplicationResult]] = {
    db.run(this.filter(_.applicationResultId === applicationResultId).result).map(_.headOption)
  }

  def saveEntity(applicationResult: ApplicationResult): Future[Option[ApplicationResult]] = {
    db.run(
      (this returning this).insertOrUpdate(applicationResult)
    )
  }

  def getEntities: Future[Seq[ApplicationResult]] = {
    db.run(ApplicationResultTable.result)
  }

  def deleteEntity(applicationResultId: String): Future[Int] = {
    db.run(this.filter(_.applicationResultId === applicationResultId).delete)
  }

  def createTable = {
    db.run(
      ApplicationResultTable.schema.createIfNotExists
    ).isCompleted
  }

}