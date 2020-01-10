package services.storage

import java.io.File

import org.apache.poi.ss.usermodel.WorkbookFactory

import scala.concurrent.Future

trait ProcessSpreadSheetService {

  def processSchoolUpload(file: File): Future[Boolean]

}

object ProcessSpreadSheetService {
  implicit lazy val processSpreadSheet = new ProcessSpreadSheetService {
    override def processSchoolUpload(file: File): Future[Boolean] = {
      val wb = WorkbookFactory.create(file)
      val sheet = wb.getSheetAt(0)
      val rows = sheet.getPhysicalNumberOfRows
      sheet.getRow(0).getCell(0).toString()
      Future.successful(true)
    }
  }

}
