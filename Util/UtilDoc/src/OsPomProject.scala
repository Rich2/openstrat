/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** Richstrat groupid for POM file. */
object RichstratID extends GroupId("com.richstrat")

/** An openstrat POM dependency minus its version. */
case class OsPomDepVerless(idStr: String)
{
  def version(version: SwVersion): OsPomDep = OsPomDep(idStr, version)
}

case class OsPomDep(idStr: String, override val version: SwVersion) extends PomDep
{ override val groupId: GroupId = RichstratID
  override def artifactId: ArtifactId = ArtifactId(idStr)
}

class OsPomProject(val artifactStr: String, val version: SwVersion, val dependencies: RArr[PomDep]) extends PomProject
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override val groudId: GroupId = RichstratID  
}

object OsPomProject
{
  def apply(artifactStr: String, version: SwVersion, scalaVersion: SwVersion, dependencies: RArr[PomDep]): OsPomProject =
    new OsPomProject(artifactStr, version, dependencies +% ScalaLibDependency(scalaVersion))

  def apply(artifactStr: String, version: SwVersion, scalaVersion: SwVersion, moduleStrs: StrArr): OsPomProject =
  { val dependencies: RArr[PomDep] = moduleStrs.map(s => OsPomDep(s, version)) +% ScalaLibDependency(scalaVersion)
    new OsPomProject(artifactStr, version, dependencies)
  }
}

/** Class for POMs for openstrat projects, lacking the project version and the Scala version. */
class OsPomProjectVerless(val moduleStr: String, val artifactStr: String, val osPomDeps: RArr[OsPomProjectVerless], val otherDeps: RArr[PomDep])
{
  def version(version: SwVersion, scalaVersion: SwVersion): OsPomProject =
    OsPomProject(artifactStr, version, scalaVersion, osPomDeps.map{ proj => OsPomDep(proj.artifactStr, version) } ++ otherDeps)
}

/** Creates POM files for Util project. */
object UtilPommer extends OsPomProjectVerless("Util", "rutil", RArr(), RArr())