/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import jakarta.servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}

class Tommy extends HttpServlet
{
  override def doGet(req: HSReq, resp: HSResp): Unit =
  {
    val ints = IntArr(2, 4, 6)
    resp.getWriter().println(s"Hello from Servlet: ${ints.str}")
  }
}
