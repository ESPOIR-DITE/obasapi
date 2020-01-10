package domain.application

import play.api.libs.json.Json
import util.HelperUtil

case class ApplicantType(
                          id:String,
                          name: String,
                          description: String = ""
                         )
object ApplicantType{
  implicit val applicantTypeFmt = Json.format[ApplicantType]
  def build(name: String, description: String = "") =
    ApplicantType(HelperUtil.codeGen(name + description), name, description)
}
