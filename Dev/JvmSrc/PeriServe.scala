/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import jakarta.servlet.http._, pWeb._

class PeriServe extends HttpServlet
{
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit =
  {
    val path = req.getServletPath()
    if (path == "/") {
      val head = HtmlHead.title("Periculo")
      val p1 = HtmlP("This is the first paragraph, using pWeb classes.")
      val body = HtmlBody(HtmlCanvas.id("scanv"), HtmlScript.jsSrc("../peri2.js"), HtmlScript.main("Peri2JsApp"))
      val page = HtmlPage(head, body)
      resp.getWriter().println(page.out)
    }
    else resp.getWriter().println("Your path was " + path)
  }
}