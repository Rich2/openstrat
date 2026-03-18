/* Copyright 2018-26 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

/** An XML element for a POM. The object model for the module, which can be a library or an end user app. */
trait PomModule extends XmlTagLines
{ def artifactId: ArtifactId
  def groudId: GroupId
  def version: VersionElem
  def modelVersion: XmlElem = XmlElemSimple("modelVersion", "4.0.0")
  override def tagName: String = "project"
  override def attribs: RArr[XAtt] = RArr()
  def dependencies: RArr[PomDep]
  def dependenciesElem: PomDepenenciesElem = PomDepenenciesElem(dependencies)
  override def contents: RArr[XConCompound] = RArr(modelVersion, groudId, artifactId, version, dependenciesElem)
}

/** XML element for a POM GroupID for "org.scala-lang". */
object ScalaGroupId extends GroupId("org.scala-lang")

/** XML element for a POM GroupID for "org.openjfx". */
object OpenJfxId extends GroupId("org.openjfx")

/** An XML element for a  */
class ArtifactId(artifactStr: String) extends XmlElemSimple("artifactId", artifactStr)
object ArtifactId { def apply(artifactStr: String): ArtifactId = new ArtifactId(artifactStr) }

/** groupid XML element. */
class GroupId(groupStr: String) extends XmlElemSimple("groupId", groupStr)

object GroupId
{ def apply(groupStr: String): GroupId = new GroupId(groupStr)
}