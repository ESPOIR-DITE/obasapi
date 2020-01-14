package repository.budget.impl.cockcroachdb.tables

import domain.budget.DisbursementType
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import util.connections.PgDBConnection
import util.connections.PgDBConnection.driver
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class DisbursementTypeTable(tag: Tag) extends Table[DisbursementTypeTable] (tag, _tableName = "disbursementType"){
  def id : Rep[String] =column[String]("id",O.PrimaryKey)
  def disbursementType:Rep[String] =column[String]("disbursementType")
  def described: Rep[String] =column[String]("described")

  def * :ProvenShape[DisbursementType] = (id, disbursementType,described) <> ((DisbursementType.apply _).tupled, DisbursementType.unapply)
}
object DisbursementType extends TableQuery(new DisbursementTypeTable(_)){
  def db: driver.api.Database =PgDBConnection.db

  def getEntity(id:String): Future[Option[DisbursementType]] ={
    db.run(this.filter(_.id === id).result).map(_.headOption)
  }
  def saveEntity(disbursementType: DisbursementType): Future[Option[DisbursementType]] = {
    db.run(
      (this returning this).insertOrUpdate(DisbursementType)
    )
  }
  def getEntities: Future[Seq[DisbursementType]] ={
    db.run(DisbursementTypeTable.result)
  }
  def deleteEntity(id: String): Future[Int] ={
    db.run(this.filter(_.id===id).delete)
  }
  def createTable :Boolean ={
    db.run(
      DisbursementTable.schema.createIfNotExists
    ).isCompleted
  }
}
