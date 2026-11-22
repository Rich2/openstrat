/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDev
import pweb.*, wcode.*, utiljvm.*, pDoc.*

case class OsPomsWriter(version: VersionPatchElem, scalaVersion: VersionPatchElem = VersionPatchElem(3, 10, 0))
{
  def stageBuildPom(dirPath: DirsAbs, name: String, depStrs: String*): Either[Exception, PomFileWritten] =
    dirPath.writePom(name + "-" + version.str, OsModuleJvm(name, version, scalaVersion, depStrs.toArr))

  def osModJvm(name: String, depStrs: String*) = OsModuleJvm(name, version, scalaVersion, depStrs.toArr)

  def stagePom(dirPath: DirsAbs, pom: OsModulePom): IOExcEither[PomFileWritten] =
    dirPath.writePom(pom.artifactStr + "-" + version.str, pom)
}