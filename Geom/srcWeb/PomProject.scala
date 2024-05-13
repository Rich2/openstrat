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

trait PomDep extends XmlMulti
{ override def tag: String = "dependency"
  override def attribs: RArr[XmlAtt] = RArr()
  def artifactId: ArtifactId
  val groupId: GroupId
  def version: VersionElem
  override def contents: RArr[XCon] = RArr(groupId, artifactId, version)
}

object PomDep
{
  //(val groupStr: String, val artifactStr: String, val versionStr: String)
}

class PomDepenenciesElem(val dependencies: RArr[PomDep]) extends XmlMulti
{ override def tag: String = "Dependencies"
  override def attribs: RArr[XmlAtt] = RArr()
  override def contents: RArr[PomDep] = dependencies
}

object PomDepenenciesElem{
  def apply(dependencies: RArr[PomDep]): PomDepenenciesElem = new PomDepenenciesElem(dependencies)
}

object ScalaGroupId extends GroupId("org.scala-lang")

class ScalaLibDependency(val versionStr: String) extends PomDep
{ override def artifactId: ArtifactId = ArtifactId("scala3-library_3")
  override val groupId: GroupId = ScalaGroupId
  override def version: VersionElem = VersionElem(versionStr)
}

object ScalaLibDependency{
  def apply(versionStr: String): ScalaLibDependency = new ScalaLibDependency(versionStr)
}

class ArtifactId(artifactStr: String) extends XmlElemSimple("artifactId", artifactStr)
object ArtifactId { def apply(artifactStr: String): ArtifactId = new ArtifactId(artifactStr) }

class GroupId(groupStr: String) extends XmlElemSimple("groupId", groupStr)
object GroupId { def apply(groupStr: String): GroupId = new GroupId(groupStr) }