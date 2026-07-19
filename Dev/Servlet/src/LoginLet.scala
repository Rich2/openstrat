/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}, plet.*

case class UserDetails(name: String, password: String)

/** First openstrat Servlet for Tomcat and Jetty. */
@WebServlet(urlPatterns = Array("/")) class LoginLet extends HttpServlet
{
  val users: RBuff[UserDetails] = RBuff()
  var numSesh: Int = 0
  val headLog = HeadHtml.title("Login")
  val logName = "regName"
  val logPass = "regPass"
  val regName = "regName"
  val regPass = "regPass"

  override def doGet(req: HSReq, resp: HSResp): Unit =
  { val currCookies: Array[Cookie] = req.getCookies
    val cookies2 = currCookies.mapArr(c => c.getName + "=" + c.getValue)

    val logForm: FormHtml = FormHtml(DivHtml("Login".bHtml),
      LabelInputStrPost.required("User Name", logName, logName, ""),
      LabelInputPassword.required("Password", logPass, logPass, ""), SubmitButton("logSubmit")
    )
    
    val regForm: FormHtml = FormHtml("Register".bHtml,
      LabelInputStrPost.required("User Name", regName, regName, ""),
      LabelInputPassword.required("Password", regPass, regPass, ""), SubmitButton("regSubmit")
    )
    
    val body: BodyHtml = BodyHtml(
      "Testbed for registration and login. At this stage do not use important passwords or give private details.",
      logForm,
      regForm,
    )

    if(cookies2.empty)
    { numSesh += 1
      resp.addCookie(Cookie("sesh", numSesh.toString))
    }

    val page: HtmlPage = HtmlPage(headLog, body)
    resp.getWriter().println(page.out)
  }

  override def doPost(req: HSReq, resp: HSResp): Unit =
  {
    val name: Option[String] = req.findParam(regName)
    val pass: Option[String] = req.findParam(regPass))

    val body = BodyHtml(
      DivHtml("Result from post"),
      DivHtml(s"name = $name"),
      DivHtml(s"password = $pass")
    )
    val page = HtmlPage(headLog, body)
    resp.getWriter().println(page.out)
  }
}