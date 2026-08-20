/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pweb.*, jakarta.*, servlet.annotation.WebServlet,java.sql.{DriverManager, Connection}, java.time.LocalDateTime,
  servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}, plet.*

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
  val catb = System.getProperty("catalina.base")
  lazy val eSetts = loadTextFile(catb / "Notes" / "ostrat.rson")
  lazy val eName: ErrBi[Throwable, String] = eSetts.flatMap(_.findStrSetting("pgUser"))
  lazy val ePass: ErrBi[Throwable, String] ={
    val res = eSetts.flatMap(_.findStrSetting("pgPassword"))
    val currentDateTime: LocalDateTime = LocalDateTime.now()
    utiljvm.writeFile(catb / "Notes/tom.txt", currentDateTime.toString -- eName.toString -- res.toString)
    res
  }

  val connStr = "jdbc:postgresql://localhost:5432/"

  var oConn: ErrBi[Throwable, Connection] = FailExc("Untried.")

  def tryConn: ErrBi[Throwable, Connection] = oConn match{
    case Succ(_) => oConn
    case fail => {
      val res = ErrBi.map2(eName, ePass){ (uName, pWord) => DriverManager.getConnection(connStr, uName, pWord) }
      oConn = res
      res
    }
  }

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
  { given reqEv: HSReq = req

    
    val contents: RArr[XCon] = req.optParam("logSubmit") match
    {  case Some(_) => RArr(
        DivHtml("Result from Login"),
        DivHtml(tryConn.toString),
        DivHtml("name =" -- req.optParam(logForm.usernameNameStr).toString),
        DivHtml("password =" -- req.optParam(logForm.passwordInput.nameAttStr).toString)
      )  
      case _ => req.optParam("regSubmit") match
      { case Some(_) => RArr(
          DivHtml("Result from registration"),
          DivHtml(tryConn.toString),
          DivHtml("name =" -- regForm.uNameGet),
          DivHtml("password =" -- regForm.passwordGet)
        )

        case _ => RArr("Unrecogonised submission.")
      }    
    }
    val page = HtmlPage(headLog, BodyHtml(contents))
    resp.getWriter().println(page.out)
  }
}