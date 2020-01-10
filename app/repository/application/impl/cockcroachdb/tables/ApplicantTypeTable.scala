package repository.application.impl.cockcroachdb.tables

import domain.application.ApplicantType
import slick.jdbc.PostgresProfile.api._
import slick.lifted.ProvenShape
import util.APPKeys
import util.connections.PgDBConnection
import util.connections.PgDBConnection.driver

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ApplicantTypeTable(tag: Tag) extends Table[ApplicantType](tag, _tableName = "applicant_type"){

  def id: Rep[String] = column[String]("id", O.PrimaryKey)

  def name: Rep[String] = column[String]("name")

  def description: Rep[String] = column[String]("description")

  override def * : ProvenShape[ApplicantType] = (id, name, description) <> ((ApplicantType.apply _).tupled, ApplicantType.unapply)
}

object ApplicantTypeTable extends TableQuery(new ApplicantTypeTable(_)) {
  def db: driver.api.Database = PgDBConnection.db

  def getEntity(id: String): Future[Option[ApplicantType]] = {
    db.run(this.filter(_.id === id).result).map(_.headOption)
  }

  def saveEntity(applicantType: ApplicantType): Future[Option[ApplicantType]] = {
    db.run(
      (this returning this).insertOrUpdate(applicantType)
    )
  }

  def getMatricApplicantType: Future[Option[ApplicantType]] = {
    db.run(this.filter(_.name.trim === APPKeys.MATRIC_APPLICANT_TYPE).result).map(_.headOption)
  }

  def getEntities: Future[Seq[ApplicantType]] = {
    db.run(ApplicantTypeTable.result)
  }

  def deleteEntity(id: String): Future[Int] = {
    db.run(this.filter(_.id === id).delete)
  }

  def createTable = {
    db.run(
      ApplicantTypeTable.schema.createIfNotExists
    ).isCompleted
  }

}
