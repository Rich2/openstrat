/* Copyright 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package plet
import pweb.*, jakarta.*, servlet.annotation.WebServlet, servlet.http.{Cookie, HttpServlet, HttpServletRequest as HSReq, HttpServletResponse as HSResp}
import java.sql.*

/** Returns a connection to  a PostgreSQL database. */
def postgresConnection(username: String, password: String) = DriverManager.getConnection("jdbc:postgresql://localhost:5432/", username, password)

extension(resp: HSReq)
{ /** Gets parameter of the given name converting nulls to [[None]]. */
  def optParam(name: String): Option[String] = Option(resp.getParameter(name))

  /** Gets parameter of the given name converting nulls to [[Left]]s. */
  def eParam(name: String): Either[Exception, String] =
  { val res: String = resp.getParameter(name)
    if (res == null) LeftExc("No value") else Right(res)
  }
}  