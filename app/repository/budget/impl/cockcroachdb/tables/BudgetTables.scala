package repository.budget.impl.cockcroachdb.tables

import java.time.LocalDateTime

import domain.budget.Budget
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import util.connections.PgDBConnection
import util.connections.PgDBConnection.driver

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class
BudgetTables(tag: Tag) extends Table[Budget](tag, _tableName = "awards") {

  def id: Rep[String] = column[String]("id", O.PrimaryKey)

  def description: Rep[String] = column[String]("description")

  def amount: Rep[BigDecimal] = column[BigDecimal]("amount")

  def date: Rep[LocalDateTime] = column[LocalDateTime]("date")

  def * : ProvenShape[Budget] = (id, description,amount, date) <> ((Budget.apply _).tupled, Budget.unapply)
}

object BudgetTables extends TableQuery(new BudgetTables(_)) {
  def db: driver.api.Database = PgDBConnection.db

  def getEntity(id: String): Future[Option[Budget]] = {
    db.run(this.filter(_.id === id).result).map(_.headOption)
  }

  def saveEntity(awards: Budget): Future[Option[Budget]] = {
    db.run(
      (this returning this).insertOrUpdate(awards)
    )
  }

  def getEntities: Future[Seq[Budget]] = {
    db.run(BudgetTables.result)
  }

  def deleteEntity(id: String): Future[Int] = {
    db.run(this.filter(_.id === id).delete)
  }

  def createTable: Boolean = {
    db.run(
      BudgetTables.schema.createIfNotExists
    ).isCompleted
  }
}