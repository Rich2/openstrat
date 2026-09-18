/* © 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, geom.*, pweb.*, webjvm.*, gres.*, java.sql.{ DriverManager, Connection }

object PostApp
{
  def main(args: Array[String]): Unit =
  { deb("Welcome to PostApp!")
    val eStr: IOExcEither[String] = resourceStr("Postgres.rson")
    val eName: Either[Exception, String] = eStr.flatMap(_.findStrSetting("username"))
    val ePass: Either[Exception, String] = eStr.flatMap(_.findStrSetting("pWord"))
    Either2Forboth(eName, ePass){errs =>
      debvar(errs)
    }{ (name, pWord) =>      
      postgresConn(name, pWord).foreach{conn0 =>
        given conn: Connection = conn0 
        debvar(conn)
        val users = Gable("users")                
        val result = users.insert(RegLogRow("Jane2", "passJane2"))
        debvar(result)
        deb("About to close connection.")
        conn.close()
        deb("Connection closed.")
      }
    }
  }
}