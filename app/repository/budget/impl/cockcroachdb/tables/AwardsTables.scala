package repository.budget.impl.cockcroachdb.tables

import java.time.LocalDateTime

import domain.budget.Awards
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import util.connections.PgDBConnection
import util.connections.PgDBConnection.driver
import scala.concurrent.ExecutionContext.Implicits.global

import scala.concurrent.Future

class AwardsTables(tag: Tag) extends Table[Awards] (tag, _tableName = "awards"){

  def id: Rep[String] = column[String]("id", O.PrimaryKey)

  def email: Rep[String] = column[String]("email")

  def amount: Rep[BigDecimal] = column[BigDecimal]("amount")

  def date: Rep[LocalDateTime] = column[LocalDateTime]("date")

  def * : ProvenShape[Awards] = (id, email, amount, date) <> ((Awards.apply _).tupled, Awards.unapply)
}

object AwardsTables extends TableQuery(new AwardsTables(_)) {
  def db: driver.api.Database = PgDBConnection.db

  def getEntity(id: String): Future[Option[Awards]] = {
    db.run(this.filter(_.id === id).result).map(_.headOption)
  }

  def saveEntity(awards: Awards): Future[Option[Awards]] = {
    db.run(
      (this returning this).insertOrUpdate(awards)
    )
  }

  def getEntities: Future[Seq[Awards]] = {
    db.run(AwardsTables.result)
  }

  def deleteEntity(email: String): Future[Int] = {
    db.run(this.filter(_.email === email).delete)
  }

  def createTable = {
    db.run(
      AwardsTables.schema.createIfNotExists
    ).isCompleted
  }

}