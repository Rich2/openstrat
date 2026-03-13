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
    oDir.foreach{dirStr => OsPomsWriter().aggregate(DirsAbs(dirStr)) }
  }
}

case class OsPomsWriter(versionStr: String = "0.3.10", scalaVersion: String = "3.8.2")
{
  def stageBuildPom(dirPath: DirsAbs, name: String, depStrs: String*): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / name + "-" + versionStr, OpenStratPomProject(name, versionStr, scalaVersion, depStrs.toArr).out)

  def osPom(name: String, depStrs: String*) = OpenStratPomProject(name, versionStr, scalaVersion, depStrs.toArr)

  def rutil: OpenStratPomProject = osPom("rutil")
  def geom: OpenStratPomProject = osPom("geom", "rutil")
  def tiling: OpenStratPomProject = osPom("tiling", "rutil", "geom")
  def egrid: OpenStratPomProject = osPom("egrid", "rutil", "geom", "tiling")

  def stagePom(dirPath: DirsAbs, pom: OpenStratPomProject): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / pom.artifactStr + "-" + versionStr, pom.out)

  def gFxDeps: RArr[PomDep] = RArr(OpenStratPomDep("rutil", versionStr), OpenStratPomDep("geom", versionStr), JavaFxControlsDependency("25.0.2"))

  def gFxPom: OpenStratPomProject = OpenStratPomProject("geomfx", versionStr, scalaVersion, gFxDeps)

  def poms: RArr[OpenStratPomProject] = RArr(rutil, geom, tiling, egrid, gFxPom)

  /** Write all the Poms to the same directory. */
  def aggregate(dirPath: DirsAbs): Unit =
  { val res: ErrBiAcc[Exception, PomFileWritten] = poms.mapErrBiAcc(pom => stagePom(dirPath, pom))
    deb(res.msgErrsSummary(s"to $dirPath"))
  }
}