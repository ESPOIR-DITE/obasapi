package services.application

import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._
import java.time.LocalDateTime

import domain.application.ApplicationStatus

class ApplicationStatusServicesTest extends FunSuite {

  val entity = ApplicationStatus("1", "Pending ","Admin",Some("We Regret to tell you"), LocalDateTime.of(2000, 5, 6, 22, 15, 5))
  val service = ApplicationStatusService
  test("createEntity"){
    val result = Await.result(service.roach.saveEntity(entity), 2 minutes)
    assert(result.nonEmpty)

  }

  test("readEntity"){
    val result = Await.result(service.roach.getEntity(entity.applicationId), 2 minutes)
    assert(result.head.applicationId==entity.applicationId)
  }


  test("getEntities") {
    val result = Await.result(service.roach.getEntities, 2 minutes)
    assert(result.nonEmpty)
  }

  test("updateEntity") {
    val result = Await.result(service.roach.saveEntity(entity), 2 minutes)
    assert(result.isEmpty)

  }


  test("deleteEntities"){
    Await.result(service.roach.deleteEntity(entity), 2 minutes)
    val result = Await.result(service.roach.getEntity(entity.applicationId), 2 minutes)
    assert(result.isEmpty)

  }


}
