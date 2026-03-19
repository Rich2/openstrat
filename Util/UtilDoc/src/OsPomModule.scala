/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pWeb.*

/** Richstrat groupid for POM file. */
object RichstratID extends GroupId("com.richstrat")

/** An openstrat module POM dependency minus its version. */
case class OsPomDepVerless(idStr: String)
{ /** Adds the missing version to the openstrat dependency. */
  def version(version: SwVersion): OsPomDep = OsPomDep(idStr, version)
}

/** An Openstrat module POM dependency. */
case class OsPomDep(idStr: String, override val version: SwVersion) extends PomDep
{ override val groupId: GroupId = RichstratID
  override def artifactId: ArtifactId = ArtifactId(idStr)
}

/** An Openstrat Pom module. */
class OsPomModule(val artifactStr: String, val version: SwVersion, val dependencies: RArr[PomDep]) extends PomModule
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override val groudId: GroupId = RichstratID  
}

object OsPomModule
{
  def apply(artifactStr: String, version: SwVersion, scalaVersion: SwVersion, dependencies: RArr[PomDep]): OsPomModule =
    new OsPomModule(artifactStr, version, dependencies +% ScalaLibDependency(scalaVersion))

  def apply(artifactStr: String, version: SwVersion, scalaVersion: SwVersion, moduleStrs: StrArr): OsPomModule =
  { val dependencies: RArr[PomDep] = moduleStrs.map(s => OsPomDep(s, version)) +% ScalaLibDependency(scalaVersion)
    new OsPomModule(artifactStr, version, dependencies)
  }
}

/** Class for POMs for openstrat projects, lacking the project version and the Scala version. */
class OsPomProjectVerless(val moduleStr: String, val artifactStr: String, val osPomDeps: RArr[OsPomProjectVerless], val otherDeps: RArr[PomDep])
{
  def version(version: SwVersion, scalaVersion: SwVersion): OsPomModule =
    OsPomModule(artifactStr, version, scalaVersion, osPomDeps.map{ proj => OsPomDep(proj.artifactStr, version) } ++ otherDeps)
}

/** Creates POM files for Util project. */
object UtilPommer extends OsPomProjectVerless("Util", "rutil", RArr(), RArr())