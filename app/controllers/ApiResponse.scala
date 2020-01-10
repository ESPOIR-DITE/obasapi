package controllers

import java.io.File
import java.nio.file.attribute.PosixFilePermission.{OWNER_READ, OWNER_WRITE}
import java.nio.file.attribute.PosixFilePermissions
import java.nio.file.{Files, Path}

import akka.stream.IOResult
import akka.stream.scaladsl.FileIO
import domain.log.LogEvent
import domain.util.events.Events
import domain.util.exeptions.TokenFailException
import io.circe.Encoder
import io.circe.syntax._
import javax.inject.Inject
import play.api.http.ContentTypes
import play.api.libs.json.{JsPath, JsonValidationError}
import play.api.libs.streams.Accumulator
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc.{AbstractController, ControllerComponents, Result}
import play.api.{Logger, Logging}
import play.core.parsers.Multipart.{FileInfo, FilePartHandler}
import services.log.LogEventService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ApiResponse @Inject()(cc: ControllerComponents) extends AbstractController(cc) with Logging {

  override val logger: Logger = Logger("ApiResponse")

  def errorResponse(error: Seq[(JsPath, Seq[JsonValidationError])], className: String): Future[Status] = {
    Future successful {
      val log = LogEvent(eventName = Events.RESPONSE, eventType = className, message = error.seq.toString())
      logger.info("Error for operation from " + className + ": " + log)
      println("Error for operation from " + className + ": " + log)
      LogEventService.apply.saveEntity(log)
      val message = error.seq.toString()
      if (message.contains("Future.filter predicate is not satisfied")) PreconditionFailed
      else InternalServerError
    }
  }

  def requestResponse[A: Encoder](response: Future[A], className: String): Future[Result] = {
    response.map(result => {
      val retdata = result.asJson.noSpaces
      logger.info("Response for " + className + ": " + retdata)
      println("Response for " + className + ": " + retdata)
      Ok(retdata)
        .as(ContentTypes.JSON)
    }).recover {
      case exp: TokenFailException =>
        val log = LogEvent(eventName = Events.TOKENFAILED, eventType = className, message = exp.getMessage)
        logger.info("Exception for " + className + ": " + log)
        println("Exception for " + className + ": " + log)
        LogEventService.apply.saveEntity(log)
        Unauthorized
      case exp: Exception =>
        val log = LogEvent(eventName = Events.RESPONSE, eventType = className, message = exp.getMessage)
        logger.info("Exception for " + className + ": " + log)
        println("Exception for " + className + ": " + log)
        LogEventService.apply.saveEntity(log)
        InternalServerError
    }
  }

  def handleFilePartAsFile: FilePartHandler[File] = {
    case FileInfo(partName, filename, contentType, dispositionType) =>
      val attr = PosixFilePermissions.asFileAttribute(java.util.EnumSet.of(OWNER_READ, OWNER_WRITE))
      val path: Path = Files.createTempFile("multipartBody", "tempFile", attr)
      Accumulator(FileIO.toPath(path)).map {
        case IOResult(count, status) =>
          logger.info(s"count = $count, status = $status")
          FilePart(partName, filename, contentType, path.toFile, count, dispositionType)
      }
  }

}