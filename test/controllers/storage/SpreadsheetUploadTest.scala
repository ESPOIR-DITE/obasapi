package controllers.storage

import java.io.{File, FileInputStream}

import org.scalatest.FunSuite

class SpreadsheetUploadTest extends FunSuite {
  val file = new File("Sample_File.xlsx")
  println(file.getAbsolutePath)
  val logo = new FileInputStream(file)
  val baseUrl = "http://localhost:9000"
  val UPLOAD = "upload"
  val SITEID = "SITEID"

  test("Test File Upload ") {
    println(file.getAbsolutePath)

  }

}
