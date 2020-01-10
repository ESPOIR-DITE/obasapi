package services.login.Impl

import java.io.File
//
//import akka.stream.actor.ActorPublisherMessage.Request
import domain.login.{Login, Register, ResetKey}
import domain.security.ResetToken
import javax.imageio.ImageIO
import org.scalatest.FunSuite
import services.login.LoginService

import scala.concurrent.Await
import scala.concurrent.duration._

class LoginServiceImplTest extends FunSuite {

  val loginService = LoginService

  test("testCheckLoginStatus") {
    val register = Register("cyfotyasi@gmail.com")
    val result = Await.result(loginService.apply.isUserRegistered(register), 2.minutes)
    println(result)
    assert(result == true)

  }

  test("testIsSecurityEnabled") {

  }

  test("testCheckFileSize") {
    val url = getClass().getResource("3.JPG")
    val file = new File(url.getPath())
  //  val someFile = new File("3.JPG")
    //val imageSize = url.getFile.length()
    val imageSize = file.length()
    println(imageSize)
    val size = imageSize
    val result = Await.result(loginService.apply.checkFileSize(size), 2.minutes)

    assert(result == true)

  }

  test("testForgotPassword") {
    val register = Register("test@test.com")
    val result = Await.result(loginService.apply.forgotPassword(register), 2.minutes)
    println(result)
    assert(result.nonEmpty)

  }

  test("testIsUserRegistered") {

  }

  test("testResetPasswordRequest") {
    val resetKey = "niwm9eehpmwrikl1ubvyfxkyonmwpih6"
    val reset = Await.result(loginService.apply.resetPasswordRequest(resetKey), 2.minutes)
    println(reset)
    assert(reset == true)


  }

  test("testLogOut") {

    val logout = Register("cyfotyasi@gmail.com")
    val isLoggedOut = Await.result(loginService.apply.logOut(logout), 2.minutes)
    println(isLoggedOut)
    assert(isLoggedOut == true)

  }

  test("testGetLoginToken") {
    val login = Login("test@gmail.com", "XambauL3")
    val token = Await.result(loginService.apply.getLoginToken(login), 2.minutes)
    println(token)
    assert(token.isDefined)
  }

  test("testRegister") {
    val register: Register = Register("nombilasoso@gmail.com")
    val isRegistered = Await.result(loginService.apply.register(register), 2.minutes)
    assert(isRegistered == true)
  }

}
