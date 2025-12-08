/* Copyright 2018-25-- Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import jakarta.servlet.http.{ Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp }, pWeb.*

/** First openstrat Servlet for Tomcat and Jetty. */
class Cookies1 extends HttpServlet
{ var users: Int = 0

  override def doGet(req: HSReq, resp: HSResp): Unit =
  { val ints = IntArr(2, 4, 6)
    val head = HtmlHead.title("First cookies")
    val currCookies: Array[Cookie] = req.getCookies
    val cookies2 = currCookies.mapArr(c => c.getName + "=" + c.getValue)
    val body = HtmlBody(s"Hello from Servlet: ${ints.str}", HtmlP(cookies2.toString))
    val page = HtmlPage(head, body)
    if (cookies2.empty)
    { users += 1
      resp.addCookie(Cookie("user", users.toString))
    }
    resp.getWriter().println(page.out)
  }
}