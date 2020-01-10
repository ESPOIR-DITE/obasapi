package controllers.storage

import java.io.File

import akka.util.ByteString
import controllers.ApiResponse
import domain.storage.FileInformation
import javax.inject.Inject
import play.api.libs.streams.Accumulator
import play.api.mvc.MultipartFormData.FilePart
import play.api.mvc._
import play.api.{Logger, Logging}
import play.core.parsers.Multipart.FileInfo
import services.login.LoginService
import services.storage.ProcessSpreadSheetService

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future


class SpreadSheetUploadController @Inject()(cc: MessagesControllerComponents, api: ApiResponse)

  extends MessagesAbstractController(cc) with Logging {
  def className: String = "SpreadSheetUploadController"

  override val logger: Logger = Logger(className)

  def loginService: LoginService = LoginService.apply

  type FilePartHandler[A] = FileInfo => Accumulator[ByteString, FilePart[A]]

  def upload: Action[MultipartFormData[File]] =
    Action.async(parse.multipartFormData(api.handleFilePartAsFile)) {
      implicit request: MessagesRequest[MultipartFormData[File]] =>
        def uploadedFile: Option[FileInformation] = request.body.file("upload")
          .map {
            case FilePart(key, filename, contentType, file, fileSize, dispositionType) =>
              FileInformation(key, filename, contentType, file, fileSize, dispositionType)
          }
        val response: Future[Boolean] =
          for {
            authorize <- loginService.checkLoginStatus(request) if authorize
            checkSize <- loginService.checkFileSize(uploadedFile.get.fileSize) if checkSize
            results <- ProcessSpreadSheetService.processSpreadSheet
              .processSchoolUpload(uploadedFile.head.file)
          } yield results
        api.requestResponse[Boolean](response, className)
    }

}

