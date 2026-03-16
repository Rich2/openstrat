/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

object RichstratID extends GroupId("com.richstrat")

case class OsPomDepVerless(idStr: String)
{
  def version(versionStr: String): OsPomDep = OsPomDep(idStr, versionStr)
}

case class OsPomDep(idStr: String, versionStr: String) extends PomDep(RichstratID, ArtifactId(idStr), VersionElem(versionStr))

class OsPomProject(val artifactStr: String, val versionStr: String, val dependencies: RArr[PomDep]) extends PomProject
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override val groudId: GroupId = RichstratID
  override def version: VersionElem = VersionElem(versionStr) 
}

object OsPomProject
{
  def apply(artifactStr: String, versionStr: String, scalaVersion: String, dependencies: RArr[PomDep]): OsPomProject =
    new OsPomProject(artifactStr: String, versionStr: String, dependencies +% ScalaLibDependency(scalaVersion))

  def apply(artifactStr: String, versionStr: String, scalaVersion: String, moduleStrs: StrArr): OsPomProject =
  { val dependencies: RArr[PomDep] = moduleStrs.map(s => OsPomDep(s, versionStr)) +% ScalaLibDependency(scalaVersion)
    new OsPomProject(artifactStr: String, versionStr: String, dependencies)
  }
}

/** Class for POMs for openstrat projects, lacking the project version and the Scala version. */
class OsPomProjectVerless(val artifactStr: String, val osPomDeps: RArr[OsPomDepVerless], val otherDeps: RArr[PomDep])
{
  def version(versionStr: String, scalaVersion: String): OsPomProject =
    OsPomProject(artifactStr, versionStr, scalaVersion, osPomDeps.map(_.version(versionStr)) ++ otherDeps)
}