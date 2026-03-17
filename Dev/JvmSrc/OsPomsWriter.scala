/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*, pDoc.*

case class OsPomsWriter(version: SwVersion = SwVersion(0, 3, 11), scalaVersion: SwVersion = SwVersion(3, 8, 2))
{
  def stageBuildPom(dirPath: DirsAbs, name: String, depStrs: String*): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / name + "-" + version.str, OsPomProject(name, version, scalaVersion, depStrs.toArr).out)

  def osPom(name: String, depStrs: String*) = OsPomProject(name, version, scalaVersion, depStrs.toArr)

  def rutil: OsPomProject = osPom("rutil")
  def geom: OsPomProject = osPom("geom", "rutil")
  def tiling: OsPomProject = osPom("tiling", "rutil", "geom")
  def egrid: OsPomProject = osPom("egrid", "rutil", "geom", "tiling")

  def stagePom(dirPath: DirsAbs, pom: OsPomProject): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / pom.artifactStr + "-" + version.str, pom.out)

  def gFxDeps: RArr[PomDep] = RArr(OsPomDep("rutil", version), OsPomDep("geom", version), JavaFxControlsDependency(25, 0, 2))

  def gFxPom: OsPomProject = OsPomProject("geomfx", version, scalaVersion, gFxDeps)

  def poms1: RArr[OsPomProject] = RArr(rutil, geom, tiling, egrid, gFxPom)

  def poms2: RArr[OsPomProject] = poms1 +% gFxPom

  /** Write all the Poms to the same directory. */
  def aggregate(dirPath: DirsAbs): ErrBiAcc[Exception, PomFileWritten] =
  { val res: ErrBiAcc[Exception, PomFileWritten] = poms2.mapErrBiAcc(pom => stagePom(dirPath, pom))
    deb(res.msgErrsSummary(s"to $dirPath"))
    res
  }
}