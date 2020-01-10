package repository.users.impl.cockroachdb

import domain.users.UserTown
import org.scalatest.FunSuite
import repository.users.UserTownRepository

import scala.concurrent.Await
import scala.concurrent.duration._

class UserTownRepositoryImplTest extends FunSuite {

  val entity = UserTown("DS23","CITY OF CAPE TOWN")
  val repository = UserTownRepository

  test("createEntity"){
    val result = Await.result(repository.apply.saveEntity(entity), 2 minutes)
    assert(result.nonEmpty)

  }

  test("readEntity"){
    val result = Await.result(repository.apply.getEntity(entity.userId), 2 minutes)
    assert(result.head.userId==entity.userId)
  }

  test("getEntities"){
    val result = Await.result(repository.apply.getEntities, 2 minutes)
    assert(result.nonEmpty)
  }

  test("updateEntity") {
    val result = Await.result(repository.apply.saveEntity(entity), 2 minutes)
    assert(result.isEmpty)

  }


  test("deleteEntities"){
    Await.result(repository.apply.deleteEntity(entity), 2 minutes)
    val result = Await.result(repository.apply.getEntity(entity.userId), 2 minutes)
    assert(result.isEmpty)
  }

}
