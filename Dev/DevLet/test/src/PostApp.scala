/* © 2026 Richard Oliver */
package ostrat; package pDev
import utiljvm.*, geom.*, pweb.*, webjvm.*, java.sql.{ DriverManager, Connection }

object PostApp
{
  def main(args: Array[String]): Unit =
  {
    deb("Welcome to PostApp!")
    val eStr: IOExcEither[String] = resourceStr("Postgres.rson")
    val eName = eStr.flatMap(_.findStrSetting("username"))
    val ePass = eStr.flatMap(_.findStrSetting("pWord"))
    debvar(eName)
    debvar(ePass)
  }
}