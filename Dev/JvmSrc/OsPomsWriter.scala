/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*, pDoc.*

case class OsPomsWriter(versionStr: String = "0.3.11", scalaVersion: String = "3.8.2")
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

  def poms1: RArr[OpenStratPomProject] = RArr(rutil, geom, tiling, egrid, gFxPom)

  def poms2: RArr[OpenStratPomProject] = poms1 +% gFxPom

  /** Write all the Poms to the same directory. */
  def aggregate(dirPath: DirsAbs): ErrBiAcc[Exception, PomFileWritten] =
  { val res: ErrBiAcc[Exception, PomFileWritten] = poms2.mapErrBiAcc(pom => stagePom(dirPath, pom))
    deb(res.msgErrsSummary(s"to $dirPath"))
    res
  }
}