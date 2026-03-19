/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pWeb.*, pDoc.*

case class OsPomsWriter(version: SwVersion = SwVersion(0, 3, 11), scalaVersion: SwVersion = SwVersion(3, 8, 2))
{
  def stageBuildPom(dirPath: DirsAbs, name: String, depStrs: String*): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / name + "-" + version.str, OsPomModule(name, version, scalaVersion, depStrs.toArr).out)

  def osPom(name: String, depStrs: String*) = OsPomModule(name, version, scalaVersion, depStrs.toArr)

  def rutil: OsPomModule = osPom("rutil")
  def geom: OsPomModule = osPom("geom", "rutil")
  def tiling: OsPomModule = osPom("tiling", "rutil", "geom")
  def egrid: OsPomModule = osPom("egrid", "rutil", "geom", "tiling")

  def stagePom(dirPath: DirsAbs, pom: OsPomModule): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / pom.artifactStr + "-" + version.str, pom.out)

  def gFxDeps: RArr[PomDep] = RArr(OsPomDep("rutil", version), OsPomDep("geom", version), JavaFxControlsDependency(25, 0, 2))

  def gFxPom: OsPomModule = OsPomModule("geomfx", version, scalaVersion, gFxDeps)

  def poms1: RArr[OsPomModule] = RArr(rutil, geom, tiling, egrid, gFxPom)

  def poms2: RArr[OsPomModule] = poms1 +% gFxPom

  /** Write all the Poms to the same directory. */
  def aggregate(dirPath: DirsAbs): ErrBiAcc[Exception, PomFileWritten] =
  { val res: ErrBiAcc[Exception, PomFileWritten] = poms2.mapErrBiAcc(pom => stagePom(dirPath, pom))
    deb(res.msgErrsSummary(s"to $dirPath"))
    res
  }
}