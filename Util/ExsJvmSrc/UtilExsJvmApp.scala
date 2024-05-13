/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utiljvm._

object UtilExsJvmApp
{
  def main(args: Array[String]): Unit =
  {
    deb("Starting ExsJvmApp")
    val versionStr = "0.3.3"
    val oDir = args.headOption
    println(oDir)

    def makePom(dirStr: String, name: String, versionStr: String, depStrs: String*): EMon[String] =
      fileWrite(dirStr, name + "-" + versionStr + ".pom", new OpenStratPomProject(name, versionStr, depStrs.toArr).out())

    oDir.foreach { dirStr =>
      println(dirStr.length)
      println(makePom(dirStr, "rutil", versionStr))
      println(makePom(dirStr, "geom", versionStr, "rutil"))
      println(makePom(dirStr, "tiling", versionStr, "rutil", "geom"))

    }
  }
}