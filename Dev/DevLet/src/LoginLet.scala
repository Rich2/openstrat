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
    
    val regForm: FormHtml = FormHtml(DivHtml("Register".bHtml),
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
  { val contents: RArr[XCon] = req.findParam("logSubmit") match
    {  case Some(_) => RArr(
        DivHtml("Result from Login"),
        DivHtml("name =" -- req.findParam (logName).toString),
        DivHtml("password =" -- req.findParam (logPass).toString)
      )  
      case _ => req.findParam("regSubmit") match
      { case Some(_) => RArr(
          DivHtml("Result from registration"),
          DivHtml("name =" -- req.findParam(regName).toString),
          DivHtml("password =" -- req.findParam (regPass).toString)
        )
        case _ => RArr("Unrecogonised submission.")
      }    
    }
    val page = HtmlPage(headLog, BodyHtml(contents))
    resp.getWriter().println(page.out)
  }
}