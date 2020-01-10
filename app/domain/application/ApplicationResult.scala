package domain.application

import java.time.LocalDateTime

import play.api.libs.json.Json

case class ApplicationResult(
                              applicationResultId:String,
                              description:String,
                              date:LocalDateTime
                            )
object ApplicationResult{
  implicit val applicationResultFmt = Json.format[ApplicationResult]
}
