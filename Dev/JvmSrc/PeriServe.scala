/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import jakarta.servlet.http._, pWeb._

class PeriServe extends HttpServlet
{
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit =
  {
    val head = HtmlHead.title("Periculo")
    val p1 = HtmlP("This is the first paragraph, using pWeb classes.")
    val body = HtmlBody(HtmlH1("This is the start for Periculo"), p1)
    val page = HtmlPage(head, body)
    resp.getWriter().println(page.out)
  }
}