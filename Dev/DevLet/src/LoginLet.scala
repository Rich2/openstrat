/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}, plet.*

/** First openstrat Servlet for Tomcat and Jetty. */
@WebServlet(urlPatterns = Array("/")) class LoginLet extends HttpServlet
{
  val users: RBuff[UserDetails] = RBuff()
  var numSesh: Int = 0
  val headLog = HeadHtml.title("Login")
  val logName = "regName"
  val logPass = "regPass"
  val regForm: RegisterForm = RegisterForm()
  val logForm: LoginForm = LoginForm()

  override def doGet(req: HSReq, resp: HSResp): Unit =
  { val currCookies: Array[Cookie] = req.getCookies
    val cookies2 = currCookies.mapArr(c => c.getName + "=" + c.getValue)
    
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
        DivHtml("name =" -- req.findParam (logForm.usernameNameAtt).toString),
        DivHtml("password =" -- req.findParam (logForm.passwordNameAtt).toString)
      )  
      case _ => req.findParam("regSubmit") match
      { case Some(_) =>
        { val reg = regForm.post(req)
          RArr(
            DivHtml("Result from registration"),
            DivHtml("name =" -- reg.uName),
            DivHtml("password =" -- reg.password)
          )
        }
        case _ => RArr("Unrecogonised submission.")
      }    
    }
    val page = HtmlPage(headLog, BodyHtml(contents))
    resp.getWriter().println(page.out)
  }
}