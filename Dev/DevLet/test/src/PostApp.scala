/* © 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, geom.*, pweb.*, webjvm.*, pParse.*, java.sql.Connection

/** For this test app Create "Postgres.rson" file in the Users folder. */
object PostApp
{
  def main(args: Array[String]): Unit =
  { deb("Welcome to PostApp!")
    val eStr: IOExcEither[String] = loadResourceStr("Postgres.rson")
    val eDB: IOParseExcEither[Identifier] = eStr.flatMap(_.findSetting[Identifier]("dBase"))
    val eName: IOParseExcEither[Identifier] = eStr.flatMap(_.findSetting[Identifier]("username"))
    val ePass: IOParseExcEither[String] = eStr.flatMap(_.findStrSetting("pWord"))
    
    val eSetts: IOParseExcEither[(Identifier, Identifier, String)] =
      eStr.flatMap(_.find3Settings[Identifier, Identifier, String]("dbase", "username", "pWord"))
    //Either.fold3Do(eDB, eName, ePass)*/  
    eSetts.foldDo{ errs =>
      debvar(errs)
    }{ (dbName, uName, pWord) =>      
      postgresConn(dbName.srcStr, uName.srcStr, pWord).foldDo{ err =>
        debvar(err)
      }{conn0 =>
        given conn: Connection = conn0 
        debvar(conn)
        val users = Gable("users")
        val newRes = users.insert(RegLogRow("Jane2", "passJane2"))
        newRes.foldDo{ err =>
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