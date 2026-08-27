/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package plet
import pweb.*, jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}

extension(resp: HSReq)
{ /** Gets parameter of the given name converting nulls to [[None]]. */
  def optParam(name: String): Option[String] = Option(resp.getParameter(name))

  /** Gets parameter of the given name converting nulls to [[Fail]]s. */
  def eParam(name: String): ErrBi[Exception, String] = {
    val res: String = resp.getParameter(name)
    if (res == null) FailExc("No value") else Succ(res)
  }
}  