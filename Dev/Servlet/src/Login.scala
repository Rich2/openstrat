/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}

case class UserDetails(name: String, password: String)

/** First openstrat Servlet for Tomcat and Jetty. */
@WebServlet(urlPatterns = Array("/")) class Login extends HttpServlet
{
  val users: RBuff[UserDetails] = RBuff()

  override def doGet(req: HSReq, resp: HSResp): Unit =
  { val head: HeadHtml = HeadHtml.title("Login")
    val body: BodyHtml = BodyHtml(
      "Testbed for registration and login. At this stage do not use important passwords or give private details.",
      regForm
    )
    
    def regForm = FormHtml(LabelInputStr("regName", "User Name", ""))
  }
}