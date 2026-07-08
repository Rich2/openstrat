/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import geom.*, pweb.*, jakarta.*, servlet.annotation.WebServlet, 
  servlet.http.{ Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp }

/** First openstrat Servlet for Tomcat and Jetty. */
@WebServlet(urlPatterns = Array("/")) class GeomUser extends HttpServlet
{ var users: Int = 0

  override def doGet(req: HSReq, resp: HSResp): Unit =
  { val ints = IntArr(2, 4, 6, 8)
    val head: HeadHtml = HeadHtml.title("First cookies")
    val currCookies: Array[Cookie] = req.getCookies
    val cookies2 = currCookies.mapArr(c => c.getName + "=" + c.getValue)
    val body: BodyHtml = BodyHtml(
      DivHtml(s"This is the GeomUser Servlet. Its purpose is just to test out use of the Util and Geom Openstrat libraries."),
      DivHtml(s"Here is an Int Array from the Util modules: ${ints.str}"),
      PHtml(cookies2.mkStr(" ")),
      SvgSvgRel.auto(20, RArr(Circle(100).fill(Colour.Red)))
    )
    val page: HtmlPage = HtmlPage(head, body)
    if (cookies2.empty)
    { users += 1
      resp.addCookie(Cookie("user", users.toString))
    }
    resp.getWriter().println(page.out)
  }
}