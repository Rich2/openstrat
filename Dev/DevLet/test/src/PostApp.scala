/* © 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, geom.*, pweb.*, webjvm.*, gres.*, java.sql.Connection

object PostApp
{
  def main(args: Array[String]): Unit =
  { deb("Welcome to PostApp!")
    val eStr: IOExcEither[String] = loadResourceStr("Postgres.rson")
    val eName: Either[Exception, String] = eStr.flatMap(_.findStrSetting("username"))
    val ePass: Either[Exception, String] = eStr.flatMap(_.findStrSetting("pWord"))
    val eDB: Either[Exception, String] = eStr.flatMap(_.findStrSetting("dBase"))
    Either.forboth3(eDB, eName, ePass){errs =>
      debvar(errs)
    }{ (dbName, uName, pWord) =>      
      postgresConn(dbName, uName, pWord).forboth{err =>
        debvar(err)
      }{conn0 =>
        given conn: Connection = conn0 
        debvar(conn)
        val users = Gable("users")
        val newRes = users.insert(RegLogRow("Jane2", "passJane2"))
        newRes.forboth{err =>
          deb(err.toString)
          if(err.uniqueFail) println("Unique violation found!")
        }{g => deb("Success" -- g.str) }
        deb("About to close connection.")
        conn.close()
        deb("Connection closed.")
      }
    }
  }
}