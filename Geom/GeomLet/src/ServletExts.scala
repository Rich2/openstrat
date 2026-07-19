/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package plet
import pweb.*, jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}

extension(resp: HSReq)
{
  def findParam(name: String): Option[String] = Option(resp.getParameter(name))
}  