package services.users

import java.time.LocalDate

import domain.users.User
import org.scalatest.FunSuite

import scala.concurrent.Await
import scala.concurrent.duration._


class UserServiceTest extends FunSuite{
  val entity = User("ajwiese@gmail.com","Abraham","Jabobus","Wiese", LocalDate.now)
  val roachService = UserService
  test("createEntity"){
    val result = Await.result(roachService.apply.saveEntity(entity), 2 minutes)
    assert(result)

  }

  test("readEntity"){
    val result = Await.result(roachService.apply.getEntity(entity.email), 2 minutes)
    assert(result.head.email==entity.email)
  }

  test("getEntities") {
    val result = Await.result(roachService.apply.getEntities, 2 minutes)
    assert(result.nonEmpty)
  }

  test("updateEntities"){
    val updatedEntity=entity.copy(firstName = "JvR High")
    Await.result(roachService.apply.saveEntity(updatedEntity), 2 minutes)
    val result = Await.result(roachService.apply.getEntity(entity.email), 2 minutes)
    assert(result.head.firstName==updatedEntity.firstName)
  }


  test("deleteEntities"){
    Await.result(roachService.apply.deleteEntity(entity), 2 minutes)
    val result = Await.result(roachService.apply.getEntity(entity.email), 2 minutes)
    assert(result.isEmpty)

  }
}