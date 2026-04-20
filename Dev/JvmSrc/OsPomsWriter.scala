/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import utiljvm.*, pweb.*, pDoc.*

case class OsPomsWriter(version: SwVersion, scalaVersion: SwVersion = SwVersion(3, 8, 2))
{
  def stageBuildPom(dirPath: DirsAbs, name: String, depStrs: String*): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / name + "-" + version.str, OsModuleJvm(name, version, scalaVersion, depStrs.toArr).out)

  def osModJvm(name: String, depStrs: String*) = OsModuleJvm(name, version, scalaVersion, depStrs.toArr)

  def stagePom(dirPath: DirsAbs, pom: OsModulePom): ErrBi[Exception, PomFileWritten] =
    writePom(dirPath.str / pom.artifactStr + "-" + version.str, pom.out) 
}