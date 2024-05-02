/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pWeb

class PomProject(val groupStr: String, val artifactStr: String, versionStr: String, val modelVersionStr: String = "4.0.0") extends XmlMulti
{ def artifactId: ArtifactId = ArtifactId(artifactStr)
  val groudId: GroupId = GroupId(groupStr)
  def version: XmlVersion = XmlVersion(versionStr)
  def modelVersion = XmlElemSimple("modelVersion", modelVersionStr)
  override def tag: String = "project"
  override def attribs: RArr[XmlAtt] = RArr()

  override def contents: RArr[XCon] = RArr(modelVersion, groudId, artifactId, version)
}

class PomDep(val groupStr: String, val artifactStr: String, val versionStr: String) extends XmlMulti
{ override def tag: String = "dependency"
  override def attribs: RArr[XmlAtt] = RArr()
  def artifactId: ArtifactId = ArtifactId(artifactStr)
  val groudId: GroupId = GroupId(groupStr)
  def version: XmlVersion = XmlVersion(versionStr)
  override def contents: RArr[XCon] = RArr(groudId, artifactId, version)
}

class ArtifactId(artifactStr: String) extends XmlElemSimple("artifactId", artifactStr)
object ArtifactId { def apply(artifactStr: String): ArtifactId = new ArtifactId(artifactStr) }

class GroupId(groupStr: String) extends XmlElemSimple("groupId", groupStr)
object GroupId { def apply(groupStr: String): GroupId = new GroupId(groupStr) }