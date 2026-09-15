/* © 2026 Richard Oliver */
package ostrat; package pDev
import utiljvm.*, geom.*, pweb.*, webjvm.*, java.sql.{ DriverManager, Connection }

object PostApp
{
  def main(args: Array[String]): Unit =
  {
    deb("Welcome to PostApp!")
    val eStr: IOExcEither[String] = resourceStr("Postgres.rson")
    val eName: Either[Exception, String] = eStr.flatMap(_.findStrSetting("username"))
    val ePass: Either[Exception, String] = eStr.flatMap(_.findStrSetting("pWord"))
    Either2Forboth(eName, ePass){errs =>
      debvar(errs)
    }{ (name, pWord) =>
      val connStr = "jdbc:postgresql://localhost:5432/"
      val conn: Connection = DriverManager.getConnection(connStr, name, pWord)
      try {
        debvar(conn)
      } catch {
        case e: Exception => deb(e.getMessage)
      } finally {
        deb("About to close connection.")
        conn.close()
        deb("Connection closed.")
      }
    }
  }
}