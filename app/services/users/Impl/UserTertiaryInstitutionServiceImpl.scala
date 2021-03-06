package services.users.Impl

import domain.users.UserTertiaryInstitution
import repository.users.UserTertiaryInstitutionRepository
import services.users.{UserTertiaryCourseService, UserTertiaryInstitutionService}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class UserTertiaryInstitutionServiceImpl extends UserTertiaryInstitutionService {
  override def getEntity(userId: String, applicationId: String): Future[Option[UserTertiaryInstitution]] =
    UserTertiaryInstitutionRepository.apply.getEntity(userId, applicationId)

  override def getEntitiesForUser(userId: String): Future[Seq[UserTertiaryInstitution]] =
    UserTertiaryInstitutionRepository.apply.getEntitiesForUser(userId)

  override def saveEntity(entity: UserTertiaryInstitution): Future[Option[UserTertiaryInstitution]] =
    UserTertiaryInstitutionRepository.apply.saveEntity(entity)

  override def getEntities: Future[Seq[UserTertiaryInstitution]] =
    UserTertiaryInstitutionRepository.apply.getEntities

  override def getEntity(id: String): Future[Option[UserTertiaryInstitution]] = ???

  override def deleteEntity(entity: UserTertiaryInstitution): Future[Boolean] =
    UserTertiaryInstitutionRepository.apply.deleteEntity(entity)

  override def createTable: Future[Boolean] = UserTertiaryInstitutionRepository.apply.createTable

  override def updateEntity(entity: UserTertiaryInstitution): Future[Option[UserTertiaryInstitution]] =
    for {
      userTertiaryCourse <- UserTertiaryCourseService.apply.getEntity(entity.userId, entity.applicationId)
      updated <- if (userTertiaryCourse.isEmpty) saveEntity(entity)
      else Future(Some(entity))
    } yield updated
}
