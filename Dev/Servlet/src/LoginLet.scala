/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}

case class UserDetails(name: String, password: String)

/** First openstrat Servlet for Tomcat and Jetty. */
@WebServlet(urlPatterns = Array("/")) class LoginLet extends HttpServlet
{
  val users: RBuff[UserDetails] = RBuff()
  var numSesh: Int = 0
  val headLog = HeadHtml.title("Login")

  override def doGet(req: HSReq, resp: HSResp): Unit =
  { val currCookies: Array[Cookie] = req.getCookies
    val cookies2 = currCookies.mapArr(c => c.getName + "=" + c.getValue)
    val regForm = FormHtml(
      LabelInputStrPost("User Name", "regName",  "regName", ""),
      LabelInputPassword("Password", "regPass", "regPass", ""), SubmitButton("regSubmit")
    )
    
    val body: BodyHtml = BodyHtml(
      "Testbed for registration and login. At this stage do not use important passwords or give private details.",
      regForm
    )

    if(cookies2.empty)
    { numSesh += 1
      resp.addCookie(Cookie("sesh", numSesh.toString))
    }

    val page: HtmlPage = HtmlPage(headLog, body)
    resp.getWriter().println(page.out)
  }

  override def doPost(req: HSReq, resp: HSResp): Unit =
  { val names1 = req.getParameterNames//("regName")
    val nBuff = StringBuff()
    while(names1.hasMoreElements){
      nBuff.grow(names1.nextElement)
    }

    val nameLen = nBuff.length
    val names2 = nBuff.map{str => DivHtml(str)}

    val body = BodyHtml(
      DivHtml("Result from post") %: DivHtml(s"num parameters = $nameLen") %: names2
      //DivHtml(s"parameter names = $name2")
    )
    val page = HtmlPage(headLog, body)
    resp.getWriter().println(page.out)
  }
}