/* Copyright 2018-23 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import jakarta.servlet.http._

class PeriServe extends HttpServlet
{
  override def doGet(req: HttpServletRequest, resp: HttpServletResponse): Unit =
  {
    resp.getWriter().println("<h1>This is the start for Periculo</h1>")
  }
}