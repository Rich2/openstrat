/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*

/** Application for producing Openstrat POM files. Takes the target folder as a parameter. */
object PomsApp
{
  def main(args: Array[String]): Unit =
  { val versionStr = "0.3.10"
    val scalaVersion ="3.8.2"
    val oDir: Option[String] = args.headOption
    debvar(oDir)
    oDir.foreach{dirStr => OsPomsWriter().meth1(DirsAbs(dirStr)) }
  }
}

case class OsPomsWriter(versionStr: String = "0.3.10", scalaVersion: String = "3.8.2")
{
  def stagePom(dirPath: DirsAbs, name: String, depStrs: String*): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / name + "-" + versionStr, OpenStratPomProject(name, versionStr, scalaVersion, depStrs.toArr).out)

  def stagePom2(dirPath: DirsAbs, name: String, pom: OpenStratPomProject): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / name + "-" + versionStr, pom.out)

  def meth1(dirPath: DirsAbs): Unit =
  { val gFxDeps = RArr(OpenStratPomDep("rutil", versionStr), OpenStratPomDep("geom", versionStr), JavaFxControlsDependency("25.0.2"))
    val gFxPom = OpenStratPomProject("geomfx", versionStr, scalaVersion, gFxDeps)
    val res: ErrBiAcc[Exception, PomFileWritten] = ErrBiAcc(
      stagePom(dirPath, "rutil"),
      stagePom(dirPath, "geom", "rutil"),
      stagePom(dirPath, "tiling", "rutil", "geom"),
      stagePom(dirPath, "egrid", "rutil", "geom", "tiling"),
      stagePom2(dirPath, "geomfx", gFxPom)
    )
    deb(res.msgErrsSummary(s"to $dirPath"))
  }
}