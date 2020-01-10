package repository.application.impl.cockcroachdb

import domain.application.ApplicationType
import org.scalatest.FunSuite
import repository.application.ApplicationTypeRepository

import scala.concurrent.Await
import scala.concurrent.duration._

class ApplicationTypeRepositoryImplTest extends FunSuite {

  val entity = ApplicationType("DS23","University Student",Some("University Student"))
  val repository = ApplicationTypeRepository

  test("createEntity"){
    val result = Await.result(repository.apply.saveEntity(entity), 2 minutes)
    assert(result.nonEmpty)

  }

  test("readEntity"){
    val result = Await.result(repository.apply.getEntity(entity.id), 2 minutes)
    assert(result.head.id==entity.id)
  }

  test("getEntities"){
    val result = Await.result(repository.apply.getEntities, 2 minutes)
    print(result)
    assert(result.nonEmpty)
  }

  test("updateEntity") {
    val result = Await.result(repository.apply.saveEntity(entity), 2 minutes)
    assert(result.isEmpty)

  }


  test("deleteEntities"){
    Await.result(repository.apply.deleteEntity(entity), 2 minutes)
    val result = Await.result(repository.apply.getEntity(entity.id), 2 minutes)
    assert(result.isEmpty)
  }

}