/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pDoc
import pweb.*, wcode.*

/** Richstrat groupid for POM file. */
object RichstratID extends GroupId("com.richstrat")

/** An openstrat module POM dependency minus its version. */
case class OsPomDepVerless(idStr: String)
{ /** Adds the missing version to the openstrat dependency. */
  def version(version: VersionPatchElem): OsPomDep = OsPomDep(idStr, version)
}

/** An Openstrat module POM dependency. */
case class OsPomDep(idStr: String, override val version: VersionPatchElem) extends PomDep
{ override val groupId: GroupId = RichstratID
  override def artifactId: ArtifactId = ArtifactId(idStr)
}

/** An Openstrat module class for Poms and Jar staging. */
trait OsModulePom extends PomModule
{ def scalaVersion: VersionPatchElem
  def artifactStr: String
  override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override val groudId: GroupId = RichstratID
  //override def dependencies: RArr[PomDep] = otherDependencies +% ScalaLibDependency(scalaVersion)
}

/** An Openstrat class for JVM modules / subprojects for Pom module. */
class OsModuleJvm(val artifactStr: String, val version: VersionPatchElem, val scalaVersion: VersionPatchElem, val otherDependencies: RArr[PomDep]) extends OsModulePom
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override def dependencies: RArr[PomDep] = otherDependencies +% ScalaLibDep(scalaVersion)
}

object OsModuleJvm
{
  def apply(artifactStr: String, version: VersionPatchElem, scalaVersion: VersionPatchElem, dependencies: RArr[PomDep]): OsModuleJvm =
    new OsModuleJvm(artifactStr, version, scalaVersion, dependencies)

  def apply(artifactStr: String, version: VersionPatchElem, scalaVersion: VersionPatchElem, moduleStrs: StrArr): OsModuleJvm =
  { val dependencies: RArr[PomDep] = moduleStrs.map(s => OsPomDep(s, version))
    new OsModuleJvm(artifactStr, version, scalaVersion, dependencies)
  }
}

/** An Openstrat class for JavaScript modules / subprojects for Pom module. */
class OsModuleJs(val artifactStr: String, val version: VersionPatchElem, val scalaVersion: VersionPatchElem, val otherDependencies: RArr[PomDep]) extends OsModulePom
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override def dependencies: RArr[PomDep] = otherDependencies +% ScalaJsLibDep(scalaVersion)
}

/** Class for POMs for openstrat projects, lacking the project version and the Scala version. */
trait OsModulePomVerless
{ /** The artifact stem [[String]]. */
  def artifactStr: String

  /** The Mill builder output subdirectory for the module. */
  def moduleDir: DirsRel

  def otherDeps: RArr[PomDep]

  def version(version: VersionPatchElem, scalaVersion: VersionPatchElem): OsModulePom
}

/** Class for POMs for openstrat projects, lacking the project version and the Scala version. */
class OsModuleJvmVerless(val moduleDir: DirsRel, val artifactStr: String, val osPomDeps: RArr[OsModuleJvmVerless], val otherDeps: RArr[PomDep]) extends
  OsModulePomVerless
{
  def version(version: VersionPatchElem, scalaVersion: VersionPatchElem): OsModulePom =
    OsModuleJvm(artifactStr, version, scalaVersion, osPomDeps.map{ proj => OsPomDep(proj.artifactStr, version) } ++ otherDeps)
}

object OsModuleJvmVerless
{ /** Factory apply method for an Openstrat JVM module lacking the project version and the Scala version. */
  def apply(moduleDir: DirsRel, artifactStr: String, osPomDeps: RArr[OsModuleJvmVerless], otherDeps: RArr[PomDep]): OsModuleJvmVerless =
    new OsModuleJvmVerless(moduleDir, artifactStr, osPomDeps, otherDeps)
}

/** Versionless. Creates POM files and copies Mill, JAR, artifacts for Util JVM module. */
object UtilPommer extends OsModuleJvmVerless(DirsRel("Util"), "rutil", RArr(), RArr())

/** Class for POMs for openstrat projects, lacking the project version and the Scala version. */
class OsModuleJsVerless(val moduleDir: DirsRel, val artifactStr: String, val osPomDeps: RArr[OsModuleJsVerless], val otherDeps: RArr[PomDep]) extends
  OsModulePomVerless
{
  def version(version: VersionPatchElem, scalaVersion: VersionPatchElem): OsModuleJs =
    OsModuleJs(artifactStr, version, scalaVersion, osPomDeps.map{ proj => OsPomDep(proj.artifactStr, version) } ++ otherDeps)
}

object OsModuleJsVerless
{ /** Factory apply method for an Openstrat Scala.js module lacking the project version and the Scala version. */
  def apply(moduleDir: DirsRel, artifactStr: String, osPomDeps: RArr[OsModuleJsVerless], otherDeps: RArr[PomDep]): OsModuleJsVerless =
    new OsModuleJsVerless(moduleDir, artifactStr, osPomDeps, otherDeps)
}

/** Versionless Creates POM files and copies Mill, JAR artifacts for UtilJs project. */
object UtilJsPommer extends OsModuleJsVerless(DirsRel("UtilJs"), "rutiljs", RArr(), RArr())