/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import utiljvm._

object PomsApp
{
  def main(args: Array[String]): Unit =
  { deb("Starting PomsApp")
    val versionStr = "0.3.4"
    val oDir = args.headOption
    println(oDir)

    def makePom(dirStr: String, name: String, versionStr: String, depStrs: String*): ErrBi[Exception, FileWritten] =
      fileWrite(dirStr / name + "-" + versionStr + ".pom", new OpenStratPomProject(name, versionStr, depStrs.toArr).out())

    oDir.foreach { dirStr =>
      println(dirStr.length)
      println(makePom(dirStr, "rutil", versionStr))
      println(makePom(dirStr, "geom", versionStr, "rutil"))
      println(makePom(dirStr, "tiling", versionStr, "rutil", "geom"))
      println(makePom(dirStr, "egrid", versionStr, "rutil", "geom", "tiling"))
      println(makePom(dirStr, "apps", versionStr, "rutil", "geom", "tiling", "egrid"))
    }
  }
}