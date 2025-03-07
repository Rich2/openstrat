/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm._

/** Application for producing Openstrat POM files. Takes the target folder as a paremter. */
object PomsApp
{
  def main(args: Array[String]): Unit =
  { deb("Starting PomsApp")
    val versionStr = "0.3.6snap"
    val oDir = args.headOption
    debvar(oDir)

    def makePom(dirStr: String, name: String, versionStr: String, depStrs: String*): ErrBi[Exception, FileWritten] =
      fileWrite(dirStr / name + "-" + versionStr + ".pom", new OpenStratPomProject(name, versionStr, depStrs.toArr).out())

    oDir.foreach { dirStr =>
      val res = ErrBiAcc(makePom(dirStr, "rutil", versionStr), makePom(dirStr, "geom", versionStr, "rutil"),
        makePom(dirStr, "tiling", versionStr, "rutil", "geom"), makePom(dirStr, "egrid", versionStr, "rutil", "geom", "tiling"),
        makePom(dirStr, "apps", versionStr, "rutil", "geom", "tiling", "egrid"))
     println(res.errsSummary)
    }
  }
}