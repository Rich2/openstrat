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

/** An Openstrat module class for Poms and Jar staging. */
trait OsModulePom extends PomModule
{ def scalaVersion: SwVersion
  def artifactStr: String
  override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override val groudId: GroupId = RichstratID
  //override def dependencies: RArr[PomDep] = otherDependencies +% ScalaLibDependency(scalaVersion)
}



/** An Openstrat class for JVM modules / subprojects for Pom module. */
class OsModuleJvm(val artifactStr: String, val version: SwVersion, val scalaVersion: SwVersion, val otherDependencies: RArr[PomDep]) extends OsModulePom
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override def dependencies: RArr[PomDep] = otherDependencies +% ScalaLibDependency(scalaVersion)
}

object OsModuleJvm
{
  def apply(artifactStr: String, version: SwVersion, scalaVersion: SwVersion, dependencies: RArr[PomDep]): OsModuleJvm =
    new OsModuleJvm(artifactStr, version, scalaVersion, dependencies)

  def apply(artifactStr: String, version: SwVersion, scalaVersion: SwVersion, moduleStrs: StrArr): OsModuleJvm =
  { val dependencies: RArr[PomDep] = moduleStrs.map(s => OsPomDep(s, version))
    new OsModuleJvm(artifactStr, version, scalaVersion, dependencies)
  }
}

/** Class for POMs for openstrat projects, lacking the project version and the Scala version. */
class OsPomModuleVerless(val moduleDir: DirsRel, val artifactStr: String, val osPomDeps: RArr[OsPomModuleVerless], val otherDeps: RArr[PomDep])
{
  def version(version: SwVersion, scalaVersion: SwVersion): OsModulePom =
    OsModuleJvm(artifactStr, version, scalaVersion, osPomDeps.map{ proj => OsPomDep(proj.artifactStr, version) } ++ otherDeps)
}

object OsPomModuleVerless
{
  def apply(moduleDir: DirsRel, artifactStr: String, osPomDeps: RArr[OsPomModuleVerless], otherDeps: RArr[PomDep]): OsPomModuleVerless =
    new OsPomModuleVerless(moduleDir, artifactStr, osPomDeps, otherDeps)
}

/** Creates POM files for Util project. */
object UtilPommer extends OsPomModuleVerless(DirsRel("Util"), "rutil", RArr(), RArr())

