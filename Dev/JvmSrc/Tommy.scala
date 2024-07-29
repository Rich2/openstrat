/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import jakarta.servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp }, pWeb._

/** First openstrat Servlet fro Tomcat. */
class Tommy extends HttpServlet
{
  override def doGet(req: HSReq, resp: HSResp): Unit =
  {
    val ints = IntArr(2, 4, 6)
    val head = HtmlHead.title("Tommy")
    val body = HtmlBody(s"Hello from Servlet: ${ints.str}".xCon)
    val page = HtmlPage(head, body)
    resp.getWriter().println(page.out)
  }
}
