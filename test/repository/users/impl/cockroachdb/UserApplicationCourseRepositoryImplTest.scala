package repository.users.impl.cockroachdb

import domain.users.UserApplicationCourse
import org.scalatest.FunSuite
import repository.users.UserApplicationCourseRepository

import scala.concurrent.Await
import scala.concurrent.duration._

class UserApplicationCourseRepositoryImplTest extends FunSuite {

  val entity = UserApplicationCourse("1", "to be deleted", "13 asd Me Way")
  val entity2 = UserApplicationCourse("1", "154", "13 asd Me Way")
  val repository = UserApplicationCourseRepository

  test("createEntity") {
    println(entity)
    val result = Await.result(repository.apply.saveEntity(entity2), 2 minutes)
    assert(result.nonEmpty)
  }

  test("readEntity") {
    val result = Await.result(repository.apply.getEntity(entity.userId, entity.applicationId), 2 minutes)
    println(result.head)
    assert(result.head.userId == entity.userId)
  }

  test("readEntityForUser") {
    val result = Await.result(repository.apply.getEntitiesForUser(entity.userId), 2 minutes)
    println(result)
    assert(result.head.userId == entity.userId)
  }

  test("getEntities"){
    val result = Await.result(repository.apply.getEntities, 2 minutes)
    println(result)
    assert(result.nonEmpty)
  }

  test("updateEntity") {
    val updatedEntity = entity.copy(courseId = "updated")
    Await.result(repository.apply.saveEntity(updatedEntity), 2 minutes)
    val result = Await.result(repository.apply.getEntity(entity.userId, entity.applicationId), 2 minutes)
    println(result)
    assert(result.head.courseId == updatedEntity.courseId)
  }

  test("deleteEntity") {
    Await.result(repository.apply.deleteEntity(entity2), 2 minutes)
    val result = Await.result(repository.apply.getEntity(entity2.userId, entity2.applicationId), 2 minutes)
    println(result)
    assert(result.isEmpty)
  }
}