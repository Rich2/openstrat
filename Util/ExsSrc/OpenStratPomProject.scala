/* Copyright 2018-24 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
import ostrat.pWeb._

case class OpenStratPomDep(idStr: String, versionStr: String) extends PomDep
{
  override def artifactId: ArtifactId  = ArtifactId(idStr)

  override val groupId: GroupId = GroupId("com.richstrat")

  override def version: VersionElem = VersionElem(versionStr)
}

class OpenStratPomProject(val artifactStr: String, val versionStr: String, moduleStrs: StrArr) extends PomProject
{ override def artifactId: ArtifactId = ArtifactId(artifactStr)
  override val groudId: GroupId = GroupId("com.richstrat")
  override def version: VersionElem = VersionElem(versionStr)
  override def dependencies: RArr[PomDep] = moduleStrs.map(s => OpenStratPomDep(artifactStr, versionStr)) +% ScalaLibDependency("3.4.1")
}