/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package plet
import pweb.*, jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}


extension (form: RegLogForm)
{ def uNameGet(using req: HSReq): String = req.optParam(form.uNameInput.nameAttStr).toString
  def passwordGet(using req: HSReq): String = req.optParam(form.passwordInput.nameAttStr).toString  
}