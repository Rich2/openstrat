/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package plet
import pweb.*, jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}

extension(form: RegisterForm)
{
  def post(req: HSReq): RegisterLet = RegisterLet(form, req)
}  

class RegisterLet(form: RegisterForm, req: HSReq)
{ def uName: String = req.optParam(form.usernameNameStr).toString
  def password: String = req.optParam(form.passwordNameStr).toString  
}