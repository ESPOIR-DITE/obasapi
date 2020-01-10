package repository.application.impl.cockcroachdb

import java.time.LocalDateTime


import domain.application.ApplicationStatus
import org.scalatest.FunSuite
import repository.application.ApplicationStatusRepository

import scala.concurrent.Await
import scala.concurrent.duration._

class ApplicationStatusRepositoryImplTest extends FunSuite {


  val entity = ApplicationStatus("1", "test", "repo to be deleted",Some(""),LocalDateTime.now())
  val entity2 = ApplicationStatus("1", "test", "repo to be deleted",Some(""),LocalDateTime.now())
  val entity3 = ApplicationStatus("1", "test", "repo to be deleted",Some(""),LocalDateTime.now())
  val repository = ApplicationStatusRepository

  test("createEntity") {
    println(entity)
    val result = Await.result(repository.roach.saveEntity(entity3), 2 minutes)
    assert(result.nonEmpty)
  }

  test("readEntity") {
    val result = Await.result(repository.roach.getEntity(entity.applicationId), 2 minutes)
    println(result.head)
    assert(result.head.applicationId == entity.applicationId)
  }

  test("readEntiesForAppnStatus"){
    val result =Await.result(repository.roach.getEntitiesForAppnStatus(entity.applicationId,entity.statusId),2 minutes)
                assert(result.head.applicationId == entity.applicationId)
  }


  test("readLatestForAppnStatus"){
    val result =Await.result(repository.roach.getLatestForAppnStatus(entity.applicationId,entity.statusId), 2 minutes)
    assert(result.head.applicationId == entity.applicationId)
  }
  

  test("readEntityForApplication") {
    val result = Await.result(repository.roach.getEntitiesForApplication(entity.applicationId), 2 minutes)
    println(result)
    assert(result.head.applicationId == entity.applicationId)
  }

  test("getEntities"){
    val result = Await.result(repository.roach.getEntities, 2 minutes)
    println(result)
    assert(result.nonEmpty)
  }

  test("updateEntity") {
    val updatedEntity = entity.copy(modifiedBy = "14 Loop Street")
    Await.result(repository.roach.saveEntity(updatedEntity), 2 minutes)
    val result = Await.result(repository.roach.getEntity(entity.applicationId), 2 minutes)
    println(result)
    assert(result.head.modifiedBy == updatedEntity.modifiedBy)
  }

  test("deleteEntity") {
    Await.result(repository.roach.deleteEntity(entity3), 2 minutes)
    val result = Await.result(repository.roach.getEntity(entity3.applicationId), 2 minutes)
    println(result)
    assert(result.isEmpty)
  }
}
