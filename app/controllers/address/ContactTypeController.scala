package controllers.address


import controllers.ApiResponse
import domain.address.ContactType
import io.circe.generic.auto._
import javax.inject.Inject
import play.api.{Logger, Logging}
import play.api.libs.json.{JsValue, Json}
import play.api.mvc._
import services.address.ContactTypeService
import services.login.LoginService
import util.HelperUtil

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future



class ContactTypeController @Inject()
(cc: ControllerComponents, api: ApiResponse) extends AbstractController(cc) with Logging {
  type DomainObject = ContactType

  def className: String = "ContactTypeController"
  override val logger: Logger = Logger(className)

  def domainService: ContactTypeService = ContactTypeService.roach

  def loginService: LoginService = LoginService.apply

  def create: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val entity = Json.fromJson[DomainObject](request.body).asEither
      logger.info("Create request with body: " + entity)
      entity match {
        case Right(value) =>
          val copy = value.copy(contactTypeId = HelperUtil.codeGen(value.name))
          logger.info("Saving contact type: " + copy)
          val response: Future[Option[ContactType]] = for {
            results: Option[ContactType] <- domainService.saveEntity(copy)
          } yield results
          api.requestResponse[Option[ContactType]](response, className)
        case Left(error) => api.errorResponse(error, className)
      }
  }

  def update: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val entity = Json.fromJson[DomainObject](request.body).asEither
      logger.info("Update request with body: " + entity)
      entity match {
        case Right(value) =>
          val response: Future[Option[ContactType]] = for {
//            _ <- loginService.checkLoginStatus(request)
            results: Option[ContactType] <- domainService.saveEntity(value)
          } yield results
          api.requestResponse[Option[ContactType]](response, className)
        case Left(error) => api.errorResponse(error, className)
      }
  }

  def getContactTypeById(contactTypeId: String): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      logger.info("Retrieve by id: " + contactTypeId)
      val response: Future[Option[DomainObject]] = for {
        results <- domainService.getEntity(contactTypeId)
      } yield results
      api.requestResponse[Option[DomainObject]](response, className)
  }

  def getAllContactType: Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      logger.info("Retrieve all requested")
      val response: Future[Seq[DomainObject]] = for {
        results <- domainService.getEntities
      } yield results
      api.requestResponse[Seq[DomainObject]](response, className)
  }

  def deleteContactType: Action[JsValue] = Action.async(parse.json) {
    implicit request: Request[JsValue] =>
      val entity = Json.fromJson[DomainObject](request.body).asEither
      logger.info("Delete request with body: " + entity)
      entity match {
        case Right(value) =>
          val response: Future[Boolean] = for {
            results: Boolean <- domainService.deleteEntity(value)
          } yield results
          api.requestResponse[Boolean](response, className)
        case Left(error) => api.errorResponse(error, className)
      }
  }
}
