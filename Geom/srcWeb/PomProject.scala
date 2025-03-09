/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

trait PomProject extends XmlMulti
{ def artifactId: ArtifactId
  val groudId: GroupId
  def version: VersionElem
  def modelVersion: XmlElem = XmlElemSimple("modelVersion", "4.0.0")
  override def tag: String = "project"
  override def attribs: RArr[XmlAtt] = RArr()
  def dependencies: RArr[PomDep]
  def dependenciesElem: PomDepenenciesElem = PomDepenenciesElem(dependencies)
  override def contents: RArr[XCon] = RArr(modelVersion, groudId, artifactId, version, dependenciesElem)
}

/** XML element for POM file dependency */
class PomDep(val groupId: GroupId, val artifactId: ArtifactId, val version: VersionElem) extends XmlMulti
{ override def tag: String = "dependency"
  override def attribs: RArr[XmlAtt] = RArr()
  override def contents: RArr[XCon] = RArr(groupId, artifactId, version)
}

object PomDep
{ /** Factory apply mthod to construct [[PomDep]] from [[String]]s. */
  def apply(groupStr: String, artifactStr: String, versionStr: String): PomDep = new PomDep(GroupId(groupStr), ArtifactId(artifactStr), VersionElem(versionStr))
}

/** XML element for POM file dependenies. Takes individual [[PopDep]]s as its child elements. */
class PomDepenenciesElem(val dependencies: RArr[PomDep]) extends XmlMulti
{ override def tag: String = "Dependencies"
  override def attribs: RArr[XmlAtt] = RArr()
  override def contents: RArr[PomDep] = dependencies
}

object PomDepenenciesElem{
  def apply(dependencies: RArr[PomDep]): PomDepenenciesElem = new PomDepenenciesElem(dependencies)
}

/** XML element for a POM GroupID for "org.scala-lang". */
object ScalaGroupId extends GroupId("org.scala-lang")

/** XML element for a POM dependency for a version of the Scala3 library. */
class ScalaLibDependency(val versionStr: String) extends PomDep(ScalaGroupId, ArtifactId("scala3-library_3"), VersionElem(versionStr))

object ScalaLibDependency
{ def apply(versionStr: String): ScalaLibDependency = new ScalaLibDependency(versionStr)
}

class ArtifactId(artifactStr: String) extends XmlElemSimple("artifactId", artifactStr)
object ArtifactId { def apply(artifactStr: String): ArtifactId = new ArtifactId(artifactStr) }

class GroupId(groupStr: String) extends XmlElemSimple("groupId", groupStr)
{
//  def dependency(versionStr: String): PomDep = PomDep()
}

object GroupId
{ def apply(groupStr: String): GroupId = new GroupId(groupStr)
}