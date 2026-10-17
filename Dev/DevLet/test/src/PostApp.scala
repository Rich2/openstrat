/* © 2026 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, geom.*, pweb.*, webjvm.*, pParse.*, java.sql.Connection

/** For this test app Create "Postgres.rson" file in the Users folder. */
object PostApp
{
  def main(args: Array[String]): Unit =
  { deb("Welcome to PostApp!")
    val users = Gable("users")
    
    val eStr: IOExcEither[String] = loadResourceStr("Postgres.rson")        
    
    val eConn = eStr.flatMap{ settsStr => PgDatabase.settConn(settsStr) }
      
     
    eConn.foldDo{ errs =>
      debvar(errs)
    }{ conn0 =>
      given conn: Connection = conn0 
      debvar(conn)      
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