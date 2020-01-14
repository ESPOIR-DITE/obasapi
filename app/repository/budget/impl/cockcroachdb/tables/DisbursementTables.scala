package repository.budget.impl.cockcroachdb.tables

import java.time.LocalDateTime

import domain.budget.Disbursement
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import util.connections.PgDBConnection
import util.connections.PgDBConnection.driver

import scala.concurrent.Future

class DisbursementTables(tag: Tag) extends Table[Disbursement] (tag,_tableName = "disbursement"){
  def id: Rep[String] =column[String]("id",O.PrimaryKey)
  def awardId: Rep[String] =column[String] ("awardId")
  def amount: Rep[BigDecimal] =column[BigDecimal]("amount")
  def disbursementType: Rep[String] =column[String]("disbursementType")
  def date: Rep[LocalDateTime] =column[LocalDateTime]("date")

  def * :ProvenShape[Disbursement] =(id,awardId,amount,disbursementType,date) <> ((Disbursement.apply _).tupled,Disbursement.unapply)
}
object DisbursementTables extends TableQuery(new DisbursementTables(_)){
  def db: driver.api.Database = PgDBConnection.db

//  def getEntity(id: String): Future[Option[Disbursement]] = {
//    db.run(this.filter(_.id === id).result).map(_.headOption)
//  }

  def saveEntity(disbursement: Disbursement): Future[Option[Disbursement]] = {
    db.run(
    (this returning this).insertOrUpdate(disbursement)
    )
  }
  def getEntyties: Future[Seq[Disbursement]] = {
    db.run(DisbursementTables.result)
  }
  def deleteEntity(id:String): Future[Int] ={
    db.run(this.filter(_.id===id).delete)
  }
  def createTable :Boolean ={
    db.run(
      DisbursementTables.schema.createIfNotExists
    ).isCompleted
  }
}
