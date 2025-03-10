/* Copyright 2018-25 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import ostrat.pWeb.JavaFxControlsDependency
import utiljvm.*

/** Application for producing Openstrat POM files. Takes the target folder as a paremter. */
object PomsApp
{
  def main(args: Array[String]): Unit =
  { val versionStr = "0.3.6"
    val scalaVersion ="3.6.4"
    val oDir = args.headOption
    debvar(oDir)

    def stagePom(dirStr: String, name: String, depStrs: String*): ErrBi[Exception, PomFileWritten] =
      pomFileWrite(dirStr / name + "-" + versionStr, OpenStratPomProject(name, versionStr, scalaVersion, depStrs.toArr).out())

    def stagePom2(dirStr: String, name: String, pom: OpenStratPomProject): ErrBi[Exception, PomFileWritten] =
      pomFileWrite(dirStr / name + "-" + versionStr, pom.out())

    oDir.foreach { dirStr =>
      val gFxDeps = RArr(OpenStratPomDep("rutil", versionStr), OpenStratPomDep("geom", versionStr), JavaFxControlsDependency("15.0.1"))
      val gFxPom = OpenStratPomProject("geomfx", versionStr, scalaVersion, gFxDeps)
      val res: ErrBiAcc[Exception, PomFileWritten] = ErrBiAcc(
        stagePom(dirStr, "rutil"),
        stagePom(dirStr, "geom", "rutil"),
        stagePom(dirStr, "tiling", "rutil", "geom"),
        stagePom(dirStr, "egrid", "rutil", "geom", "tiling"),
        stagePom2(dirStr, "geomfx", gFxPom)
      )
     deb(res.msgErrsSummary(s"to $dirStr"))
    }
  }
}